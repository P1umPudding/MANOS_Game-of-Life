import processing.core.*;

public class pixelanalyse { // kein PApplet mehr
   PApplet p;
   PImage img;

   public pixelanalyse(PApplet parent) {
      p = parent;
   }

   /**
    * Bild laden, auf cols×rows skalieren und in ein boolsches Feld
    * umwandeln (weiß = true, schwarz = false).
    */
   public boolean[][] transformInArray(int cols, int rows) {
      img = p.loadImage("image.png");
      if (img == null) { // fehlende Datei abfangen
         PApplet.println("image.png nicht gefunden");
         return new boolean[cols][rows];
      }

      img.resize(cols, rows);
      img.loadPixels();

      boolean[][] result = new boolean[cols][rows];

      for (int i = 0; i < img.pixels.length; i++) {
         int x = i % img.width;
         int y = i / img.width;
         int c = img.pixels[i];

         float gray = 0.299f * p.red(c)
               + 0.587f * p.green(c)
               + 0.114f * p.blue(c);

         result[x][y] = gray >= 127; // Schwellenwert 127
      }
      return result;
   }
}