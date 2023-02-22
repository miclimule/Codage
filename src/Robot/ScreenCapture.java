package Robot;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ScreenCapture {
    public ScreenCapture() {
        try {
            Robot robot = new Robot();
            Rectangle rectangle = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
            BufferedImage image = robot.createScreenCapture(rectangle);
            File file = new File("screenshot.png");
            ImageIO.write(image, "png", file);
            System.out.println("Capture d'écran enregistrée avec succès !");
        } catch (AWTException | IOException ex) {
            System.err.println(ex);
        }
    }
}

