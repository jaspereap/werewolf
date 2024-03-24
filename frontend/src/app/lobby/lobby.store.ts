import { HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Game, Player } from '../models/dtos';
import { Observable, exhaustMap, take, tap, withLatestFrom } from 'rxjs';
import { ComponentStore, tapResponse } from '@ngrx/component-store';
import { Router } from '@angular/router';
import { LobbyService } from './lobby.service';

export interface LobbyState {
  currentPlayer: Player,
  currentGame: Game,
  games: Game[];
}

@Injectable({providedIn: 'root'})
export class LobbyStore extends ComponentStore<LobbyState> {
  
  constructor( private router: Router, 
    private lobbyService: LobbyService) { 
// Initialise store
    super({
      currentPlayer: {playerName: ''} as Player,
      currentGame: {} as Game,
      games: []
    })
  }
// Selectors
  currentPlayer$ = this.select((state) => state.currentPlayer);
  currentGame$ = this.select((state) => state.currentGame);
  games$ = this.select((state) => state.games);

// Updaters
  readonly setCurrentPlayer = this.updater((state, currentPlayer: Player) => ({
    ...state,
    currentPlayer
  }))
  readonly setCurrentGame = this.updater((state, currentGame: Game) => ({
    ...state,
    currentGame
  }))
  readonly unsetCurrentGame = this.updater((state) => ({
    ...state,
    currentGame: {} as Game
  }))
  readonly setGames = this.updater((state, games: Game[]) => ({
    ...state,
    games: games
  }))
  readonly addGame = this.updater((state, game: Game) => ({
    ...state,
    games: [...state.games, game]
  }))

  readonly addPlayer = this.updater((state, newPlayer: Player) => ({
    ...state,
    currentGame: {
      ...state.currentGame,
      players: state.currentGame && Array.isArray(state.currentGame.players) 
      ? [...state.currentGame.players, newPlayer] 
      : [newPlayer]
    }
  }))

  readonly removePlayer = this.updater((state, removePlayer: Player) => ({
    ...state,
    currentGame: {
      ...state.currentGame,
      players: state.currentGame && Array.isArray(state.currentGame.players)
      ? state.currentGame.players.filter(player => player.playerName !== removePlayer.playerName)
      : []
    }
  }))

// Retrieve list of games for lobby
  readonly getGames = this.effect((trigger$) => 
    trigger$.pipe(
      tap(() => console.log('getGames triggered')),
        exhaustMap(() => {
          return this.lobbyService.getGames().pipe(
            tapResponse(
              (games) => {
                console.log('getGames Server Response: ', games)
                this.setGames(games);
              },
              (err: HttpErrorResponse) => {console.error(err)}
            )
          )
        }
      )
    )
  );

  // For Client-side persistence, rehydate currentPlayer and currentGame
  // readonly getCurrentGame = this.effect((trigger$: Observable<[string, string]>) => 
  //   trigger$.pipe(
  //     tap(() => console.log('getGame triggered')),
  //     exhaustMap(([playerName, gameId]) => 
  //       this.lobbyService.getCurrentGame(playerName, gameId).pipe(
  //         tapResponse(
  //           response => {
  //             console.log('getGame Server Response: ', response);
  //             this.setCurrentPlayer({playerName: playerName} as Player);
  //             // this.setCurrentGame(response);
  //           },
  //           error => console.error(error)
  //         )
  //       )
  //     )
  //   ));
// For creating a new game
  readonly createGame = this.effect((gameName$: Observable<string>) =>
    gameName$.pipe(
      tap(() => console.log('createGame triggered')),
      exhaustMap(gameName => 
        this.select(state => state.currentPlayer).pipe(
          take(1), // Take the current player state once to avoid repeating the operation
          exhaustMap(currentPlayer => 
            this.lobbyService.createGame(currentPlayer.playerName, currentPlayer.playerId, gameName).pipe(
              tapResponse(
                (game) => {
                  console.log("createGame Server Response: ", game);
                  // Add game to list - not needed if user routed to room immediately.
                  this.addGame(game);
                  // Set current game to created game
                  this.setCurrentGame(game);
                  // Route to created game room
                  this.enterRoom(game.gameId, currentPlayer.playerId)
                },
                (error) => console.error(error)
              )
            )
          )
        )
      )
    ));
// For joining a game
  readonly joinGame = this.effect((gameId$: Observable<string>) => 
    gameId$.pipe(
      withLatestFrom(this.currentPlayer$),
      tap(() => console.log('joinGame triggered')),
      exhaustMap(([gameId, currentPlayer]) => 
      this.lobbyService.joinGame(currentPlayer.playerName, currentPlayer.playerId, gameId).pipe(
        tapResponse(
          resp => {
            console.log('joinGame Server Response: ', resp);
            this.setCurrentGame(resp);
            // Route to room
            this.enterRoom(gameId, currentPlayer.playerName);
          },
          error => console.error(error)
        )
      )
    )
  ));

// For leaving a game
  readonly leaveGame = this.effect(trigger$ => 
    trigger$.pipe(
      tap(() => console.log('leaveGame triggered')),
      withLatestFrom(this.currentPlayer$, this.currentGame$),
      exhaustMap(([,currentPlayer, currentGame]) => 
        this.lobbyService.leaveGame(currentPlayer.playerName, currentPlayer.playerId, currentGame.gameId).pipe(
          tapResponse(
            resp => {
              console.log('leaveGame Server Response: ', resp);
              this.unsetCurrentGame();
              this.router.navigate(['/lobby']);
            },
            err => console.error(err)
          )
        )
      )
    )
  );

  readonly startGame = this.effect(trigger$ =>
    trigger$.pipe(
      tap(),
      withLatestFrom(this.currentPlayer$, this.currentGame$),
      exhaustMap(([, currentPlayer, currentGame]) =>
        this.lobbyService.startGame(currentPlayer.playerName, currentGame.gameId).pipe(
          tapResponse(
            resp => {
              console.log('startGame Server Response: ', resp)
            },
            err => console.error(err)
          )
        )
      )
    )  
  )
  
  enterRoom(gameId: string, playerName: string) {
    console.log('entering room')
    // Route to room
    this.router.navigate(['/room', gameId]);
  }
  deleteGame() {}
}
