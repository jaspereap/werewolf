import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment as env } from '../../environments/environment';
import { CreateGameRequest, Game, Player } from '../models/dtos';
import { Observable, exhaustMap, switchMap, take, tap, withLatestFrom } from 'rxjs';
import { ComponentStore, tapResponse } from '@ngrx/component-store';
import { Router } from '@angular/router';

export interface LobbyState {
  currentPlayer: Player,
  currentGame: Game,
  games: Game[];
}

@Injectable({providedIn: 'root'})
export class LobbyStore extends ComponentStore<LobbyState> {
  
  constructor(private http: HttpClient, private router: Router) { 
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
  readonly setGames = this.updater((state, games: Game[]) => ({
    ...state,
    games: games
  }))
  readonly addGame = this.updater((state, game: Game) => ({
    ...state,
    games: [...state.games, game]
  }))

  addPlayer = this.updater((state, newPlayer: Player) => ({
    ...state,
    currentGame: {
      ...state.currentGame,
      players: [...state.currentGame.players, newPlayer]
    }
  }))

// Retrieve list of games for lobby
  readonly getGames = this.effect((trigger$) => 
    trigger$.pipe(
      tap(() => console.log('getGames triggered')),
        exhaustMap(() => {
          return this.http.get<Game[]>(`${env.backendUrl}/rooms`).pipe(
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
  readonly getCurrentGame = this.effect((trigger$: Observable<[string, string]>) => 
    trigger$.pipe(
      tap(() => console.log('getGame triggered')),
      exhaustMap(([playerName, gameName]) => 
        this.http.post<Game>(`${env.backendUrl}/room/${gameName}`, { playerName, gameName }).pipe(
          tapResponse(
            response => {
              console.log('getGame Server Response: ', response);
              this.setCurrentPlayer({playerName: playerName} as Player);
              this.setCurrentGame(response);
            },
            error => console.error(error)
          )
        )
      )
    ));
// For creating a new game
  readonly createGame = this.effect((gameName$: Observable<string>) =>
    gameName$.pipe(
      tap(() => console.log('createGame triggered')),
      exhaustMap(gameName => 
        this.select(state => state.currentPlayer).pipe(
          take(1), // Take the current player state once to avoid repeating the operation
          exhaustMap(currentPlayer => 
            this.http.post<Game>(`${env.backendUrl}/create`, { playerName: currentPlayer.playerName, gameName })
            .pipe(
              tapResponse(
                (game) => {
                  console.log("createGame Server Response: ", game);
                  // Add game to list
                  this.addGame(game);
                  // Set current game to created game
                  this.setCurrentGame(game);
                  // Route to created game room
                  this.router.navigate(['/room', gameName]);
                },
                (error) => console.error(error)
              )
            )
          )
        )
      )
    )
  );
// For joining a game
  readonly joinGame = this.effect((gameName$: Observable<string>) => 
    gameName$.pipe(
      withLatestFrom(this.currentPlayer$),
      tap(() => console.log('joinGame triggered')),
      exhaustMap(([gameName, currentPlayer]) => 
        this.http.post<Game>(`${env.backendUrl}/join/${gameName}`, { playerName: currentPlayer.playerName, gameName })
        .pipe(
          tapResponse(
            resp => {
              console.log('joinGame Server Response: ', resp);
              this.setCurrentGame(resp);
              // Route to room
              this.router.navigate(['/room', gameName]);
            },
            error => console.error(error)
          )
        )
      )
    )
  );

// For leaving a game
  readonly leaveGame = this.effect(trigger$ => 
    trigger$.pipe(
      tap(() => console.log('leaveGame triggered')),
      withLatestFrom(this.currentPlayer$, this.currentGame$),
      exhaustMap(([,currentPlayer, currentGame]) => 
        this.http.post(`${env.backendUrl}/leave/${currentGame.gameName}`, { playerName: currentPlayer.playerName, gameName: currentGame.gameName }).pipe(
          tapResponse(
            resp => {
              console.log('leaveGame Server Response: ', resp);
              this.router.navigate(['/lobby']);
            },
            err => console.error(err)
          )
        )
      )
    )
  );
  

  deleteGame() {}
}
