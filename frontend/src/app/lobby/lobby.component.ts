import { Component, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { LobbyService } from '../lobby.service';
import { Player } from '../dtos';
import { provideComponentStore } from '@ngrx/component-store';
import { of, switchMap, tap } from 'rxjs';


interface FormData {
  currentPlayerName: string,
  gameName: string
}

@Component({
  selector: 'app-lobby',
  templateUrl: './lobby.component.html',
  providers: provideComponentStore(LobbyService),
  styleUrl: './lobby.component.css'
})
export class LobbyComponent implements OnInit {
  newRoomForm!: FormGroup;
  
  currentPlayer!: Player;
  games$ = this.lobbySvc.games$;
  
  constructor(private fb: FormBuilder, private lobbySvc: LobbyService) {}

  ngOnInit(): void {
    this.newRoomForm = this.initForm();
    console.log('Lobby Component Init')
    console.log('getting games')
    this.lobbySvc.getGames();
  }

  processNewRoom() {
    const formData = this.newRoomForm.value as FormData;
    const gameName = formData.gameName;

    this.lobbySvc.createGame(of({
      currentPlayer: this.currentPlayer,
      gameName: gameName
    }))
  }

  setPlayer(name: string) {
    console.log("Current Player set: ", name);
    this.currentPlayer = this.newPlayer(name);
  }

  initForm(): FormGroup {
    return this.fb.group({
      // currentPlayerName: this.fb.control<string>('bob'),
      gameName: this.fb.control<string>('testGame')
    })
  }

  newPlayer(name: string) {
    return {name: name } as Player;
  }
}

