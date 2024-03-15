import { Component, OnDestroy, OnInit } from '@angular/core';
import { Game, Player } from '../../dtos';
import { ActivatedRoute } from '@angular/router';
import { LobbyStore } from '../lobby.store';
import { Observable } from 'rxjs';
import { LocalStoreService } from '../../local-store.service';

@Component({
  selector: 'app-game-room',
  templateUrl: './game-room.component.html',
  styleUrl: './game-room.component.css'
})
export class GameRoomComponent implements OnInit, OnDestroy{
  currentPlayer$: Observable<Player> = this.lobbyStore.currentPlayer$;
  currentGame$: Observable<Game> = this.lobbyStore.currentGame$;
  gameName: string = this.route.snapshot.params['gameName'];
  constructor(private lobbyStore: LobbyStore, private route: ActivatedRoute, private localStore: LocalStoreService) {}
  
  
  ngOnInit(): void {
    console.log('Current player name: ' + this.localStore.getCurrentPlayerName())
    this.lobbyStore.getCurrentGame([this.localStore.getCurrentPlayerName(), this.gameName]);

    this.currentGame$.subscribe(
      (game) => console.log('current game: ', game)
    )
    this.currentPlayer$.subscribe(
      (player) => console.log('current player: ', player)
    )
  }
  
  startGame() {
    
  }
  
  leaveGame() {
    console.log("Leave Game Button")
    this.lobbyStore.leaveGame();
  }
  
  ngOnDestroy(): void {
    console.log('Game Room Component Destroyed');
  }
}
