package Robot;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

public class RunMinecraft {

	public RunMinecraft() throws InterruptedException, AWTException {
		Robot robot = new Robot();
//		robot.mouseMove(30, 1080);
		robot.keyPress(KeyEvent.VK_WINDOWS);
		robot.keyRelease(KeyEvent.VK_WINDOWS);
		robot.mouseMove(99, 133);
		
		Thread.sleep(500);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        Thread.sleep(500);
		robot.keyPress(KeyEvent.VK_T);
		robot.keyRelease(KeyEvent.VK_T);
		robot.keyPress(KeyEvent.VK_L);
		robot.keyRelease(KeyEvent.VK_L);
		robot.keyPress(KeyEvent.VK_A);
		robot.keyRelease(KeyEvent.VK_A);
		robot.keyPress(KeyEvent.VK_U);
		robot.keyRelease(KeyEvent.VK_U);
		robot.keyPress(KeyEvent.VK_N);
		robot.keyRelease(KeyEvent.VK_N);
		robot.keyPress(KeyEvent.VK_C);
		robot.keyRelease(KeyEvent.VK_C);
		robot.keyPress(KeyEvent.VK_H);
		robot.keyRelease(KeyEvent.VK_H);
		robot.keyPress(KeyEvent.VK_E);
		robot.keyRelease(KeyEvent.VK_E);
		robot.keyPress(KeyEvent.VK_R);
		robot.keyRelease(KeyEvent.VK_R);
		
		Thread.sleep(500);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		
		Thread.sleep(13000);
		robot.mouseMove(832, 656);
		robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        Thread.sleep(35000);
        robot.mouseMove(742, 414);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        Thread.sleep(500);
        robot.mouseMove(972, 726);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        
        Thread.sleep(500);
        Random random = new Random();
        int nbrLettre = random.nextInt(10) + 1;
        int lettreASCI = 0;
        for (int i = 0; i < nbrLettre; i++) {
        	lettreASCI = random.nextInt(26) + 65;
        	robot.keyPress(lettreASCI);
    		robot.keyRelease(lettreASCI);
		}
        
        Thread.sleep(500);
        robot.mouseMove(550, 811);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		// 742 414
		// 979 726
		// 550 811
//        while (true) {
//            Point mousePos = MouseInfo.getPointerInfo().getLocation();
//            System.out.println("Mouse position: " + mousePos.x + ", " + mousePos.y);
//            Thread.sleep(100);
//        }
	}
	
}
