import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.SlickException;

public class Emp extends GameObject{
    // STATIC VARS
    public static float MAX_LIFETIME = 600;
    private static Image image;
    public static final int HEIGHT, WIDTH;

    static{
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

    // INSTANCE VARS
    private int lifetime;


    public Emp(float positionX, float positionY, float velocityX, float velocityY, float angle) {
        currentImage = image;
        initialisePhysics(positionX, positionY, velocityX, velocityY, angle);
    }

    public void incrementLifetime(int timeStep){
        lifetime += timeStep;
    }

    public void initialisePhysics(float positionX, float positionY, float velocityX, float velocityY, float angle){
        // Uses Galilean transformations
        float VELOCITY = 8;
        this.positionX = positionX;
        this.positionY = positionY;
        this.velocityX = velocityX + Math.round(VELOCITY * Math.cos(Math.toRadians(angle)));
        this.velocityY = velocityY + Math.round(VELOCITY * Math.sin(Math.toRadians(angle)));
        // Collider is used for collision detection
        collider = new Circle(positionX + WIDTH / 2, positionY + HEIGHT / 2, currentImage.getWidth() / 2.0f);
    }

    public void move(){
        positionX += velocityX;
        positionY += velocityY;
        wrap();
        collider.setLocation(positionX, positionY);
    }

    // GETTERS
    public int getLifetime(){
        return lifetime;
    }
}
