import { Injectable } from '@angular/core';
import { Game } from '../models/dtos';
import { HttpClient } from '@angular/common/http';
import { environment as env } from '../../environments/environment';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LobbyService {

  constructor(private http:HttpClient) { }

    getGames(): Observable<Game[]>{
      return this.http.get<Game[]>(`${env.backendUrl}/rooms`)
    }

    // getCurrentGame(playerId: string, gameId: string): Observable<Game> {
    //   return this.http.post<Game>(`${env.backendUrl}/room/${gameId}`, { playerId: playerId, gameId: gameId })
    // }

    createGame(playerName:string, playerId: string, gameName: string) {
      return this.http.post<Game>(`${env.backendUrl}/create`, { playerName: playerName, playerId: playerId, gameName: gameName })
    }

    joinGame(playerName: string, playerId: string, gameId: string) {
      return this.http.post<Game>(`${env.backendUrl}/join/${gameId}`, { playerName: playerName, playerId: playerId, gameId: gameId })
    }

    leaveGame(playerName: string, playerId: string, gameId: string) {
      return this.http.post(`${env.backendUrl}/leave/${gameId}`, { playerName: playerName, playerId: playerId, gameId: gameId })
    }

    startGame(playerId: string, gameId: string): Observable<Game> {
      return this.http.post<Game>(`${env.backendUrl}/start/${gameId}`, { playerId: playerId, gameId: gameId })
    }
}
