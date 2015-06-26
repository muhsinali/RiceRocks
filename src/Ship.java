import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;


public class Ship {
    private int height;
    private int width;

    private int positionX;
    private int positionY;
    private int velocity = 5;

    private int initialAngle = 270;
    private int angularVelocity = 2;
    private int angle = initialAngle;  // in degrees & wrt vertical. Rotates clockwise as that's the convention of Image.setRotation()

    private Image[] images;
    private Image currentImage;

    private Shape collider;

    public Ship(String imagePath){
        try {
            loadImages(new Image(imagePath));
            collider = new Circle(positionX + width / 2, positionY + height / 2, width / 2);
        } catch(SlickException e){
            e.printStackTrace();
        }
    }

    private void loadImages(Image sprites){
        SpriteSheet spriteSheet = new SpriteSheet(sprites, sprites.getWidth() / 2, sprites.getHeight());
        images = new Image[2];
        for(int i = 0; i < images.length; i++){
            images[i] = spriteSheet.getSprite(i, 0);
            images[i].setRotation(initialAngle);
        }

        // Figure out how to use streams to get all the widths from the images and then select the max width (& height).
        width = images[0].getWidth();
        height = images[0].getHeight();
        currentImage = images[0];
        positionX = Math.round(Game.FRAME_WIDTH / 2);
        positionY = Math.round(Game.FRAME_HEIGHT / 2);
    }

    public void move(Input input){
        if (input.isKeyDown(Input.KEY_DOWN)) {
            moveDown();
        }
        if(input.isKeyDown(Input.KEY_LEFT)) {
            rotate(-angularVelocity);
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            rotate(angularVelocity);
        }
        if (input.isKeyDown(Input.KEY_UP)) {
            moveUp();
            currentImage = images[1];
        } else {
            currentImage = images[0];
        }

        wrap();
        updateColliderPosition();
    }

    private void rotate(int deltaAngle){
        angle += deltaAngle;
        angle %= 360;
        for(Image image : images){
            image.setRotation(angle);
        }
    }

    public void wrap(){
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

    public int getPositionX(){
        return positionX;
    }

    public int getPositionY(){
        return positionY;
    }


    // SETTERS
    public void moveDown(){     // Figure out why these physics equations work properly
        // why does int -= double work but just - does not?
        positionX -= Math.round(0.5f * velocity * Math.cos(Math.toRadians(angle)));
        positionY -= Math.round(0.5f * velocity * Math.sin(Math.toRadians(angle)));
    }

    public void moveUp(){
        positionX += Math.round(velocity * Math.cos(Math.toRadians(angle)));
        positionY += Math.round(velocity * Math.sin(Math.toRadians(angle)));
    }

    public void updateColliderPosition(){
        collider.setCenterX(positionX);
        collider.setCenterY(positionY);
    }
}
