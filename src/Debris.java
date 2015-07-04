import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

// todo should be a singleton.
// Used to draw the moving debris in the back of the screen.
public class Debris {
    private Image image;
    private float width;
    private float positionX;

    public Debris(String imagePath, int scaledWidth, int scaledHeight){
        try{
            this.image = new Image(imagePath).getScaledCopy(scaledWidth, scaledHeight);
            width = scaledWidth;
        }catch(SlickException e){
            e.printStackTrace();
        }
    }

    public void move(){
        final float VELOCITY_X = 0.25f;
        positionX %= width;
        positionX += VELOCITY_X;
    }

    // GETTERS
    public Image getImage(){
        return image;
    }

    public float getPositionX(){
        return positionX;
    }

    public float getWidth(){
        return width;
    }
}
