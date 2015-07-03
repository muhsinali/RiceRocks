import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.media.AudioClip;


public class Ship extends GameObject{
    private GameInfo gameInfo;

    // Resources
    public static final int HEIGHT, WIDTH;
    private static List<Image> images;

    private Sound thrustSound;
    private AudioClip shootEmp;


    // Physics
    private static final float ACCELERATION = 0.08f;
    private static final float ANGULAR_VELOCITY = 4;
    public static final float INITIAL_ANGLE = 270;
    public static final float INITIAL_POS_X;
    public static final float INITIAL_POS_Y;

    private float angle = INITIAL_ANGLE;  // in degrees & wrt vertical. Rotates clockwise.


    // Other characteristics
    // Alpha variable is for Color. Used to adjust opacity of ship.
    public static final float HIGH_ALPHA = 1;
    public static final float LOW_ALPHA = 0.6f;
    private float alpha = 1;

    public static final int RESPAWN_PERIOD = (3 * 2 * ShipSpawner.TIME_STEP);
    private int lifetime;


    static{
        try {
            images = loadImages(new Image("res/images/double_ship.png"), 2, 1);
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
        loadSounds();
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

    private void drag(){
        float DRAG = 0.99f;
        velocityX *= DRAG;
        velocityY *= DRAG;
    }

    private void initialisePhysics(){
        positionX = INITIAL_POS_X;
        positionY = INITIAL_POS_Y;
        // Collider is used for collision detection
        collider = new Circle(positionX + WIDTH / 2, positionY + HEIGHT / 2, WIDTH / 2);
    }

    public boolean isRespawning(){
        return lifetime <= RESPAWN_PERIOD;
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

    private void loadSounds(){
        try {
            thrustSound = new Sound("res/sounds/thrust.ogg");
            shootEmp = new AudioClip(Paths.get("res/sounds/emp.mp3").toUri().toString());
        }catch (SlickException e){
            e.printStackTrace();
        }
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
            playThrustSound();
        } else {
            currentImage = images.get(0);
            drag();
            stopThrustSound();
        }
        setPosition(positionX + velocityX, positionY + velocityY);
    }

    private void playThrustSound(){
        if(!thrustSound.playing()) {
            thrustSound.play(1, 0.3f);
        }
    }

    public void shoot(Input input){
        if(input.isKeyPressed(Input.KEY_SPACE)){
            // Ensures that the emp is centered correctly so that it looks like it comes out of the ship's canon
            float alignEmpX = currentImage.getCenterOfRotationX() - Emp.WIDTH / 2;
            float alignEmpY = currentImage.getCenterOfRotationY() - Emp.HEIGHT / 2;
            float empX = positionX + alignEmpX + (float) (alignEmpX * Math.cos(Math.toRadians(angle)));
            float empY = positionY + alignEmpY + (float) (alignEmpY * Math.sin(Math.toRadians(angle)));
            gameInfo.getEmps().add(new Emp(empX, empY, velocityX, velocityY, angle));
            shootEmp.setVolume(0.7);
            shootEmp.play();
        }
    }

    private void stopThrustSound(){
        if(thrustSound.playing()){
            thrustSound.stop();
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
        angle %= 360;
        for(Image image : images){
            image.setRotation(angle);
        }
    }
}
