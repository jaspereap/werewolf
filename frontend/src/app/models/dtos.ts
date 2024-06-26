export interface Game {
    gameName: string,
    gameState: GameState
    players: Player[],
    gameId: string
}

export interface Player {
    playerName: string,
    playerId: string,
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
    gameId: string;
}
// HTTP
export interface CreateGameRequest {
    gameName: string;
    playerName: string;
    playerId: string;
}

export enum MessageType {
    ACK = 'ACK',
    JOIN_ROOM = 'JOIN_ROOM',
    PLAYER_JOINED = 'PLAYER_JOINED',
    PLAYER_LEFT = 'PLAYER_LEFT',
    INIT_GAME = 'INIT_GAME',
    START_GAME = 'START_GAME'
}