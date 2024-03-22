import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { GameComponent } from './game/game.component';
import { AppComponent } from './app.component';
import { TestComponent } from './test/test.component';
import { GameRoomComponent } from './lobby/game-room/game-room.component';
import { LobbyComponent } from './lobby/lobby.component';
import { authGuard } from './auth/auth.guard';
import { triggerLeaveRoom } from './lobby/lobby.guard';

const routes: Routes = [
  {path: '', component: AppComponent},
  {path: 'test', component: TestComponent},
  {path: 'game/:gameName', component: GameComponent},
  {path: 'lobby', component: LobbyComponent, canActivate: [authGuard]},
  {
    path: 'room/:gameName', component: GameRoomComponent,
    runGuardsAndResolvers: 'always',
    canActivate: [authGuard], 
    canDeactivate: [triggerLeaveRoom]
  },
  {path: '**', redirectTo: '/lobby', pathMatch: 'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
