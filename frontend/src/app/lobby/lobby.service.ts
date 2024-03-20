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

    getCurrentGame(playerName: string, gameName: string): Observable<Game> {
      return this.http.post<Game>(`${env.backendUrl}/room/${gameName}`, { playerName, gameName })
    }

    createGame(playerName: string, gameName: string) {
      return this.http.post<Game>(`${env.backendUrl}/create`, { playerName: playerName, gameName: gameName })
    }

    joinGame(playerName: string, gameName: string) {
      return this.http.post<Game>(`${env.backendUrl}/join/${gameName}`, { playerName: playerName, gameName: gameName })
    }

    leaveGame(playerName: string, gameName: string) {
      return this.http.post(`${env.backendUrl}/leave/${gameName}`, { playerName: playerName, gameName: gameName })
    }
}
