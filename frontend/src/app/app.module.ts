import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { WebSocketService } from './websocket.service';
import { RxStompService } from './rx-stomp.service';
import { rxStompServiceFactory } from './rx-stomp-service-factory';
import { GameComponent } from './game/game.component';
import { TestComponent } from './test/test.component';
import { MessageService } from './message.service';

@NgModule({
  declarations: [
    AppComponent,
    GameComponent,
    TestComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [
    WebSocketService,
    MessageService,
    {
      provide: RxStompService,
      useFactory: rxStompServiceFactory
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
