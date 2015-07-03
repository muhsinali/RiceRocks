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
    private Debris debris;
    private Timer timer = new Timer();

    private void drawBackground(Graphics g){
        g.drawImage(backgroundImage, 0, 0);
    }

    private void drawDebris(Graphics g){
        g.drawImage(debris.getImage(),
                0, 0,
                debris.getPositionX(), Game.FRAME_HEIGHT,
                debris.getWidth() - debris.getPositionX(), 0,
                debris.getWidth(), Game.FRAME_HEIGHT);

        g.drawImage(debris.getImage(),
                debris.getPositionX(), 0,
                Game.FRAME_WIDTH, Game.FRAME_HEIGHT,
                0, 0,
                Game.FRAME_WIDTH - debris.getPositionX(), Game.FRAME_HEIGHT);
    }

    private void drawEmps(Graphics g){
        for (Emp emp : emps) {
            g.drawImage(emp.getCurrentImage(), emp.getPositionX(), emp.getPositionY());
        }
    }

    private void drawHUD(Graphics g){
        final int HUD_RIGHT_X = Game.FRAME_WIDTH - 120;
        final int HUD_RIGHT_Y = 15;
        final int LINE_SPACING = 20;
        g.drawString("Lives: " + gameInfo.getLivesRemaining(), HUD_RIGHT_X, HUD_RIGHT_Y);
        g.drawString("Score: " + gameInfo.getScore(), HUD_RIGHT_X, HUD_RIGHT_Y + LINE_SPACING);
    }

    private void drawRocks(Graphics g){
        for (Rock rock : rocks) {
            if(rock.hasExplosionBegun()){
                g.drawAnimation(rock.getExplosion(), rock.getPositionX(), rock.getPositionY());
            }else {
                g.drawImage(rock.getCurrentImage(), rock.getPositionX(), rock.getPositionY());
            }
        }
    }

    private void drawShip(Graphics g){
        g.drawImage(ship.getCurrentImage(), ship.getPositionX(), ship.getPositionY(), ship.getColor());
    }

    @Override
    public int getID(){
        return GameState.PLAY.getID();
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg){
        try{
            backgroundImage = new Image("res/images/nebula_blue.png");
            debris = new Debris("res/images/debris2_blue.png", Game.FRAME_WIDTH, Game.FRAME_HEIGHT);
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
        drawBackground(g);
        drawDebris(g);
        drawShip(g);
        drawRocks(g);
        drawEmps(g);
        drawHUD(g);
    }


    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta){
        Input input = gc.getInput();
        debris.move();
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

