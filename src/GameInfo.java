import java.util.ArrayList;
import java.util.List;

public class GameInfo {
    private List<Missile> missiles = new ArrayList<>();
    private RockSpawner rockSpawner;
    private List<Rock> rocks = new ArrayList<>(Rock.MAX_ROCKS);
    private Ship ship;


    public GameInfo(){
        rockSpawner = new RockSpawner(this);
    }

    public void createShip(){
        ship = new Ship(this);
    }

    // GETTERS
    public List<Missile> getMissiles(){
        return missiles;
    }

    public List<Rock> getRocks(){
        return rocks;
    }

    public RockSpawner getRockSpawner(){
        return rockSpawner;
    }

    public Ship getShip(){
        return ship;
    }
}
