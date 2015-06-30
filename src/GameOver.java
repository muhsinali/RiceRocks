import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOver extends BasicGameState {

    @Override
    public int getID(){
        return GameState.GAME_LOST.getID();
    }

    @Override
    public void init(GameContainer gc, StateBasedGame sbg){

    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
        g.drawString("Game over.", 100, 100);
    }

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta){

    }
}
