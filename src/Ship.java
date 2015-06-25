import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Ship {
    private int positionX;
    private int positionY;
    private int velocity = 5;

    private int initialAngle = 270;
    private int angularVelocity = 2;
    private int angle = initialAngle;  // in degrees & wrt vertical. Rotates clockwise as that's the convention of Image.setRotation()

    private Image[] images;
    private Image currentImage;


    public Ship(String imagePath){
        try {
            Image sprites = new Image(imagePath);
            SpriteSheet spriteSheet = new SpriteSheet(sprites, sprites.getWidth() / 2, sprites.getHeight());

            images = new Image[2];
            for(int i = 0; i < images.length; i++){
                images[i] = spriteSheet.getSprite(i, 0);
                images[i].setRotation(initialAngle);
            }
            currentImage = images[0];
            positionX = Game.FRAME_WIDTH / 2;
            positionY = Game.FRAME_HEIGHT / 2;
        } catch(SlickException e){
            e.printStackTrace();
        }
    }


    public void move(Input input){
        if (input.isKeyDown(Input.KEY_DOWN)){
            moveDown();
        }
        if(input.isKeyDown(Input.KEY_LEFT)) {
            rotate(-angularVelocity);
        }
        if (input.isKeyDown(Input.KEY_RIGHT)){
            rotate(angularVelocity);
        }
        if (input.isKeyDown(Input.KEY_UP)){
            moveUp();
            currentImage = images[1];
        } else {
            currentImage = images[0];
        }


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


    private void rotate(int deltaAngle){
        angle += deltaAngle;
        for(Image image : images){
            image.setRotation(angle);
        }
    }


    // GETTERS
    public Image getImage(){
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
        positionX -= 0.5 * velocity * Math.cos(Math.toRadians(angle));  // why does int -= double work but just - does not?
        positionY -= 0.5 * velocity * Math.sin(Math.toRadians(angle));
    }

    public void moveUp(){
        positionX += velocity * Math.cos(Math.toRadians(angle));
        positionY += velocity * Math.sin(Math.toRadians(angle));
    }
}
