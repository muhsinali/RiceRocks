public class NoRockZone {
    // todo need to sort out the design issues w/ regards to objects created from this class
    // No rock spawn zone. These variables are used to prevent rocks from spawning too close to the ship.
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
