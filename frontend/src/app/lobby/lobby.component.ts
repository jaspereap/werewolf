import { Component, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { LobbyStore } from './lobby.store';
import { Player } from '../dtos';
import { provideComponentStore } from '@ngrx/component-store';
import { LocalStoreService } from '../local-store.service';

interface FormData {
  currentPlayerName: string,
  gameName: string
}

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrl: './lobby.component.css'
})
export class LobbyComponent implements OnInit, OnDestroy{
  newRoomForm!: FormGroup;
  
  currentPlayer$ = this.lobbyStore.currentPlayer$;
  games$ = this.lobbyStore.games$;
  
  constructor(private fb: FormBuilder, private lobbyStore: LobbyStore, private localStore: LocalStoreService) {}
  ngOnDestroy(): void {
    console.log('Lobby Component Destroyed');
  }

  ngOnInit(): void {
    this.newRoomForm = this.initForm();
    console.log('Lobby Component Init')
    console.log('getting games')
    this.lobbyStore.getGames();
  }

  processNewRoom() {
    const formData = this.newRoomForm.value as FormData;
    const gameName = formData.gameName;
    this.lobbyStore.createGame(gameName);
  }

  setPlayer(name: string) {
    console.log(name)
    this.lobbyStore.setCurrentPlayer(this.newPlayer(name));
    this.localStore.saveCurrentPlayerName(name);
  }

  initForm(): FormGroup {
    return this.fb.group({
      // currentPlayerName: this.fb.control<string>('bob'),
      gameName: this.fb.control<string>('testGame')
    })
  }

  newPlayer(name: string) {
    return {playerName: name } as Player;
  }
}

