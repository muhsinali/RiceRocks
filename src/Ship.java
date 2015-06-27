import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

// This should have a singleton design pattern?
public class Ship {
    private GameInfo gameInfo;

    private int height;
    private int width;

    // Physics
    private float positionX, positionY;
    private float velocityX, velocityY;
    private float ACCELERATION = 8;
    private int initialAngle = 270;
    private int ANGULAR_VELOCITY = 3;
    private int angle = initialAngle;  // in degrees & wrt vertical. Rotates clockwise as that's the convention of Image.setRotation()

    private Image[] images;
    private Image currentImage;

    private Shape collider;

    public Ship(GameInfo gameInfo){
        try {
            this.gameInfo = gameInfo;
            loadImages(new Image("res/images/double_ship.png"));
            initialisePhysics();
            collider = new Circle(positionX + width / 2, positionY + height / 2, width / 2);
        } catch(SlickException e){
            e.printStackTrace();
        }
    }

    private void drag(){
        currentImage = images[0];
        float DRAG = 0.99f;
        velocityX *= DRAG;
        velocityY *= DRAG;
    }

    private void loadImages(Image sprites){
        SpriteSheet spriteSheet = new SpriteSheet(sprites, sprites.getWidth() / 2, sprites.getHeight());
        images = new Image[2];
        for(int i = 0; i < images.length; i++){
            images[i] = spriteSheet.getSprite(i, 0);
            images[i].setRotation(initialAngle);
        }
        currentImage = images[0];
    }

    private void initialisePhysics(){
        // todo Figure out how to use streams to get all the widths from the images and then select the max width (& height).
        width = images[0].getWidth();
        height = images[0].getHeight();
        positionX = Math.round(Game.FRAME_WIDTH / 2);
        positionY = Math.round(Game.FRAME_HEIGHT / 2);
    }

    public void move(Input input){
        if(input.isKeyDown(Input.KEY_LEFT)) {
            rotate(-ANGULAR_VELOCITY);
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            rotate(ANGULAR_VELOCITY);
        }
        if (input.isKeyDown(Input.KEY_UP)) {
            accelerate();
        } else {
            drag();
        }
        updatePosition();
    }

    private void rotate(int deltaAngle){
        angle += deltaAngle;
        angle %= 360;
        for(Image image : images){
            image.setRotation(angle);
        }
    }

    public void shoot(Input input){
        if(input.isKeyPressed(Input.KEY_SPACE)){
            float centerX =  currentImage.getCenterOfRotationX();
            float centerY =  currentImage.getCenterOfRotationY();

            // shift by 5 pixels to the left as the missile image is 10px wide & high
            float shiftMissileX = Missile.CENTER_X;
            float shiftMissileY = Missile.CENTER_Y;
            float x = positionX + centerX - shiftMissileX + (float) ((centerX - shiftMissileX) * Math.cos(Math.toRadians(angle)));
            float y = positionY + centerY - shiftMissileY + (float) ((centerY - shiftMissileY) * Math.sin(Math.toRadians(angle)));
            gameInfo.addMissile(new Missile(x, y, angle));
        }
    }

    private void wrap(){
        if(positionX + currentImage.getCenterOfRotationX() < 0){
            positionX += Game.FRAME_WIDTH;
        } else if (positionX + currentImage.getCenterOfRotationX() >= Game.FRAME_WIDTH){
            positionX -= Game.FRAME_WIDTH;
        }

        if(positionY + currentImage.getCenterOfRotationY() < 0){
            positionY += Game.FRAME_HEIGHT;
        } else if (positionY + currentImage.getCenterOfRotationY() >= Game.FRAME_HEIGHT){
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


    // SETTERS
    // Figure out why these physics equations work properly
    // why does int -= double work but just - does not?
    private void accelerate(){
        currentImage = images[1];
        // add acceleration and use an if statement introduce a kind of drag function to it?
        velocityX = (float) (ACCELERATION * Math.cos(Math.toRadians(angle)));
        velocityY = (float) (ACCELERATION * Math.sin(Math.toRadians(angle)));
    }

    public void updatePosition(){
        positionX += velocityX;     // VELOCITY was 5
        positionY += velocityY;
        wrap();
        collider.setCenterX(positionX);
        collider.setCenterY(positionY);
    }
}
