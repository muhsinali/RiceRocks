import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Circle;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Rock extends GameObject {
    public static final int MAX_ROCKS = 5;
    public static final int POINTS = 100;
    private Animation explosion;  // todo sh be final

    public final int HEIGHT, WIDTH;
    private boolean explode;

    // Physics
    private float angle;
    private float angularVelocity;


    public Rock(){
        List<Image> explosionSprites;
        try {
            currentImage = new Image("res/images/asteroid_blue.png");

            explosionSprites = loadImages(new Image("res/images/explosion_alpha.png"), 24, 1);
            explosion = new Animation(explosionSprites.toArray(new Image[explosionSprites.size()]), 40);
            explosion.setLooping(false);
        }catch (SlickException e){
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
        initialisePhysics();
    }

    public void beginExplosion(){
        velocityX = 0;
        velocityY = 0;
        explode = true;
        playExplosionSound("res/sounds/explosion.ogg");
    }

    public boolean hasExplosionBegun(){
        return explode;
    }

    private void initialisePhysics(){
        Random random = new Random();
        initialiseRotation(random);
        initialiseTranslation(random);
        // Shape used for collision detection
        collider = new Circle(positionX + WIDTH / 2, positionY + HEIGHT / 2, WIDTH / 2);
    }

    private void initialiseRotation(Random random){
        final float MIN_ANG_VEL = 2;
        angle = 360 * random.nextFloat();
        angularVelocity = MIN_ANG_VEL + 6 * random.nextFloat();
        currentImage.setRotation(angle);
    }

    private void initialiseTranslation(Random random){
        final float MIN_VEL = 2;
        final float velocity = MIN_VEL + 4 * random.nextFloat();
        positionX = (Game.FRAME_WIDTH - WIDTH) * random.nextFloat();
        positionY = (Game.FRAME_HEIGHT - HEIGHT) * random.nextFloat();
        velocityX = (float) (velocity * Math.cos(Math.toRadians(angle)));
        velocityY = (float) (velocity * Math.sin(Math.toRadians(angle)));
    }

    private static List<Image> loadImages(Image sprites, int numImagesX, int numImagesY){
        SpriteSheet spriteSheet = new SpriteSheet(sprites, sprites.getWidth() / numImagesX, sprites.getHeight() / numImagesY);
        List<Image> images = new ArrayList<>(numImagesX * numImagesY);
        for(int i = 0; i < numImagesX; i++){
            for (int j = 0; j < numImagesY; j++) {
                images.add(spriteSheet.getSprite(i, j));
            }
        }
        return images;
    }

    public void move(){
        currentImage.rotate(angularVelocity);
        positionX += velocityX;
        positionY += velocityY;
        wrap();
        collider.setLocation(positionX, positionY);
    }

    private void playExplosionSound(String sound){
        try {
            Sound explosionSound = new Sound(sound);
            explosionSound.play(1, 0.6f);
        }catch (SlickException e){
            e.printStackTrace();
        }
    }

    // GETTERS
    public Animation getExplosion(){
        return explosion;
    }
}
