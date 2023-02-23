package Robot;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class RunAndPlayDemineur {

	public RunAndPlayDemineur() throws Exception {
	
		Robot robot = new Robot();
//		robot.mouseMove(30, 1080);
		robot.keyPress(KeyEvent.VK_WINDOWS);
		robot.keyRelease(KeyEvent.VK_WINDOWS);
		robot.mouseMove(99, 133);
		
		Thread.sleep(500);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        System.out.println("minesweeper Classic Challenge");
        Thread.sleep(500);
        
	}
	
}
