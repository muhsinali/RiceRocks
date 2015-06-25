import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import java.util.ArrayList;
import java.util.List;

public class Rock {
    // randomly add rocks at certain intervals. Check if there's < maxRocks? If so, add more. Make sure that when you
    // destroy rocks that you remove that destroyed rock from the array.

    public static List<Rock> rockArray = new ArrayList<>();
    private Image image;

    public Rock(String imagePath){
        try{
            image = new Image(imagePath);
        }catch(SlickException e){
            e.printStackTrace();
        }
    }
}
