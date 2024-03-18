import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { RxStompService } from '../rx-stomp.service';
import { Message } from '@stomp/stompjs';

import { Game } from '../dtos';
import { MessageService } from '../message.service';
import { GameService } from '../game.service';

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

    constructor(private gameSvc: GameService) {}
  
    
    ngOnInit(): void {
      this.gameSvc.subscribeGameRoom(this.gameName, this.playerName);
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

