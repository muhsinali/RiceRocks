import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.util.Date;
import java.util.List;
import java.util.Timer;


public class Play extends BasicGameState{
    private List<Emp> emps;
    private List<Rock> rocks;
    private Ship ship;

    private Image backgroundImage;
    private Timer timer = new Timer();


    @Override
    public int getID(){
        return Game.PLAY;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg){
        try{
            backgroundImage = new Image("res/images/nebula_blue.png");
            GameInfo gameInfo = new GameInfo();
            gameInfo.createShip();
            ship = gameInfo.getShip();
            rocks = gameInfo.getRocks();
            emps = gameInfo.getEmps();
            rocks.add(new Rock());  // removing this produces a bug. find out why.
            timer.schedule(gameInfo.getRockSpawner(), new Date(), 1000);
        } catch(SlickException e){
            e.printStackTrace();
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
        g.drawImage(backgroundImage, 0, 0);
        g.drawImage(ship.getCurrentImage(), ship.getPositionX(), ship.getPositionY());
        g.draw(ship.getCollider());
        for (Rock rock : rocks) {
            g.drawImage(rock.getCurrentImage(), rock.getPositionX(), rock.getPositionY());
            g.draw(rock.getCollider());
        }
        for(Emp emp : emps){
            g.drawImage(emp.getCurrentImage(), emp.getPositionX(), emp.getPositionY());
            g.draw(emp.getCollider());
        }
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta){
        Input input = gc.getInput();
        ship.move(input);
        rocks.stream().forEach(Rock::move);
        emps.stream().forEach(Emp::move);

        // Shoot emps
        ship.shoot(input);


        // Collisions
        // TODO Kill the ship - add lives later.

//        for(Rock rock : rocks) {
//            if (ship.getCollider().intersects(rock.getCollider())) {
//                System.out.println("Ow!" + "\t" + ++i);
//            }
//        }

    }

}

