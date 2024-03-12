import { Component, OnInit } from '@angular/core';
import { WebsocketService } from './websocket.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent implements OnInit {
  title = 'WebSocket Example';

  constructor(private wsService: WebsocketService) {}

  ngOnInit(): void {
    this.wsService.connect().asObservable().subscribe(
      () => console.log('WebSocket connection established'),
      (error) => console.error('WebSocket connection error:', error),
      () => console.log('WebSocket connection closed')
    );
  }

  sendMessage(message: string) {
    console.log(">> Send Button pressed", message)
    this.wsService.send(message);
  }
  closeSocket() {
    this.wsService.getSubject().complete();
  }

  connect() {
    console.log("Connecting..")
    this.wsService.connect().asObservable().subscribe(
      () => console.log('WebSocket connection established'),
      (error) => console.error('WebSocket connection error:', error),
      () => console.log('WebSocket connection closed')
    );
  }
}
