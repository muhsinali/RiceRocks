import java.util.TimerTask;

/**
 * Used to respawn the ship at the center of the screen. TimerTask is extended in order to get the ship to flash when
 * it's respawning, as this indicates to the user that the ship cannot be destroyed by a rock. This respawn period lasts
 * Ship.RESPAWN_PERIOD ms.
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
            // used to get the ship to flash.
            ship.setAlpha(ship.getLifetime() % (2 * TIME_STEP) == 0 ? Ship.HIGH_ALPHA : Ship.LOW_ALPHA);
        }
        ship.setLifetime(ship.getLifetime() + TIME_STEP);
    }
}
