import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame{
    public static final String NAME = "Rice Rocks!";
    public static final int PLAY = 0;
    public static final int GAME_WON = 1;
    public static final int GAME_OVER = 2;

    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 600;

    public Game(String gameName){
        super(gameName);
        this.addState(new Play());

    }

    @Override
    public void initStatesList(GameContainer gc) {
        try {
            this.getState(PLAY).init(gc, this);
            this.enterState(PLAY);
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
}
