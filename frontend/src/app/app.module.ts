import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { WebSocketService } from './websocket.service';
import { RxStompService } from './rxStomp/rx-stomp.service';
import { rxStompServiceFactory } from './rxStomp/rx-stomp-service-factory';
import { GameComponent } from './game/game.component';
import { TestComponent } from './test/test.component';
import { MessageService } from './message.service';
import { GameRoomComponent } from './lobby/game-room/game-room.component';
import { ReactiveFormsModule } from '@angular/forms';
import { LobbyComponent } from './lobby/lobby.component';
import { GameListComponent } from './lobby/game-list/game-list.component';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  declarations: [
    AppComponent,
    GameComponent,
    TestComponent,
    GameRoomComponent,
    LobbyComponent,
    GameListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    HttpClientModule
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
