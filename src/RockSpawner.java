import java.util.TimerTask;

public class RockSpawner extends TimerTask {
    private GameInfo gameInfo;

    public RockSpawner(GameInfo gameInfo){
        this.gameInfo = gameInfo;
    }


    @Override
    public void run(){
        if(gameInfo.getRocks().size() < Rock.MAX_ROCKS){
            gameInfo.getRocks().add(new Rock());
        }
    }
}
