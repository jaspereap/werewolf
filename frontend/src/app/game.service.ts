import { Injectable } from '@angular/core';
import { MessageService } from './message.service';
import { MessageType } from './dtos';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private messageSvc: MessageService) { }

    subscribeGameRoom(gameName: string, playerName: string) {
      return this.messageSvc.subscribe(gameName).subscribe(
          ({headers, body}) => {
              this.publishACK(gameName, playerName)
          }
      )
    }

    publishACK(gameName: string, playerName: string) {
      const ackEndpoint = 'ack'
      this.messageSvc.publish(`${gameName}/${ackEndpoint}`, playerName, MessageType.ACK);
    }


}
