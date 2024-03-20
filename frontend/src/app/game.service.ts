import { Injectable } from '@angular/core';
import { MessageService } from './message.service';
import { MessageType } from './models/dtos';
import { LobbyStore } from './lobby/lobby.store';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private messageSvc: MessageService, private lobbyStore: LobbyStore) { }


}
