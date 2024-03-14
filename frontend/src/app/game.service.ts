import { Injectable } from '@angular/core';
import { MessageService } from './message.service';
import { Message } from '@stomp/stompjs';
import { InboundMessage } from './dtos';

@Injectable({
  providedIn: 'root'
})
export class GameService {

  constructor(private messageSvc: MessageService) { }
    // Fetch game
    subscribeGame(gameName: string, playerName: string) {
      this.messageSvc.subscribeToGame(gameName).subscribe(
          (msg: Message) => {
              this.messageSvc.publishAck(gameName, playerName); // Send ack
              const inbound: InboundMessage = JSON.parse(msg.body);
              console.log(inbound);
          }
      )
  }
}
