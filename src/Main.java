import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;


public class Main {

    public static void main(String[] args) {
        // a game container that displays the game as a stand alone application
        AppGameContainer appGC;
        try{
            appGC = new AppGameContainer(new Game(Game.NAME), Game.FRAME_WIDTH, Game.FRAME_HEIGHT, false);
            appGC.setVSync(true);   // sets FPS to screen's refresh rate
            appGC.start();
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
}
