import java.util.List;
import java.util.TimerTask;

public class RockSpawner extends TimerTask {
    public static final int TIME_STEP = 2000;
    private List<Rock> rocks;

    public RockSpawner(List<Rock> rocks){
        this.rocks = rocks;
    }


    @Override
    public void run(){
        if(rocks.size() < Rock.MAX_ROCKS){
            rocks.add(new Rock());
        }
    }
}
