import { Component, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { LobbyStore } from './lobby.store';
import { Player, PlayerState } from '../models/dtos';
import { provideComponentStore } from '@ngrx/component-store';
import { LocalStoreService } from '../shared/local-store.service';
import { Observable, Subscription, map, of } from 'rxjs';
import { v4 as uuid } from 'uuid';

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
  currentGame$ = this.lobbyStore.currentGame$;
  games$ = this.lobbyStore.games$;
  // State Checks
  isCurrentPlayerSet$: Observable<boolean> = this.currentPlayer$.pipe(
    map(player => !!player.playerName)
  );
  // Sub management
  ScurrentPlayer$!: Subscription;
  ScurrentGame$!: Subscription;
  Sgames$!: Subscription;

  constructor(private fb: FormBuilder, private lobbyStore: LobbyStore, private localStore: LocalStoreService) {

  }

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
        let currentPlayer = this.localStore.getCurrentPlayer();
        if (!player.playerName && currentPlayer.playerId) {
          this.lobbyStore.setCurrentPlayer({
            playerName: currentPlayer.playerName, 
            playerId: currentPlayer.playerId,
            playerState: PlayerState.ALIVE
          } as Player);
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

  setPlayer(playerName: string) {
    let player = this.newPlayer(playerName) as Player;
    this.lobbyStore.setCurrentPlayer(player);
    this.localStore.saveCurrentPlayer(playerName, player.playerId);
  }

  initForm(): FormGroup {
    return this.fb.group({
      // currentPlayerName: this.fb.control<string>('bob'),
      gameName: this.fb.control<string>('testGame')
    })
  }

// TODO: TEMPORARY INIT PLAYER
  newPlayer(playerName: string): Player {
  let id = uuid().substring(0,6).toUpperCase();
  console.log("UUID ID: ", id)
    return {playerName: playerName, playerId: id, playerState: PlayerState.ALIVE} as Player;
  }

  joinRoom(gameName: string) {
    console.log("Joining room: ", gameName)
    this.lobbyStore.joinGame(of(gameName));
    // this.lobbyStore.getGames();
  }
}

