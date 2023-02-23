package Robot;

import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Main {

	public static void main(String[] args) throws TesseractException {
//		RunMinecraft minecraft = new RunMinecraft();
//		ScreenCapture capture = new ScreenCapture();
//		capture.capture("file");
		Tesseract tesseract = new Tesseract();
		tesseract.setDatapath("D:\\Tess4J-3.4.8-src\\Tess4J");
		String text = tesseract.doOCR(new File("chaos1.png"));
		System.out.println(text);
	}
	
}
