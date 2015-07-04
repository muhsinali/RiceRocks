// No rock spawn zone. This class is used to prevent rocks from spawning too close to the ship so the ship doesn't get
// destroyed before the user has a chance to react. The no rock spawn zone is determined by determining the positions
// of its 4 corners (its rectangular in shape) and then seeing if the rock that's about to be spawned is in that region.

// Objects of this class is accessed through objects of the Ship class. Why? because no rock zones belong to the ship.
public class NoRockZone {
    // todo need to sort out the design issues w/ regards to objects created from this class.
    private float x, y, width, height;

    private Ship ship;

    public NoRockZone(Ship ship){
        this.ship = ship;
        initialiseRectangle();
    }

    private void initialiseRectangle(){
        width = 3 * Ship.WIDTH;
        height = 3 * Ship.HEIGHT;
    }

    // updates the positions of the 4 corners of the noRockZone. Wraps them as necessary.
    public void updateRectangle(){
        // todo edit to prevent the rock from being able to spawn w/i the zone when x,y are just at the top left of the zone
        // todo to do this, u need to include the Rock's width & height.
        x = ship.getPositionX() + Ship.WIDTH / 2 - width / 2;   // todo find out how the ship rotating affects ship.getPositionX() & Y
        y = ship.getPositionY() + Ship.HEIGHT / 2 - height / 2;

        if (x < 0){
            x += Game.FRAME_WIDTH;
        } else if (x > Game.FRAME_WIDTH){
            x -= Game.FRAME_WIDTH;
        }

        if (y < 0){
            y += Game.FRAME_HEIGHT;
        } else if (y > Game.FRAME_HEIGHT){
            y -= Game.FRAME_HEIGHT;
        }
    }

    // todo needs neatening up & checking to see if it's correct.
    // Checks to see if the rock's top left corner is in the noRockZone.
    public boolean inZone(float rockX, float rockY){
        if(x + width > Game.FRAME_WIDTH && y + height > Game.FRAME_HEIGHT){
            return x <= rockX && rockX <= Game.FRAME_WIDTH && y <= rockY && rockY <= Game.FRAME_HEIGHT
                    || 0 <= rockX && rockX < ((x + width) % Game.FRAME_WIDTH) && y <= rockY && rockY <= Game.FRAME_HEIGHT
                    || 0 <= rockX && rockX <= ((x + width) % Game.FRAME_WIDTH) && 0 <= rockY && rockY <= ((y + height) % Game.FRAME_HEIGHT)
                    || x <= rockX && rockX <= Game.FRAME_WIDTH && 0 <= rockY && rockY <= ((y + height) % Game.FRAME_HEIGHT);
        }

        if(x + width > Game.FRAME_WIDTH){
            return x <= rockX && rockX <= Game.FRAME_WIDTH && y <= rockY && rockY <= y + height
                    || 0 <= rockX && rockX <= ((x + width) % Game.FRAME_WIDTH) && y <= rockY && rockY <= y + height;
        }

        if(y + height > Game.FRAME_HEIGHT){
            return x <= rockX && rockX <= x + width && y <= rockY && rockY <= Game.FRAME_HEIGHT
                    || x <= rockX && rockX <= x + width && 0 <= rockY && rockY <= ((y + height) % Game.FRAME_HEIGHT);
        }

        return x <= rockX && rockX <= x + width && y <= rockY && rockY <= y + height;
    }

}
