import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
// Ignore class
export class LocalStoreService {

  constructor() { }

  saveCurrentPlayerName(playerName: string) {
    sessionStorage.setItem('playerName', playerName);
  }
  
  getCurrentPlayerName() : string {
    return sessionStorage.getItem('playerName') ?? '';
  }
}
