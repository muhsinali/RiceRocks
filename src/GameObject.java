import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Shape;

// Ship, Emp, and Rock inherit from this class.
public class GameObject {
    protected float positionX, positionY;
    protected float velocityX, velocityY;
    protected Shape collider;
    protected Image currentImage;


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

    public float getPositionX(){
        return positionX;
    }

    public float getPositionY(){
        return positionY;
    }
}
