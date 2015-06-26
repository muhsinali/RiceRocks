import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rock {
    // randomly add rocks at certain intervals. Check if there's < maxRocks? If so, add more. Make sure that when you
    // destroy rocks that you remove that destroyed rock from the array.
    private int height;
    private int width;
    private int positionX;
    private int positionY;
    private int velocity;
    private int velocityX;
    private int velocityY;

    private int angle;
    private int angularVelocity;

    //public static List<Rock> rockArray = new ArrayList<>();
    private Image currentImage;

    private Shape collider;

    public Rock(String imagePath){
        try{
            currentImage = new Image(imagePath);
            width = currentImage.getWidth();
            height = currentImage.getHeight();

            Random random = new Random();
            positionX = random.nextInt(Game.FRAME_WIDTH - width);
            positionY = random.nextInt(Game.FRAME_HEIGHT - height);
            velocity = random.nextInt(5) + 5;       // these are just arbitrary numbers atm
            angularVelocity = random.nextInt(8) + 2;
            angle = random.nextInt(360);
            velocityX = (int) Math.round((velocity * Math.cos(Math.toRadians(angle))));
            velocityY = (int) Math.round((velocity * Math.sin(Math.toRadians(angle))));

            collider = new Circle(positionX + width / 2, positionY + height / 2, width / 2);
            currentImage.setRotation(angle);
        }catch(SlickException e){
            e.printStackTrace();
        }
    }

    public void move(){
        angle += angularVelocity;
        angle %= 360;
        currentImage.setRotation(angle);
        positionX += velocityX;
        positionY += velocityY;
        wrap();
        collider.setCenterX(positionX);
        collider.setCenterY(positionY);
    }


    public void wrap(){
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
