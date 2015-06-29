import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.geom.Shape;

// todo This should have a singleton design pattern?
public class Ship {
    private GameInfo gameInfo;

    public static final int HEIGHT, WIDTH;

    // Physics
    private static final float ACCELERATION = 0.08f;
    private static final float ANGULAR_VELOCITY = 3;
    private static final float INITIAL_ANGLE = 270;

    private float positionX, positionY;
    private float velocityX, velocityY;
    private float angle = INITIAL_ANGLE;  // in degrees & wrt vertical. Rotates clockwise as that's the convention of Image.setRotation()


    private static Image[] images;
    private Image currentImage;

    private Shape collider;


    static{
        Image sprites = null;
        try {
            sprites = new Image("res/images/double_ship.png");
            loadImages(sprites);
            // todo Figure out how to use streams to get all the widths from the images and then select the max WIDTH (& HEIGHT).
        }catch(SlickException e){
            e.printStackTrace();
        } finally{
            if(sprites != null){
                WIDTH = images[0].getWidth();
                HEIGHT = images[0].getHeight();
            } else {
                WIDTH = 0;
                HEIGHT = 0;
            }
        }
    }

    public Ship(GameInfo gameInfo){
        this.gameInfo = gameInfo;
        currentImage = images[0];
        initialisePhysics();
        collider = new Circle(positionX + WIDTH / 2, positionY + HEIGHT / 2, WIDTH / 2);
    }

    private static void loadImages(Image sprites){
        SpriteSheet spriteSheet = new SpriteSheet(sprites, sprites.getWidth() / 2, sprites.getHeight());
        images = new Image[2];
        for(int i = 0; i < images.length; i++){
            images[i] = spriteSheet.getSprite(i, 0);
            images[i].setRotation(INITIAL_ANGLE);
        }
    }

    private void drag(){
        float DRAG = 0.99f;
        velocityX *= DRAG;
        velocityY *= DRAG;
    }



    private void initialisePhysics(){
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
            currentImage = images[0];
            drag();
        }
        updatePosition();
    }

    private void rotate(float deltaAngle){
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
            float shiftMissileX = Missile.WIDTH / 2;
            float shiftMissileY = Missile.HEIGHT / 2;
            float x = positionX + centerX - shiftMissileX + (float) ((centerX - shiftMissileX) * Math.cos(Math.toRadians(angle)));
            float y = positionY + centerY - shiftMissileY + (float) ((centerY - shiftMissileY) * Math.sin(Math.toRadians(angle)));
            gameInfo.getMissiles().add(new Missile(x, y, velocityX, velocityY, angle));
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
    private void accelerate(){
        currentImage = images[1];
        // why does int -= double not require casting but just - does?
        if(Math.pow(velocityX, 2) + Math.pow(velocityY, 2) < Math.pow(8, 2)) {
            velocityX += (float) (ACCELERATION * Math.cos(Math.toRadians(angle)));
            velocityY += (float) (ACCELERATION * Math.sin(Math.toRadians(angle)));
        } else {
            // For when ship has reached max velocity. This block allows the ship to change direction whilst keeping its
            // speed constant. How? When the ship changes direction at max vel, the force & vel vectors are no longer
            // positioned at the same angle. Therefore, the acceleration relative to the vel needs to be added, causing
            // the ship to change direction.
            float velocityAngle = (float) Math.toDegrees(Math.atan2(velocityY, velocityX));
            velocityX += (float) (ACCELERATION * (Math.cos(Math.toRadians(angle)) - Math.cos(Math.toRadians(velocityAngle))));
            velocityY += (float) (ACCELERATION * (Math.sin(Math.toRadians(angle)) - Math.sin(Math.toRadians(velocityAngle))));
        }
    }

    public void updatePosition(){
        positionX += velocityX;
        positionY += velocityY;
        wrap();
        collider.setLocation(positionX, positionY);
    }
}
