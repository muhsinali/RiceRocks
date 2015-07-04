import java.util.List;
import java.util.TimerTask;

// Spawns a rock at every TIME_STEP, provided that the max number of rocks hasn't been reached. The rocks are spawned
// outisde of the ship's noRockZone (which is an area surrounding the ship). This is to prevent the ship from getting
// destroyed by a rock before the user has a chance to shoot it.
public class RockSpawner extends TimerTask {
    public static final int TIME_STEP = 2000;
    private List<Rock> rocks;
    private Ship ship;

    public RockSpawner(List<Rock> rocks, Ship ship){
        this.rocks = rocks;
        this.ship = ship;
    }

    private synchronized void spawnRock(){
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

    @Override
    public void run(){
        spawnRock();
    }
}
