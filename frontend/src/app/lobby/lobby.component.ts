import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { LobbyService } from '../lobby.service';
import { Game, GameState, Player, PlayerState } from '../dtos';
import { switchMap } from 'rxjs';

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  styleUrl: './lobby.component.css'
})
export class LobbyComponent {
  newRoomForm!: FormGroup;
  currentPlayer!: Player;
  games!: Game[];
  
  constructor(private fb: FormBuilder, private lobbySvc: LobbyService) {
    this.games = [{gameName: 'Sample Game', gameState: GameState.CREATED, players: []} as Game]
  }
  ngOnInit(): void {
    this.newRoomForm = this.initForm();
    this.fetchGames();
  }

  processNewRoom() {
    const formData = this.newRoomForm.value as FormData;
    const gameName = formData.gameName;
    const currentPlayerName = formData.currentPlayerName;
    // this.lobbySvc.createGame(this.newPlayer(currentPlayerName), gameName).subscribe(
    //   () => {
    //     this.fetchGames();
    //   }
    // );
    this.lobbySvc.createGame(this.newPlayer(currentPlayerName), gameName)
    .pipe(
      switchMap(() => this.lobbySvc.getGames())
    )
    .subscribe(
      (games) => {
        this.games = games;
      }
    );
  
  }

  initForm(): FormGroup {
    return this.fb.group({
      currentPlayerName: this.fb.control<string>('bob'),
      gameName: this.fb.control<string>('testGame')
    })
  }

  newPlayer(name: string) {
    return {name: name } as Player;
  }

  fetchGames() {
    this.lobbySvc.getGames().subscribe(
      (games) => {
        this.games = games;
      }
    )
  }
}

interface FormData {
  currentPlayerName: string,
  gameName: string
}