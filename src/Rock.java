import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.SlickException;

import java.util.Random;


public class Rock {
    public static final int MAX_ROCKS = 2;
    public static final int HEIGHT, WIDTH;

    private static Image currentImage;

    static{
        try {
            currentImage = new Image("res/images/asteroid_blue.png");
        }catch (SlickException e){
            e.printStackTrace();
        } finally {
            if(currentImage != null){
                WIDTH = currentImage.getWidth();
                HEIGHT = currentImage.getHeight();
            } else {
                WIDTH = 0;
                HEIGHT = 0;
            }
        }
    }


    // Physics
    private float positionX;
    private float positionY;
    private float velocity;
    private float velocityX;    // todo find out how to make these blank instance final variables
    private float velocityY;
    private float angle;
    private float angularVelocity;
    private Shape collider;


    public Rock(){
        initialisePhysics();
    }

    private void initialisePhysics(){
        // These are just arbitrary numbers atm
        Random random = new Random();

        // Rotational motion
        final int MIN_ANG_VEL = 1;
        final int MIN_VEL = 3;
        angularVelocity = MIN_ANG_VEL + 4 * random.nextFloat();
        angle = 360 * random.nextFloat();
        currentImage.setRotation(angle);

        // Translational motion
        positionX = (Game.FRAME_WIDTH - WIDTH) * random.nextFloat();
        positionY = (Game.FRAME_HEIGHT - HEIGHT) * random.nextFloat();
        velocity = MIN_VEL + 4 * random.nextFloat();
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


    private void wrap(){
        if(positionX + currentImage.getCenterOfRotationX() < 0){
            positionX += Game.FRAME_WIDTH;
        } else if (positionX + currentImage.getCenterOfRotationX() >= Game.FRAME_WIDTH){
            positionX -= Game.FRAME_WIDTH;
        }

        if(positionY + currentImage.getCenterOfRotationY() < 0){
            positionY += Game.FRAME_HEIGHT;
        } else if(positionY + currentImage.getCenterOfRotationY() >= Game.FRAME_HEIGHT){
            positionY -= Game.FRAME_HEIGHT;
        }
    }


    // GETTERS
    public Shape getCollider(){
        return collider;
    }

    public Image getCurrentImage(){
        return currentImage;
    }

    public float getPositionX(){
        return positionX;
    }

    public float getPositionY(){
        return positionY;
    }
}
