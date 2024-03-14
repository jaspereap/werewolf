import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment as env } from '../environments/environment';
import { CreateGameRequest, Game, Player } from './dtos';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LobbyService {

  constructor(private http: HttpClient) { }

  createGame(currentPlayer: Player, gameName: string): Observable<any> {
    // const params = new HttpParams().set('gameName', gameName);
    const request: CreateGameRequest = {
      playerName: currentPlayer.name,
      gameName: gameName
    }
    return this.http.post<Player>(`${env.backendUrl}/create`, request);
  }

  getGames(): Observable<Game[]>{
    return this.http.get<any>(`${env.backendUrl}/rooms`);
  }


  joinGame() {}
  leaveGame() {}
  deleteGame() {}
}
