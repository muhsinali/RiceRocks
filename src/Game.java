import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.state.StateBasedGame;

// This class sets up the game and deals with the different GameStates.
public class Game extends StateBasedGame{
    public static final String NAME = "Rice Rocks!";

    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 600;

    public Game(String gameName){
        super(gameName);
        loadStates();
        playSoundTrack("res/sounds/soundtrack.ogg");
    }

    @Override
    public void initStatesList(GameContainer gc) {
        try {
            this.getState(GameState.PLAY.getID()).init(gc, this);
            this.enterState(GameState.PLAY.getID());
        }catch (SlickException e){
            e.printStackTrace();
        }
    }

    private void loadStates(){
        this.addState(new Play());
        this.addState(new GameOver());
    }

    private void playSoundTrack(String sound){
        try {
            Sound soundTrack = new Sound(sound);
            soundTrack.loop();
            soundTrack.play(1, 0.5f);
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
}
