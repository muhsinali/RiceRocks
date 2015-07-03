import java.util.List;
import java.util.TimerTask;

public class RockSpawner extends TimerTask {
    public static final int TIME_STEP = 2000;
    private List<Rock> rocks;
    private Ship ship;

    public RockSpawner(List<Rock> rocks, Ship ship){
        this.rocks = rocks;
        this.ship = ship;
    }


    @Override
    public void run(){
        if(rocks.size() < Rock.MAX_ROCKS){
            Rock rock = new Rock();
            boolean keepGoing = true;
            while(keepGoing) {
                if (!ship.isRockInZone(rock)) {
                    rocks.add(rock);
                    keepGoing = false;
                } else {
                    rock.resetPosition();
                }
            }
        }
    }
}
