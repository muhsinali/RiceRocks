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
    private int positionX;
    private int positionY;
    private int velocity;
    private int velocityX;
    private int velocityY;
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
        angularVelocity = random.nextInt(8) + 2;
        angle = random.nextInt(360);
        currentImage.setRotation(angle);

        // Translational motion
        positionX = random.nextInt(Game.FRAME_WIDTH - width);
        positionY = random.nextInt(Game.FRAME_HEIGHT - height);
        velocity = random.nextInt(4) + 3;
        velocityX = (int) Math.round((velocity * Math.cos(Math.toRadians(angle))));
        velocityY = (int) Math.round((velocity * Math.sin(Math.toRadians(angle))));

        // Collision detection
        collider = new Circle(positionX + width / 2, positionY + height / 2, width / 2);
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

    public int getPositionX(){
        return positionX;
    }

    public int getPositionY(){
        return positionY;
    }
}
