import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;

import java.util.ArrayList;
import java.util.List;

public class Ship extends GameObject{
    private GameInfo gameInfo;

    public static final int HEIGHT, WIDTH;
    private static List<Image> images;


    // Physics
    private static final float ACCELERATION = 0.08f;
    private static final float ANGULAR_VELOCITY = 4;
    public static final float INITIAL_ANGLE = 270;
    public static final float INITIAL_POS_X;
    public static final float INITIAL_POS_Y;

    private float angle = INITIAL_ANGLE;  // in degrees & wrt vertical. Rotates clockwise.

    // Alpha for colour. Used to adjust opacity of ship.
    public static final float HIGH_ALPHA = 1;
    public static final float LOW_ALPHA = 0.6f;
    private float alpha = 1;

    public static final int RESPAWN_PERIOD = (3 * 2 * ShipSpawner.TIME_STEP);
    private int lifetime;

    static{
        Image sprites;
        try {
            sprites = new Image("res/images/double_ship.png");
            images = loadImages(sprites, 2, 1);
        }catch(SlickException e){
            e.printStackTrace();
        } finally{
            WIDTH = images.stream().mapToInt(Image::getWidth).max().orElse(0);
            HEIGHT = images.stream().mapToInt(Image::getWidth).max().orElse(0);
            INITIAL_POS_X = (Game.FRAME_WIDTH - WIDTH) / 2f;
            INITIAL_POS_Y = (Game.FRAME_HEIGHT - 0.5f * HEIGHT) / 2f;
        }
    }

    public Ship(GameInfo gameInfo){
        this.gameInfo = gameInfo;
        currentImage = images.get(0);
        initialisePhysics();
        collider = new Circle(positionX + WIDTH / 2, positionY + HEIGHT / 2, WIDTH / 2);
    }



    private void accelerate(){
        currentImage = images.get(1);
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


    private static List<Image> loadImages(Image sprites, int numImagesX, int numImagesY){
        SpriteSheet spriteSheet = new SpriteSheet(sprites, sprites.getWidth() / numImagesX, sprites.getHeight() / numImagesY);
        List<Image> images = new ArrayList<>(numImagesX * numImagesY);
        for(int i = 0; i < numImagesX; i++){
            for (int j = 0; j < numImagesY; j++) {
                images.add(spriteSheet.getSprite(i, j));
                images.get(i).setRotation(INITIAL_ANGLE);
            }
        }
        return images;
    }

    private void drag(){
        float DRAG = 0.99f;
        velocityX *= DRAG;
        velocityY *= DRAG;
    }

    private void initialisePhysics(){
        positionX = INITIAL_POS_X;
        positionY = INITIAL_POS_Y;
    }

    public boolean isRespawning(){
        return lifetime <= RESPAWN_PERIOD;
    }

    public void move(Input input){
        if(input.isKeyDown(Input.KEY_LEFT)) {
            setOrientation(angle - ANGULAR_VELOCITY);
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            setOrientation(angle + ANGULAR_VELOCITY);
        }
        if (input.isKeyDown(Input.KEY_UP)) {
            accelerate();
        } else {
            currentImage = images.get(0);
            drag();
        }
        setPosition(positionX + velocityX, positionY + velocityY);
    }


    public void shoot(Input input){
        if(input.isKeyPressed(Input.KEY_SPACE)){
            float centerX =  currentImage.getCenterOfRotationX();
            float centerY =  currentImage.getCenterOfRotationY();

            // shift a little bit to make the emp's image be centered correctly
            float shiftEmpX = Emp.WIDTH / 2;
            float shiftEmpY = Emp.HEIGHT / 2;
            float x = positionX + centerX - shiftEmpX + (float) ((centerX - shiftEmpX) * Math.cos(Math.toRadians(angle)));
            float y = positionY + centerY - shiftEmpY + (float) ((centerY - shiftEmpY) * Math.sin(Math.toRadians(angle)));
            gameInfo.getEmps().add(new Emp(x, y, velocityX, velocityY, angle));
        }
    }


    // GETTERS
    public Color getColor(){
        return new Color(255, 255, 255, alpha);
    }

    public int getLifetime(){
        return lifetime;
    }


    // SETTERS
    public void setAlpha(float value){
        alpha = value;
    }

    public void setLifetime(int value){
        lifetime = value;
    }

    public void setPosition(float x, float y){
        positionX = x;
        positionY = y;
        wrap();
        collider.setLocation(positionX, positionY);
    }

    public void setOrientation(float value){
        angle = value;

        if(angle >= 360){
            angle -= 360;
        } else if (angle < 0){
            angle += 360;
        }

        for(Image image : images){
            image.setRotation(angle);
        }
    }

    public void setVelocity(float vX, float vY){
        velocityX = vX;
        velocityY = vY;
    }
}
