import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;


public class Play extends BasicGameState{
    private Image backgroundImage;
    private Ship ship;
    private Timer timer = new Timer();
    private List<Rock> rocks = new ArrayList<>(Rock.MAX_ROCKS);
    private RockSpawner rockSpawner;

    @Override
    public int getID(){
        return Game.PLAY;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg){
        try{
            backgroundImage = new Image("res/images/nebula_blue.png");
            ship = new Ship("res/images/double_ship.png");

            rocks.add(new Rock());
            rockSpawner = new RockSpawner(this);
            timer.schedule(rockSpawner, new Date(), 1000);
        } catch(SlickException e){
            e.printStackTrace();
        }
    }



    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
        g.drawImage(backgroundImage, 0, 0);
        g.drawImage(ship.getCurrentImage(), ship.getPositionX(), ship.getPositionY());
        for (Rock rock : rocks) {
            g.drawImage(rock.getCurrentImage(), rock.getPositionX(), rock.getPositionY());
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta){
        Input input = gc.getInput();
        ship.move(input);
        for (Rock rock : rocks) {
            rock.move();
        }

        // Collisions
        // TODO Kill the ship - add lives later.
//        for(Rock rock : rocks) {
//            if (ship.getCollider().intersects(rock.getCollider())) {
//                System.out.println("Ow!");
//            }
//        }

    }


    // GETTERS
    public List<Rock> getRocks(){
        return rocks;
    }
}

