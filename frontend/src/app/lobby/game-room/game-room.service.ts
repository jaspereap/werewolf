import { Injectable } from '@angular/core';
import { MessageService } from '../../shared/message.service';
import { LobbyStore } from '../lobby.store';
import { MessageType, Player } from '../../models/dtos';
import { Router } from '@angular/router';

@Injectable()
export class GameRoomService {

  constructor(private messageSvc: MessageService, private lobbyStore: LobbyStore, private router: Router) { }
  // Listen for player join/leave, game start
  subscribeGameRoom(gameId: string, playerName: string) {
    return this.messageSvc.subscribe(gameId).subscribe(
        ({headers, body}) => {

            switch(headers['type'] as MessageType) {
              case MessageType.ACK: {
                console.log('Server GAME Acknowledged')
                break;
              }
              case MessageType.PLAYER_JOINED: {
                console.log('Player_joined GAME message received')
                console.log('Player_joined body: ', body)
                // this.lobbyStore.updateCurrentGamePlayers(JSON.parse(body) as Player)
                // this.lobbyStore.getCurrentGame([playerName, gameName]);
                console.log('Adding player to store')
                this.lobbyStore.addPlayer(JSON.parse(body) as Player);
                break;
              }
              case MessageType.PLAYER_LEFT: {
                console.log('Player_left GAME message received')
                console.log('Player_left body: ', body)
                // this.lobbyStore.getCurrentGame([playerName, gameName]);
                this.lobbyStore.removePlayer(JSON.parse(body) as Player)
                break;
              }
              case MessageType.INIT_GAME: {
                console.log('Init_game GAME message received')
                this.publishAck(gameId, playerName, MessageType.INIT_GAME)
                this.router.navigate(['/game', gameId])
                break;
              }
            }
        }
    )
  }
  unsubscribeGameRoom() {

  }
  

  publishAck(gameName: string, playerName: string, type: MessageType) {
    return this.messageSvc.publish(`${gameName}/${playerName}/ack`, playerName, type)
  }
}
