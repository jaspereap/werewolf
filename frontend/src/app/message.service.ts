import { Injectable } from '@angular/core';
import { RxStompService } from './rxStomp/rx-stomp.service';
import { Observable, map, tap } from 'rxjs';
import { Message } from '@stomp/stompjs';
import { environment as env } from "../environments/environment";
import { MessageType } from './models/dtos';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  constructor(private rxStompService: RxStompService) {}

  subscribe(topic: string) {
    console.log(`WATCHING: ${env.inboundPrefix}/${topic}`);
    return this.rxStompService.watch(`${env.inboundPrefix}/${topic}`).pipe(
      map((message) => {
        const headers = message.headers;
        const body = message.body;
        return { headers, body }
      }),
      tap(({ headers, body }) => {
        console.log('INCOMING headers: ', headers);
        console.log('INCOMING type header: ', headers['type'] as MessageType);
        console.log('INCOMING body: ', body);
      })
    )
  }
  unsubscribe() {
    this.rxStompService.deactivate();
  }
  publish(topic: string, playerName: string, type: MessageType) {
    console.log(`SENDING: ${env.outboundPrefix}/${topic}`);
    const headers = {'type': type.toString()};
    const body = playerName;
    console.log('OUTGOING headers: ', headers)
    console.log('OUTGOING body: ', body)
    this.rxStompService.publish({
      destination: `${env.outboundPrefix}/${topic}`,
      headers: headers,
      body: body
    })
  }
}
