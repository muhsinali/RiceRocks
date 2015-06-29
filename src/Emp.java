import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.SlickException;

public class Emp extends GameObject{
    private static Image image = null;
    public static final int HEIGHT, WIDTH;

    static {
        try{
            image = new Image("res/images/shot2.png");
        }catch(SlickException e){
            e.printStackTrace();
        } finally {
            if(image != null){
                WIDTH = image.getWidth();
                HEIGHT = image.getHeight();
            } else {
                WIDTH = 0;
                HEIGHT = 0;
            }
        }
    }

    public Emp(float positionX, float positionY, float velocityX, float velocityY, float angle) {
        currentImage = image;
        // Galilean transformations
        float VELOCITY = 3;
        this.positionX = positionX;
        this.positionY = positionY;
        this.velocityX = velocityX + Math.round(VELOCITY * Math.cos(Math.toRadians(angle)));
        this.velocityY = velocityY + Math.round(VELOCITY * Math.sin(Math.toRadians(angle)));
        collider = new Circle(positionX + WIDTH / 2, positionY + HEIGHT / 2, currentImage.getWidth() / 2.0f);
    }

    public void move(){
        positionX += velocityX;
        positionY += velocityY;
        wrap();
        collider.setLocation(positionX, positionY);
    }
}
