import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GameComponent } from './game/game.component';
import { AppComponent } from './app.component';
import { TestComponent } from './test/test.component';
import { GameRoomComponent } from './lobby/game-room/game-room.component';
import { LobbyComponent } from './lobby/lobby.component';

const routes: Routes = [
  {path: '', component: AppComponent},
  {path: 'test', component: TestComponent},
  {path: 'main', component: GameComponent},
  {path: 'lobby', component: LobbyComponent},
  {path: 'room/:gameName', component: GameRoomComponent},
  {path: '**', redirectTo: '/lobby', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
