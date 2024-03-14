import { Component, OnInit } from '@angular/core';
import { GameState, PhaseType, Player, PlayerState, Role } from '../dtos';
import { GameStore } from './game.store';
import { provideComponentStore } from '@ngrx/component-store';
import { GameService } from '../game.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrl: './game.component.css',
  providers: [provideComponentStore(GameStore)]
})
export class GameComponent implements OnInit {
  currentPlayer!: Player;
  
  vm$ = this.gameStore.vm$;
  gameName!: string;
  gameState!: GameState;
  players!: Player[];
  currentPhase!: PhaseType;

  constructor(private gameSvc: GameService, private gameStore: GameStore) {}

  ngOnInit(): void {
    console.log('Main Game Initialised!');
    this.currentPlayer = { name: 'bob', state: PlayerState.ALIVE, role: undefined } as Player;

    // Subscribe to component store observable, update state
    this.vm$.subscribe(
      (state) => {
        this.gameName = state.gameName;
        this.currentPhase = state.currentPhase;
        this.gameState = state.gameState;
        this.players = state.players;
        // this.currentPlayer = state.currentPlayer
      }
    )
    // Subscribe to server for game updates
    this.gameSvc.subscribeGame(this.gameName, this.currentPlayer.name);
  }

  setPhaseButton() {
    this.gameStore.setCurrentPhase(PhaseType.GAMEOVER);
  }
  setStateButton() {
    this.gameStore.setGameState(GameState.COMPLETED);
  }
}

