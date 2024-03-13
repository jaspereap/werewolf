import { Injectable } from '@angular/core';
import { RxStompService } from './rx-stomp.service';
import { Observable } from 'rxjs';
import { Message } from '@stomp/stompjs';
import { environment as env } from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private rxStompService: RxStompService) {}

  subscribeToGame(gameName: string): Observable<Message> {
    console.log(`${env.inboundPrefix}/${gameName}`);
    return this.rxStompService.watch(`${env.inboundPrefix}/${gameName}`);
  }

  publishAck(gameName: string, playerName: string) {
    this.rxStompService.publish({
      destination: `${env.outboundPrefix}/${gameName}/ack`, 
      body: playerName
    })
  }
}
