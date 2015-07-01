import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;


public class Play extends BasicGameState{
    private GameInfo gameInfo;
    private List<Emp> emps;
    private List<Rock> rocks;
    private Ship ship;

    private Image backgroundImage;
    private Timer timer = new Timer();


    @Override
    public int getID(){
        return GameState.PLAY.getID();
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg){
        try{
            backgroundImage = new Image("res/images/nebula_blue.png");
            gameInfo = new GameInfo();
            ship = gameInfo.getShip();
            rocks = gameInfo.getRocks();
            emps = gameInfo.getEmps();
            rocks.add(new Rock());  // todo removing this produces a bug. find out why.

            Date date = new Date();
            timer.schedule(gameInfo.getEmpManager(), date, EmpManager.TIME_STEP);
            timer.schedule(gameInfo.getRockSpawner(), date, RockSpawner.TIME_STEP);
            timer.schedule(gameInfo.getShipSpawner(), date, ShipSpawner.TIME_STEP);
        } catch(SlickException e){
            e.printStackTrace();
        }
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
        g.drawImage(backgroundImage, 0, 0);
        g.drawImage(ship.getCurrentImage(), ship.getPositionX(), ship.getPositionY(), ship.getColor());

        for (Rock rock : rocks) {
            if(rock.hasExplosionBegun()){
                g.drawAnimation(rock.getExplosion(), rock.getPositionX(), rock.getPositionY());
            }else {
                g.drawImage(rock.getCurrentImage(), rock.getPositionX(), rock.getPositionY());
            }
        }
        for (Emp emp : emps) {
            g.drawImage(emp.getCurrentImage(), emp.getPositionX(), emp.getPositionY());
        }

        final int HUD_RIGHT_X = 680;
        final int HUD_RIGHT_Y = 15;
        final int LINE_SPACING = 20;
        g.drawString("Lives: " + gameInfo.getLivesRemaining(), HUD_RIGHT_X, HUD_RIGHT_Y);
        g.drawString("Score: " + gameInfo.getScore(), HUD_RIGHT_X, HUD_RIGHT_Y + LINE_SPACING);
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
        Iterator<Rock> rockIterator = rocks.iterator();
        while(rockIterator.hasNext()) {
            Rock rock = rockIterator.next();
            if(rock.getExplosion().isStopped()){
                rockIterator.remove();
            }

            if(ship.getCollider().intersects(rock.getCollider()) && !ship.isRespawning()){
//                rockIterator.remove();
//                gameInfo.deductLife();
//                if(gameInfo.getLivesRemaining() == 0){
//                    sbg.enterState(GameState.GAME_LOST.getID());
//                    // todo find out how to leave this state
//                }
//                ship.setPosition(Ship.INITIAL_POS_X, Ship.INITIAL_POS_Y);
//                ship.setVelocity(0, 0);
//                ship.setOrientation(Ship.INITIAL_ANGLE);
//                ship.setLifetime(0);
                continue;
            }

            Iterator<Emp> empIterator = emps.iterator();
            while (empIterator.hasNext()) {
                Emp emp = empIterator.next();
                if (emp.getCollider().intersects(rock.getCollider()) && !rock.hasExplosionBegun()) {
                    rock.beginExplosion();
                    empIterator.remove();
                    gameInfo.setScore(gameInfo.getScore() + Rock.POINTS);
                    break;
                }
            }
        }

    }
}

