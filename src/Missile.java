import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Missile {
    public static float CENTER_X;
    public static float CENTER_Y;
    private static Image currentImage;

    static {
        try{
            currentImage = new Image("res/images/shot2.png");
            CENTER_X = currentImage.getCenterOfRotationX();
            CENTER_Y = currentImage.getCenterOfRotationY();
        }catch(SlickException e){
            e.printStackTrace();
        }
    }

    // INSTANCE VARS
    private float positionX, positionY;
    private float VELOCITY = 3;      // Need to do a galilean transformation w/ the ship's velocity
    private float ANGLE;



    public Missile (float positionX, float positionY, float angle) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.ANGLE = angle;
    }

    public void move(){
        positionX += Math.round(VELOCITY * Math.cos(Math.toRadians(ANGLE)));
        positionY += Math.round(VELOCITY * Math.sin(Math.toRadians(ANGLE)));
        wrap();
    }

    private void wrap(){
//        if(positionX + currentImage.getCenterOfRotationX() < 0){    // What is the centerOfRotationX exactly?
//            positionX += Game.FRAME_WIDTH;
//        } else if (positionX + currentImage.getCenterOfRotationX() >= Game.FRAME_WIDTH){
//            positionX -= Game.FRAME_WIDTH;
//        }
//
//        if(positionY + currentImage.getCenterOfRotationY() < 0){
//            positionY += Game.FRAME_HEIGHT;
//        } else if(positionY + currentImage.getCenterOfRotationY() >= Game.FRAME_HEIGHT){
//            positionY -= Game.FRAME_HEIGHT;
//        }
    }

    // GETTERS
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
