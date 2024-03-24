import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
// Ignore class
export class LocalStoreService {

  constructor() { }

  saveCurrentPlayer(playerName: string, playerId: string) {
    sessionStorage.setItem('playerName', playerName);
    sessionStorage.setItem('playerId', playerId);
  }
  
  getCurrentPlayer() {
    return {
      playerName: sessionStorage.getItem('playerName') ?? '', 
      playerId: sessionStorage.getItem('playerId') ?? ''
    }
  }
}
