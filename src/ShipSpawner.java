import java.util.TimerTask;

/**
 * Used to respawn the ship properly.
 */
public class ShipSpawner extends TimerTask {
    public static final int TIME_STEP = 500;
    private Ship ship;

    public ShipSpawner(Ship ship){
        this.ship = ship;
    }


    @Override
    public void run(){
        if (ship.isRespawning()){
            ship.setAlpha(ship.getLifetime() % (2 * TIME_STEP) == 0 ? Ship.HIGH_ALPHA : Ship.LOW_ALPHA);
        }
        ship.setLifetime(ship.getLifetime() + TIME_STEP);
    }
}
