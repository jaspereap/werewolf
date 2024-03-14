import { ComponentStore, OnStoreInit } from "@ngrx/component-store";
import { GameComponentState, GameState, PhaseType, Player, PlayerState, Role } from "../dtos";
import { Injectable } from "@angular/core";
import { tap } from "rxjs";

@Injectable()
export class GameStore extends ComponentStore<GameComponentState> implements OnStoreInit {
    // Read values from state
    // Defining Observable using select operator -> emits values whenever selected state changes in store.
    // private readonly currentPlayer$ = this.select((state) => state.currentPlayer).pipe(tap((player) => {console.log("currentPlayer$! ",player)}))
    private readonly gameName$ = this.select((state) => state.gameName).pipe(tap((gameName) => {console.log("gameName$! ",gameName)}))
    private readonly gameState$ = this.select((state) => state.gameState).pipe(tap((gameState) => {console.log("gameState$! ",gameState)}))
    private readonly players$ = this.select((state) => state.players).pipe(tap((players) => {console.log("players$! ",players)}))
    private readonly currentPhase$ = this.select((state) => state.currentPhase).pipe(tap((currentPhase) => {console.log("currentPhase$! ",currentPhase)}))

    // Encapsulate, bundle multiple state observable into one.
    readonly vm$ = this.select({
        // currentPlayer: this.currentPlayer$,
        gameName: this.gameName$,
        gameState: this.gameState$,
        players: this.players$,
        currentPhase: this.currentPhase$
    })

    constructor() {
        super({
            // currentPlayer: {name: 'test', role: Role.VILLAGER, state: PlayerState.ALIVE} as Player,
            gameName: 'Default',
            gameState: GameState.CREATED,
            players: [],
            currentPhase: PhaseType.INIT
        })
    }
    ngrxOnStoreInit(): void {
        console.log("Store Init");
    };

    // Update State 
    // updater -> Take current state and argument object, make amendments, return new state.
    // setCurrentPlayer = this.updater(
    //     (state, player:Player) => ({
    //         ...state, 
    //         currentPlayer: player})
    // );
    setGameName = this.updater(
        (state, gameName:string) => ({
            ...state, 
            gameName: gameName})
    );
    setGameState = this.updater(
        (state, gameState:GameState) => ({
            ...state, 
            gameState: gameState})
    );
    setCurrentPhase = this.updater(
        (state, currentPhase:PhaseType) => ({
            ...state, 
            currentPhase: currentPhase})
    );
    setPlayers = this.updater(
        (state, players:Player[]) => ({
            ...state, 
            players: players})
    );
    addPlayer = this.updater(
        (state, player: Player) => ({
            ...state, 
            players: [...state.players, player]})
    );

}