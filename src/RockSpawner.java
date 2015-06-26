import java.util.TimerTask;

public class RockSpawner extends TimerTask {
    private Play play;

    public RockSpawner(Play play){
        this.play = play;
    }


    @Override
    public void run(){
        if(play.getRocks().size() < Rock.MAX_ROCKS){
            play.getRocks().add(new Rock());
        }
    }
}
