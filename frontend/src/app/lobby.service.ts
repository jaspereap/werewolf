import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment as env } from '../environments/environment';
import { Game, Player } from './dtos';
import { Observable, exhaustMap, tap } from 'rxjs';
import { ComponentStore, tapResponse } from '@ngrx/component-store';

export interface LobbyState {
  games: Game[];
}

@Injectable()
export class LobbyService extends ComponentStore<LobbyState>{
  
  games$ = this.select((state) => state.games)

  constructor(private http: HttpClient) { 
    super({
      games: []
    })
  }

  readonly setGames = this.updater((state, games: Game[]) => ({
    ...state,
    games: games
  }))

  readonly addGame = this.updater((state, game: Game) => ({
    ...state,
    games: [...state.games, game]
  }))

  readonly getGames = this.effect((trigger$) => {
    return trigger$.pipe(
      tap(() => console.log('getGames triggered')),
      exhaustMap(() => {
        return this.http.get<Game[]>(`${env.backendUrl}/rooms`).pipe(
          tapResponse(
            (games) => this.setGames(games),
            (err: HttpErrorResponse) => {console.error(err)}
          )
        )
      })
    )
  })

  readonly createGame = this.effect((params$: Observable<{ currentPlayer: Player; gameName: string }>) =>
    params$.pipe(
      tap(() => {console.log('createGame triggered')}),
      exhaustMap(({ currentPlayer, gameName }) =>
        this.http.post<Game>(`${env.backendUrl}/create`, {playerName: currentPlayer.name, gameName: gameName,})
        .pipe(
          tapResponse(
            (game) => {console.log("Server Response: ", game);this.addGame(game)},
            (error) => console.error(error)
          ))
        )
    )
  )

  joinGame() {
    console.log("Lobby Service -> joinGame")
  }
  leaveGame() {}
  deleteGame() {}
}
