import { Component, Input } from '@angular/core';
import { Game } from '../../dtos';

@Component({
  selector: 'app-game-list',
  templateUrl: './game-list.component.html',
  styleUrl: './game-list.component.css'
})
export class GameListComponent {
  @Input()
  games: Game[] | undefined
  
}
