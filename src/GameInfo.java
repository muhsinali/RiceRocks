import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameInfo {
    private List<Emp> emps = new ArrayList<>();
    private List<Rock> rocks = new ArrayList<>(Rock.MAX_ROCKS);
    private Ship ship = new Ship(this);
    private EmpManager empManager = new EmpManager(emps);
    private RockSpawner rockSpawner = new RockSpawner(rocks);
    private ShipSpawner shipSpawner = new ShipSpawner(ship);

    private int livesRemaining = 3;
    private int score;


    public void deductLife(){
        livesRemaining--;
    }

    // GETTERS
    public List<Emp> getEmps(){
        return Collections.synchronizedList(emps);
    }

    public EmpManager getEmpManager(){
        return empManager;
    }

    public int getLivesRemaining(){
        return livesRemaining;
    }

    public List<Rock> getRocks(){
        return Collections.synchronizedList(rocks);
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
