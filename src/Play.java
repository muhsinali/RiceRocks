import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Date;
import java.util.Timer;


public class Play extends BasicGameState{
    private Image backgroundImage;
    private Timer timer = new Timer();
    private GameInfo gameInfo;

    private Ship ship;


    @Override
    public int getID(){
        return Game.PLAY;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg){
        try{
            backgroundImage = new Image("res/images/nebula_blue.png");
            gameInfo = new GameInfo();
            gameInfo.createShip();
            ship = gameInfo.getShip();
            gameInfo.addRock(new Rock());  // removing this produces a bug. find out why.
            timer.schedule(gameInfo.getRockSpawner(), new Date(), 1000);
        } catch(SlickException e){
            e.printStackTrace();
        }
    }



    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
        g.drawImage(backgroundImage, 0, 0);
        g.drawImage(ship.getCurrentImage(), ship.getPositionX(), ship.getPositionY());
        for (Rock rock : gameInfo.getRocks()) {
            g.drawImage(rock.getCurrentImage(), rock.getPositionX(), rock.getPositionY());
        }
        for(Missile missile : gameInfo.getMissiles()){
            g.drawImage(missile.getCurrentImage(), missile.getPositionX(), missile.getPositionY());
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta){
        Input input = gc.getInput();
        ship.move(input);
        for (Rock rock : gameInfo.getRocks()) {
            rock.move();
        }
        for (Missile missile : gameInfo.getMissiles()){
            missile.move();
        }

        // Shoot missiles
        ship.shoot(input);


        // Collisions
        // TODO Kill the ship - add lives later.
//        for(Rock rock : rocks) {
//            if (ship.getCollider().intersects(rock.getCollider())) {
//                System.out.println("Ow!");
//            }
//        }

    }

}

