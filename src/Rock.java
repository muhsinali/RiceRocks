import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.SlickException;

import java.util.Random;


public class Rock {
    public static int MAX_ROCKS = 2;

    private Image currentImage;

    // Physics
    private int height;
    private int width;
    private float positionX;
    private float positionY;
    private float velocity;
    private float velocityX;
    private float velocityY;
    private int angle;
    private int angularVelocity;
    private Shape collider;


    public Rock(){
        try{
            currentImage = new Image("res/images/asteroid_blue.png");
            width = currentImage.getWidth();
            height = currentImage.getHeight();
            initialisePhysics();
        }catch(SlickException e){
            e.printStackTrace();
        }
    }

    private void initialisePhysics(){
        // These are just arbitrary numbers atm
        Random random = new Random();

        // Rotational motion
        int MIN_ANG_VEL = 2;
        int MIN_VEL = 3;
        angularVelocity = MIN_ANG_VEL + random.nextInt(8);
        angle = random.nextInt(360);
        currentImage.setRotation(angle);

        // Translational motion
        positionX = random.nextInt(Game.FRAME_WIDTH - width);
        positionY = random.nextInt(Game.FRAME_HEIGHT - height);
        velocity = MIN_VEL + random.nextInt(4);
        velocityX *= (float) Math.cos(Math.toRadians(angle));
        velocityY *= (float) Math.sin(Math.toRadians(angle));

        // Collision detection
        collider = new Circle(positionX + width / 2, positionY + height / 2, width / 2);    // todo centerX & centerY
    }

    public void move(){
        currentImage.rotate(angularVelocity);
        positionX += velocityX;
        positionY += velocityY;
        wrap();
        collider.setCenterX(positionX);
        collider.setCenterY(positionY);
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
