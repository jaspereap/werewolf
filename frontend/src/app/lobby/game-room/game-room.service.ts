import { Injectable } from '@angular/core';
import { MessageService } from '../../message.service';
import { LobbyStore } from '../lobby.store';
import { MessageType, Player } from '../../models/dtos';

@Injectable()
export class GameRoomService {

  constructor(private messageSvc: MessageService, private lobbyStore: LobbyStore) { }
  // Listen for player join/leave, game start
  subscribeGameRoom(gameName: string, playerName: string) {
    return this.messageSvc.subscribe(gameName).subscribe(
        ({headers, body}) => {
            this.publishACK(gameName, playerName)

            switch(headers['type'] as MessageType) {
              case MessageType.ACK: {
                console.log('Server Acknowledged')
                break;
              }
              case MessageType.PLAYER_JOINED: {
                console.log('Player_joined message received')
                console.log('Player_joined body: ', body)
                // this.lobbyStore.updateCurrentGamePlayers(JSON.parse(body) as Player)
                // this.lobbyStore.getCurrentGame([playerName, gameName]);
                console.log('Adding player to store')
                this.lobbyStore.addPlayer(JSON.parse(body) as Player);
                break;
              }
              case MessageType.PLAYER_LEFT: {
                console.log('Player_left message received')
                console.log('Player_left body: ', body)
                // this.lobbyStore.getCurrentGame([playerName, gameName]);
                this.lobbyStore.removePlayer(JSON.parse(body) as Player)
                break;
              }
            }
        }
    )
  }
  unsubscribeGameRoom() {

  }
  
  publishACK(gameName: string, playerName: string) {
    const ackEndpoint = 'ack'
    this.messageSvc.publish(`${gameName}/${ackEndpoint}`, playerName, MessageType.ACK);
  }
}
