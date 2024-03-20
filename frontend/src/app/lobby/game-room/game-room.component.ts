import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { Game, Player } from '../../models/dtos';
import { ActivatedRoute } from '@angular/router';
import { LobbyStore } from '../lobby.store';
import { Observable, map } from 'rxjs';
import { LocalStoreService } from '../../local-store.service';
import { GameService } from '../../game.service';
import { LobbyService } from '../lobby.service';

@Component({
  selector: 'app-game-room',
  templateUrl: './game-room.component.html',
  styleUrl: './game-room.component.css'
})
export class GameRoomComponent implements OnInit, OnDestroy{
  currentPlayer$: Observable<Player> = this.lobbyStore.currentPlayer$;
  @Input()
  currentGame$: Observable<Game> = this.lobbyStore.currentGame$;
  gameName: string = this.route.snapshot.params['gameName'];
  
  // currentGamePlayers$: Observable<Player[]> = this.lobbyStore.currentGamePlayers$;
  // currentGamePlayers$: Observable<Player[]> = this.currentGame$.pipe(map(game => game.players));

  constructor(private lobbyStore: LobbyStore, 
      private route: ActivatedRoute, 
      private localStore: LocalStoreService,
      private lobbyService: LobbyService) {}
  
  ngOnInit(): void {
    console.log('Game Room Component Init')
    // this.currentGame$.subscribe(
    //   (game) => {
    //     console.log('current game: ', game)
    //     console.log('current game players: ', game.players)
    //   }
    // )
    // this.currentPlayer$.subscribe(
    //   (player) => console.log('current player: ', player)
    // )

    this.lobbyService.subscribeGameRoom(this.gameName, this.localStore.getCurrentPlayerName())
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
