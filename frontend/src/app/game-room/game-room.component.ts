import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-game-room',
  templateUrl: './game-room.component.html',
  styleUrl: './game-room.component.css'
})
export class GameRoomComponent implements OnInit{
  form!: FormGroup;
  constructor(private fb: FormBuilder) {}
  ngOnInit(): void {
    this.form = this.createForm();
  }
  createForm(): FormGroup {
    return this.fb.group({
      currentPlayerName: this.fb.control<string>(''),
      gameName: this.fb.control<string>('')
    })
  }
  startGame() {

  }
}
