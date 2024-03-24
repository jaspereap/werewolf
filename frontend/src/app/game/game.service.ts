import { Injectable } from '@angular/core';
import { MessageService } from '../shared/message.service';
import { MessageType } from '../models/dtos';
import { LobbyStore } from '../lobby/lobby.store';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private messageSvc: MessageService, private lobbyStore: LobbyStore) { }

  subscribeToPlayer(gameName: string, playerName: string) {
    console.log('subbed to player')
    return this.messageSvc.subscribe(`${gameName}/${playerName}`).subscribe(
      ({headers, body}) => {
        console.log('headers: ', headers)
        console.log('body: ', body)

        switch(headers['type'] as MessageType) {
          case MessageType.ACK: {
            console.log('Server PLAYER Acknowledged')
            break;
          }

        }
      }
    );
  }
  subscribeGameRoom(gameName: string, playerName: string) {
    console.log('subbed to game')
    return this.messageSvc.subscribe(gameName).subscribe(
      ({headers, body}) => {
          
            switch(headers['type'] as MessageType) {
              case MessageType.ACK: {
                console.log('Server GAME Acknowledged')
                break;
              }
              case MessageType.START_GAME: {
                console.log('START_GAME GAME MESSAGE RECEIVED')
                this.publishAck(gameName, playerName, MessageType.START_GAME);
                break;
              }
            }
        }
    )
  }

  publishAck(gameName: string, playerName: string, type: MessageType) {
    return this.messageSvc.publish(`${gameName}/${playerName}/ack`, playerName, type)
  }
}
