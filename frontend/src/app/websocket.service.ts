import { Injectable } from '@angular/core';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';

@Injectable()
export class WebSocketService {
  private subject: WebSocketSubject<any>;
  
  constructor() { 
    console.log("Initialised websocket service")
    this.subject = webSocket('ws://localhost:8080/ws')
  }
  connect() {
    this.subject = new WebSocketSubject('ws://localhost:8080/ws')
    return this.subject;
  }
  sendMessage(message:any) {
    const sampleMessage: SampleMessage = {
      message: message
    }
    this.subject.next(sampleMessage);
  }

  getMessage() {
    return this.subject.asObservable();
  }
}

interface SampleMessage {
  message: string;
}