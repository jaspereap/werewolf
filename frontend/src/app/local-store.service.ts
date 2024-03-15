import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LocalStoreService {

  constructor() { }

  saveCurrentPlayerName(playerName: string) {
    localStorage.setItem('playerName', playerName);
  }
  getCurrentPlayerName() : string {
    return localStorage.getItem('playerName') ?? '';
  }
}
