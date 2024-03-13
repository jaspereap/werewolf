export interface Game {
    gameName: string,
    gameState: GameState
    players: Player[]
}

export interface Player {
    name: string,
    role: Role
    state: PlayerState
}

export enum GameState {
    CREATED,
    STARTED,
    COMPLETED
}

export enum PlayerState {
    ALIVE, 
    DEAD
}

// TODO: add new roles here
export enum Role {
    WEREWOLF,
    VILLAGER
}

// TODO: add new phases here
export enum PhaseType {
    INIT, 
    NIGHT, 
    WEREWOLF, 
    WAKE, 
    DAY, 
    EXECUTION, 
    GAMEOVER
}

export interface InboundMessage {
    type: string,
    data: Game
  }