import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.SlickException;

public class Missile {
    private static Image currentImage;
    public static final int HEIGHT, WIDTH;

    private Shape collider;

    static {
        try{
            currentImage = new Image("res/images/shot2.png");
        }catch(SlickException e){
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

    // INSTANCE VARS
    private float positionX, positionY;
    private float velocityX, velocityY;


    public Missile (float positionX, float positionY, float velocityX, float velocityY, float angle) {
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

    private void wrap(){
        if(positionX + currentImage.getCenterOfRotationX() < 0){    // What is the centerOfRotationX exactly?
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
