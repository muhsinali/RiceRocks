public enum GameState {
    PLAY(0),
    GAME_WON(1),
    GAME_LOST(2);

    private int id;

    GameState(int id){
        this.id = id;
    }

    public int getID(){
        return id;
    }
}
