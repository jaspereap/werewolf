import { Component, OnInit } from '@angular/core';
import { GameState, PhaseType, Player, PlayerState, Role } from '../dtos';
import { GameComponentState, GameStore } from './game.store';
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
  
  vm$ = this.gameStore.vm$;

  currentPlayer!: Player;
  gameName!: string;
  gameState!: GameState;
  players!: Player[];
  currentPhase!: PhaseType;

  constructor(private gameSvc: GameService, private gameStore: GameStore) {}

  ngOnInit(): void {
    console.log('Main Game Initialised!');
    // Override initial state
    this.gameStore.setGameName('testGame');
    this.gameStore.setCurrentPlayer({ name: 'bob', state: PlayerState.ALIVE, role: Role.VILLAGER } as Player);

    // Subscribe to component store observable, update state
    this.vm$.subscribe(
      (state) => {
        this.gameName = state.gameName;
        this.currentPhase = state.currentPhase;
        this.gameState = state.gameState;
        this.players = state.players;
        this.currentPlayer = state.currentPlayer
      }
    )
    // Subscribe to server for game updates
    this.gameSvc.subGame(this.gameName, this.currentPlayer.name);
  }

  setPhaseButton() {
    this.gameStore.setCurrentPhase(PhaseType.GAMEOVER);
  }
  setStateButton() {
    this.gameStore.setGameState(GameState.COMPLETED);
  }
}

