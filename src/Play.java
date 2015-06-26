import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;


public class Play extends BasicGameState{
    private Image backgroundImage;
    private Ship ship;
    private Rock rock;


    @Override
    public int getID(){
        return Game.PLAY;
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg){
        try{
            backgroundImage = new Image("res/images/nebula_blue.png");
            ship = new Ship("res/images/double_ship.png");
            rock = new Rock("res/images/asteroid_blue.png");
        } catch(SlickException e){
            e.printStackTrace();
        }
    }



    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
        g.drawImage(backgroundImage, 0, 0);
        g.drawImage(ship.getCurrentImage(), ship.getPositionX(), ship.getPositionY());
        g.drawImage(rock.getCurrentImage(), rock.getPositionX(), rock.getPositionY());
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta){
        Input input = gc.getInput();
        ship.move(input);
        rock.move();

        // Collisions
        if(ship.getCollider().intersects(rock.getCollider())){
            System.out.println("Ow!");
        }

    }
}

