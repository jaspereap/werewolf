import { Component, Input, OnChanges, OnDestroy, OnInit, SimpleChanges } from '@angular/core';
import { Game, Player } from '../../dtos';
import { LobbyStore } from '../lobby.store';
import { of } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrl: './game-list.component.css'
})
export class GameListComponent implements OnInit, OnChanges, OnDestroy {
  @Input() games: Game[] | null = []
  @Input() currentPlayer: Player | null = null;

  constructor(private lobbyStore: LobbyStore, private router: Router) {}

  ngOnInit(): void {
    console.log("Game List Component Init")
  }
  joinRoom(gameName: string) {
    console.log("Joining room: ", gameName)
    this.lobbyStore.joinGame(of(gameName));
    this.lobbyStore.getGames();
    // this.router.navigate(['/room'])
  }
  ngOnChanges(changes: SimpleChanges) {
    console.log('Game-list changed:', changes);
  }
  ngOnDestroy(): void {
    console.log('Game List Component Destroyed');
  }
  
}
