import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame{
    public static final String NAME = "Rice Rocks!";

    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 600;

    public Game(String gameName){
        super(gameName);
        this.addState(new Play());
        this.addState(new GameOver());

    }

    @Override
    public void initStatesList(GameContainer gc) {
        this.enterState(GameState.PLAY.getID());
    }
}
