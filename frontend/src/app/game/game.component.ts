import { Component, OnInit } from '@angular/core';
import { Game, GameState, PhaseType, Player, PlayerState, Role } from '../models/dtos';
import { GameStore } from './game.store';
import { provideComponentStore } from '@ngrx/component-store';
import { GameService } from './game.service';
import { Observable, withLatestFrom } from 'rxjs';
import { LobbyStore } from '../lobby/lobby.store';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrl: './game.component.css',
  providers: [provideComponentStore(GameStore)]
})
export class GameComponent implements OnInit {

  constructor(private gameSvc: GameService, 
    private gameStore: GameStore, 
    private lobbyStore: LobbyStore,
    private route: ActivatedRoute) {}

  gameId: string = this.route.snapshot.params['gameId'];

  currentPlayer$: Observable<Player> = this.lobbyStore.currentPlayer$;
  currentGame$: Observable<Game> = this.lobbyStore.currentGame$;

  ngOnInit(): void {
    console.log('Game Initialised!');

    this.currentPlayer$.pipe(withLatestFrom(this.currentGame$)).subscribe(
      ([player, game]) => {
        console.log('Current player: ', player.playerName);
        console.log('Current Game: ', game.gameId);
        this.gameSvc.subscribeGameRoom(game.gameId, player.playerName)
        this.gameSvc.subscribeToPlayer(game.gameId, player.playerName)
      }
    )
  }

  setPhaseButton() {
    this.gameStore.setCurrentPhase(PhaseType.GAMEOVER);
  }
  setStateButton() {
    this.gameStore.setGameState(GameState.COMPLETED);
  }
}

