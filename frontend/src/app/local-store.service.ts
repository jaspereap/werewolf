import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStoreService {

  constructor() { }

  saveCurrentPlayerName(playerName: string) {
    sessionStorage.setItem('playerName', playerName);
    
  }
  getCurrentPlayerName() : string {
    return sessionStorage.getItem('playerName') ?? '';
  }
}
