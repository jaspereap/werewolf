import { Component, Input, OnChanges, OnDestroy, OnInit, Output, SimpleChanges } from '@angular/core';
import { Game, Player } from '../../models/dtos';
import { LobbyStore } from '../lobby.store';
import { Subject, of } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrl: './game-list.component.css'
})
export class GameListComponent implements OnInit, OnChanges, OnDestroy {
  @Input() games: Game[] | null = []
  @Input() currentPlayer: Player | null = null;
  @Output() joinRoomEvent = new Subject<string>;
  constructor(private lobbyStore: LobbyStore, private router: Router) {}

  ngOnInit(): void {
    console.log("Game List Component Init")
  }
  joinRoom(gameName: string) {
    this.joinRoomEvent.next(gameName);
  }
  ngOnChanges(changes: SimpleChanges) {
    console.log('Game-list changed:', changes);
  }
  ngOnDestroy(): void {
    console.log('Game List Component Destroyed');
  }
  
}
