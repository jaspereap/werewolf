import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { RxStompService } from '../rxStomp/rx-stomp.service';
import { Message } from '@stomp/stompjs';

import { Game } from '../models/dtos';
import { MessageService } from '../message.service';
import { GameService } from '../game.service';
import { LobbyService } from '../lobby/lobby.service';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrl: './test.component.css'
})

  export class TestComponent implements OnInit, OnDestroy {
    title = 'WebSocket Example';
    gameName = 'gameName';
    playerName = 'bob';

    topicSub!: Subscription;

    constructor(private lobbyService: LobbyService) {}
  
    
    ngOnInit(): void {
      // this.lobbyService.subscribeGameRoom(this.gameName, this.playerName);
    }
  
    // sendMessage(input: string) {
    //   console.log(">> Send Button pressed", input)
    //   const message : SampleMessage = {
    //     message: input
    //   }
  
    //   this.rxStompService.publish({
    //     destination: '/app/message',
    //     body: JSON.stringify(message)
    //   })
    // }
  
    ngOnDestroy(): void {
      this.topicSub.unsubscribe();
    }
  }

