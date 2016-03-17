package ui;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ResourceLoader {
 public static InputStream load(String path) {
  InputStream input = ResourceLoader.class.getResourceAsStream(path);
  if (input == null) {
   input = ResourceLoader.class.getResourceAsStream("/"+path);
  }
  return input;
 }
 
 public static ImageIcon loadImage(String imageResource) {
	 ImageIcon image = null;
	 try {
		image = new ImageIcon(ImageIO.read(ResourceLoader.load(imageResource)));
	} catch (IOException e) {
		e.printStackTrace();
	}
	 return image;
 }
}