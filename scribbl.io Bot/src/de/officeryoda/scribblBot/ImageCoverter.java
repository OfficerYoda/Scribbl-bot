package de.officeryoda.scribblBot;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

public class ImageCoverter {

	private String basePath = "D:\\Coding\\ScribbleBot\\";

	public static void main(String[] args) {
		try {
			new ImageCoverter();
		} catch (IOException | AWTException e) {
			e.printStackTrace();
		}
	}
	
	public ImageCoverter() throws IOException, AWTException {
		System.out.println("Start");

		//rename all Folder with name latest
		renameFolder(new File(basePath), "latest");

		//create Folder
		File folder = new File(basePath + "latest");
		folder.mkdirs();
		System.out.println("Folder created");
		
		//get a valid File
		String imagePath = waitTillValidFile(folder, ".jpg", ".png");
		System.out.println("valid File");
		
		//adjust resolution
		BufferedImage outputImage = resize(imagePath, basePath + "latest\\output.jpg", 83, 63);
		System.out.println("resized");
		
		//make Pixels only specific colors
		Color[][] pixelColors = adjustColours(outputImage);
		System.out.println("adjusted Colours");
		
		//paint the image
		Painter painter = new Painter(pixelColors);
		painter.paint();
		
		System.out.println("End all");
	}

	private String waitTillValidFile(File folder, String... validFileTypes) {
		List<String> validFileTypesList = Arrays.asList(validFileTypes);

		//wait till File is in folder
		while(isDirEmpty(folder.toPath())) {
			try { Thread.sleep(1000); } catch (InterruptedException e) { }
		}

		String fileName = folder.listFiles()[0].getAbsolutePath().replace(basePath + "latest", "");
		String fileType = fileName.substring(fileName.indexOf("."));

		//if not valid file, delete file and wait for valid file
		if(!validFileTypesList.contains(fileType)) {
			folder.listFiles()[0].delete();
			return waitTillValidFile(folder, validFileTypes);
		}

		return folder.listFiles()[0].getAbsolutePath();
	}

	private boolean isDirEmpty(final Path directory) {
		try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
			return !dirStream.iterator().hasNext();
		} catch (IOException e) { e.printStackTrace(); }
		return false;
	}

	private void renameFolder(File directory, String... removeNames) {
		long crntTime = System.currentTimeMillis();
		int timeAdd = 0;

		for(File file : directory.listFiles())
			for (String string : removeNames)
				if(file.getName().equals(string))
					file.renameTo(new File(file.getAbsolutePath().replace(string, crntTime + timeAdd + "")));

	}

	private BufferedImage resize(String inputImagePath, String outputImagePath, int scaledWidth, int scaledHeight) throws IOException {
		// reads input image
		File inputFile = new File(inputImagePath);
		BufferedImage inputImage = ImageIO.read(inputFile);

		// creates output image
		BufferedImage outputImage = new BufferedImage(scaledWidth, scaledHeight, inputImage.getType());

		// scales the input image to the output image
		Graphics2D g2d = outputImage.createGraphics();
		g2d.drawImage(inputImage, 0, 0, scaledWidth, scaledHeight, null);
		g2d.dispose();

		// extracts extension of output file
		String formatName = outputImagePath.substring(outputImagePath.lastIndexOf(".") + 1);

		// writes to output file
		ImageIO.write(outputImage, formatName, new File(outputImagePath));

		return outputImage;
	}

	private Color[][] adjustColours(BufferedImage image) throws IOException {
		
		Color[][] pixelColors = new Color[83][63];
		
		BufferedImage adjustedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

		//loop through every pixel
		for (int y = 0; y < image.getHeight(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
				//get color fromPixel
				Color clr = new Color(image.getRGB(x, y));
				
				//check for closest Color
				double closest = 500;
				Color clostestClr = null;
				for(Color clr2 : ScribblColors.allColors) {
					double distance = colorDistance(clr, clr2);
					if(distance < closest) {
						closest = distance;
						clostestClr = clr2;
					}
				}
				adjustedImage.setRGB(x, y, clostestClr.getRGB());
				pixelColors[x][y] = clostestClr;
			}
		}
		
		ImageIO.write(adjustedImage, "jpg", new File(basePath + "latest\\adjusted.jpg"));

		return pixelColors;
	}

	private double colorDistance(Color c1, Color c2) {
		int r1 = c1.getRed();
		int r2 = c2.getRed();
		int g1 = c1.getGreen();
		int g2 = c2.getGreen();
		int b1 = c1.getBlue();
		int b2 = c2.getBlue();
		return Math.sqrt((r1-r2)*(r1-r2) + (g1-g2)*(g1-g2) + (b1-b2)*(b1-b2));
	}
}
