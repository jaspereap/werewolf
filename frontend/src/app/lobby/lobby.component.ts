import { Component, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { LobbyStore } from './lobby.store';
import { Player } from '../models/dtos';
import { provideComponentStore } from '@ngrx/component-store';
import { LocalStoreService } from '../local-store.service';
import { Observable, Subscription, map } from 'rxjs';

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
  // States
  currentPlayer$ = this.lobbyStore.currentPlayer$;
  games$ = this.lobbyStore.games$;
  currentGame$ = this.lobbyStore.currentGame$;
  // State Checks
  isCurrentPlayerSet$: Observable<boolean> = this.currentPlayer$.pipe(
    map(player => !!player.playerName)
  );
  // Sub management
  ScurrentPlayer$!: Subscription;
  ScurrentGame$!: Subscription;
  Sgames$!: Subscription;

  constructor(private fb: FormBuilder, private lobbyStore: LobbyStore, private localStore: LocalStoreService) {}

  ngOnInit(): void {
    this.newRoomForm = this.initForm();
    console.log('Lobby Component Init')
    this.ScurrentGame$ = this.currentGame$.subscribe(
      (g) => console.log('current game: ', g)
    )
    this.Sgames$ = this.lobbyStore.getGames();
    this.ScurrentPlayer$ = this.currentPlayer$.subscribe(
      (player) => {
        console.log(player)
        // If current player not set, retrieve from sessionStorage if available
        if (!player.playerName && this.localStore.getCurrentPlayerName()) {
          this.lobbyStore.setCurrentPlayer(this.newPlayer(this.localStore.getCurrentPlayerName()));
        }
      }
    )
  }

  ngOnDestroy(): void {
    console.log('Lobby Component Destroyed');
    this.ScurrentPlayer$.unsubscribe();
    this.ScurrentGame$.unsubscribe();
    this.Sgames$.unsubscribe();
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

