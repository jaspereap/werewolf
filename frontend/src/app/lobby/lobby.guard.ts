import { inject } from '@angular/core';
import { CanDeactivateFn } from '@angular/router';
import { LobbyStore } from './lobby.store';

export const triggerLeaveRoom: CanDeactivateFn<unknown> = (component, currentRoute, currentState, nextState) => {
  console.log('In triggerLeaveRoom')
  const lobbyStore = inject(LobbyStore);
  if (nextState && nextState['url'] === '/lobby') {
    console.log('Triggering leave game')
    lobbyStore.leaveGame()
    return true;
  }
  console.log('Leave game not triggered')
  return true;
};
