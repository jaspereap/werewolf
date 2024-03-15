export interface Game {
    gameName: string,
    gameState: GameState
    players: Player[]
}

export interface Player {
    playerName: string,
    playerState: PlayerState
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
// STOMP
export interface InboundMessage {
    type: string,
    data: Game
}
// HTTP
export interface GameComponentState {
    // currentPlayer: Player;
    gameName: string;
    gameState: GameState;
    players: Player[];
    currentPhase: PhaseType;
}
// HTTP
export interface CreateGameRequest {
    gameName: string;
    playerName: string;
}