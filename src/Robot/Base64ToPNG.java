package Robot;

import java.io.*;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;

import java.util.Base64;

public class Base64ToPNG {

	private static void changeToImg(String chaine) {
		String base64String = chaine; 

	      try {
	         byte[] imageBytes = Base64.getDecoder().decode(base64String); 
	         ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
	         BufferedImage image = ImageIO.read(bis); 
	         bis.close();

	         File outputfile = new File("image.png");
	         ImageIO.write(image, "png", outputfile);

	         System.out.println("L'image a été convertie avec succès en PNG.");

	      } catch (IOException e) {
	         System.out.println("Erreur lors de la conversion de l'image en PNG : " + e.getMessage());
	      }
	}
	
	private static String changeToBase64(File img) {
		try {
	         File imageFile = img; 
	         BufferedImage image = ImageIO.read(imageFile); 
	         ByteArrayOutputStream bos = new ByteArrayOutputStream();
	         ImageIO.write(image, "png", bos); 
	         bos.close();

	         byte[] imageBytes = bos.toByteArray();
	         String base64String = Base64.getEncoder().encodeToString(imageBytes); 

	         System.out.println("L'image a été convertie avec succès en base64 : " + base64String);
	         return base64String;
	      } catch (IOException e) {
	         System.out.println("Erreur lors de la conversion de l'image en base64 : " + e.getMessage());
	         return "Erreur lors de la conversion de l'image en base64 : " + e.getMessage();
	      }
	}
	
   public static void main(String[] args) {

   }

   public String ImgToBase64(File img) {
		try {
	         File imageFile = img; 
	         BufferedImage image = ImageIO.read(imageFile); 
	         ByteArrayOutputStream bos = new ByteArrayOutputStream();
	         ImageIO.write(image, "png", bos); 
	         bos.close();

	         byte[] imageBytes = bos.toByteArray();
	         String base64String = Base64.getEncoder().encodeToString(imageBytes); 

	         System.out.println("L'image a été convertie avec succès en base64 : " + base64String);
	         return base64String;
	      } catch (IOException e) {
	         System.out.println("Erreur lors de la conversion de l'image en base64 : " + e.getMessage());
	         return "Erreur lors de la conversion de l'image en base64 : " + e.getMessage();
	      }
	}

	public void PdfToBase64() throws IOException {
		File file = new File("cour1_codage.pdf");
		byte[] fileContent = Files.readAllBytes(file.toPath());
		String base64Encoded = Base64.getEncoder().encodeToString(fileContent);
		File base64File = new File("file.txt");
		FileWriter writer = new FileWriter(base64File);
		writer.write(base64Encoded);
		writer.close();
	}
}
