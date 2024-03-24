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

    getCurrentGame(playerName: string, gameId: string): Observable<Game> {
      return this.http.post<Game>(`${env.backendUrl}/room/${gameId}`, { playerName: playerName, gameId: gameId })
    }

    createGame(playerName: string, gameName: string) {
      return this.http.post<Game>(`${env.backendUrl}/create`, { playerName: playerName, gameName: gameName })
    }

    joinGame(playerName: string, gameId: string) {
      return this.http.post<Game>(`${env.backendUrl}/join/${gameId}`, { playerName: playerName, gameId: gameId })
    }

    leaveGame(playerName: string, gameId: string) {
      return this.http.post(`${env.backendUrl}/leave/${gameId}`, { playerName: playerName, gameId: gameId })
    }

    startGame(playerName: string, gameId: string): Observable<Game> {
      return this.http.post<Game>(`${env.backendUrl}/start/${gameId}`, { playerName: playerName, gameId: gameId })
    }
}
