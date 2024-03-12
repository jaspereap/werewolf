import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { RxStompService } from '../rx-stomp.service';
import { Message } from '@stomp/stompjs';
import { SampleMessage } from '../SampleMessage';

@Component({
  selector: 'app-test',
  templateUrl: './test.component.html',
  styleUrl: './test.component.css'
})

  export class TestComponent implements OnInit, OnDestroy {
    title = 'WebSocket Example';
    topicSub!: Subscription;
    constructor(private rxStompService: RxStompService) {}
  
    
    ngOnInit(): void {
      this.topicSub = this.rxStompService.watch('/topic/message').subscribe(
        (message: Message) => {
          console.log(message.body)
        }
      )
    }
  
    sendMessage(input: string) {
      console.log(">> Send Button pressed", input)
      const message : SampleMessage = {
        message: input
      }
  
      this.rxStompService.publish({
        destination: '/app/message',
        body: JSON.stringify(message)
      })
    }
  
    ngOnDestroy(): void {
      this.topicSub.unsubscribe();
    }
  }

