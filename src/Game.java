import javafx.scene.media.AudioClip;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.state.StateBasedGame;

import java.nio.file.Paths;

public class Game extends StateBasedGame{
    public static final String NAME = "Rice Rocks!";

    public static final int FRAME_WIDTH = 800;
    public static final int FRAME_HEIGHT = 600;

    public Game(String gameName){
        super(gameName);
        loadStates();
        playSoundTrack("res/sounds/soundtrack.mp3");
    }

    @Override
    public void initStatesList(GameContainer gc) {
        this.enterState(GameState.PLAY.getID());
    }

    private void loadStates(){
        this.addState(new Play());
        this.addState(new GameOver());
    }

    private void playSoundTrack(String sound){
        AudioClip soundTrack = new AudioClip(Paths.get(sound).toUri().toString());
        soundTrack.play(0.4);
        soundTrack.setCycleCount(AudioClip.INDEFINITE);
    }
}
