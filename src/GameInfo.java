import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameInfo {
    private List<Emp> emps = Collections.synchronizedList(new ArrayList<>());
    private List<Rock> rocks = Collections.synchronizedList(new ArrayList<>(Rock.MAX_ROCKS));
    private Ship ship = new Ship(this);
    private EmpManager empManager = new EmpManager(emps);
    private RockSpawner rockSpawner = new RockSpawner(rocks, ship);
    private ShipSpawner shipSpawner = new ShipSpawner(ship);

    private int livesRemaining = 3;
    private int score;


    public void deductLife(){
        livesRemaining--;
    }


    // GETTERS
    public synchronized List<Emp> getEmps(){
        return emps;
    }

    public EmpManager getEmpManager(){
        return empManager;
    }

    public int getLivesRemaining(){
        return livesRemaining;
    }

    public synchronized List<Rock> getRocks(){
        return rocks;
    }

    public RockSpawner getRockSpawner(){
        return rockSpawner;
    }

    public int getScore(){
        return score;
    }

    public Ship getShip(){
        return ship;
    }

    public ShipSpawner getShipSpawner(){
        return shipSpawner;
    }


    // SETTERS
    public void setScore(int newScore){
        score = newScore;
    }

}
