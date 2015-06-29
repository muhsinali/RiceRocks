import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.SlickException;

import java.util.Random;


public class Rock extends GameObject {
    public static final int MAX_ROCKS = 2;
    public static final int HEIGHT, WIDTH;

    private static Image image = null;


    static{
        try {
            image = new Image("res/images/asteroid_blue.png");
        }catch (SlickException e){
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


    // Physics
    private float angularVelocity;



    public Rock(){
        currentImage = image;
        initialisePhysics();
    }

    private void initialisePhysics(){
        // These are just arbitrary numbers atm
        Random random = new Random();

        // Rotational motion
        final float MIN_ANG_VEL = 1;
        final float MIN_VEL = 2;
        angularVelocity = MIN_ANG_VEL + 4 * random.nextFloat();
        final float angle = 360 * random.nextFloat();
        currentImage.setRotation(angle);

        // Translational motion
        positionX = (Game.FRAME_WIDTH - WIDTH) * random.nextFloat();
        positionY = (Game.FRAME_HEIGHT - HEIGHT) * random.nextFloat();

        final float velocity = MIN_VEL + 5 * random.nextFloat();
        velocityX = (float) (velocity * Math.cos(Math.toRadians(angle)));
        velocityY = (float) (velocity * Math.sin(Math.toRadians(angle)));

        // Collision detection
        collider = new Circle(positionX + WIDTH / 2, positionY + HEIGHT / 2, WIDTH / 2);
    }

    public void move(){
        currentImage.rotate(angularVelocity);
        positionX += velocityX;
        positionY += velocityY;
        wrap();
        collider.setLocation(positionX, positionY);
    }
}
