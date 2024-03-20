import { Component, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { Game, Player, PlayerState } from '../../models/dtos';
import { ActivatedRoute } from '@angular/router';
import { LobbyStore } from '../lobby.store';
import { Observable, Subscription } from 'rxjs';
import { LocalStoreService } from '../../local-store.service';
import { LobbyService } from '../lobby.service';
import { MessageService } from '../../message.service';
import { GameRoomService } from './game-room.service';

@Component({
  selector: 'app-game-room',
  templateUrl: './game-room.component.html',
  styleUrl: './game-room.component.css',
  providers: [GameRoomService]
})
export class GameRoomComponent implements OnInit, OnDestroy, OnChanges{
  gameName: string = this.route.snapshot.params['gameName'];

  currentPlayer$: Observable<Player> = this.lobbyStore.currentPlayer$;
  currentGame$: Observable<Game> = this.lobbyStore.currentGame$;
  
  // currentGamePlayers$: Observable<Player[]> = this.lobbyStore.currentGamePlayers$;
  // currentGamePlayers$: Observable<Player[]> = this.currentGame$.pipe(map(game => game.players));

  // Sub management
  SgameRoom!: Subscription
  ScurrentPlayer$!: Subscription
  ScurrentGame$!: Subscription

  constructor(private lobbyStore: LobbyStore, 
      private route: ActivatedRoute, 
      private localStore: LocalStoreService,
      private lobbyService: LobbyService,
      private messageSvc: MessageService,
      private gameRoomService: GameRoomService) {}
  
  ngOnInit(): void {
    console.log('Game Room Component Init')
    this.ScurrentGame$ = this.currentGame$.subscribe(
      (game) => {
        console.log('current game: ', game)
        console.log('current game players: ', game.players)
      }
    )
    this.ScurrentPlayer$ = this.currentPlayer$.subscribe(
      (player) => {
        console.log('current player: ', player)
        this.SgameRoom = this.gameRoomService.subscribeGameRoom(this.gameName, player.playerName)
      }
    )

  }
  
  startGame() {
    
  }
  
  leaveGame() {
    console.log("Leave Game Button")
    this.lobbyStore.leaveGame();
  }

  testAddPlayer() {
    this.lobbyStore.addPlayer({playerName: 'lol', playerState: PlayerState.ALIVE});
  }
  ngOnDestroy(): void {
    console.log('Game Room Component Destroyed');
    this.SgameRoom.unsubscribe();
    this.ScurrentPlayer$.unsubscribe()
    this.ScurrentGame$.unsubscribe()
  }
  ngOnChanges(changes: SimpleChanges) {
    console.log('Game-room changed:', changes);
  }
}
