import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Game } from '../../dtos';
import { LobbyService } from '../../lobby.service';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrl: './game-list.component.css'
})
export class GameListComponent implements OnInit, OnChanges {
  @Input()
  games: Game[] | null = []

  constructor(private lobbySvc: LobbyService) {

  }
  ngOnInit(): void {
    console.log("Game List Component Init")
  }
  joinRoom(gameName: string) {
    console.log("Joining room: ", gameName)
    this.lobbySvc.joinGame();
  }
  ngOnChanges(changes: SimpleChanges) {
    console.log('Game-list changed:', changes);
  }
  
}
