import processing.core.*;

public class pixelanalyse extends PApplet {
   PApplet p;
   PImage img;

   pixelanalyse(PApplet parent) {
      p = parent;
   }

   public void setup() {
      img = p.loadImage("image.png");
      setGray();
      transformInArray();
   }

   void setGray() {
      img.loadPixels();

      for (int i = 0; i < img.pixels.length; i++) {

         int c = img.pixels[i];

         float r = red(c);
         float g = green(c);
         float b = blue(c);

         // gewichtete Grauberechnung
         float setBW = 0.299f * r + 0.587f * g + 0.114f * b;

         if (setBW < 127)
            setBW = 0;
         else
            setBW = 255;
         img.pixels[i] = color(setBW);

      }
      img.updatePixels();
   }

   boolean[][] trueFalse;

   boolean[][] transformInArray() {

      trueFalse = new boolean[img.width][img.height];

      for (int p = 0; p < img.pixels.length; p++) {

         int x = p % img.width; // Spalte
         int y = p / img.width; // Zeile

         if (img.pixels[p] == color(0)) {
            trueFalse[x][y] = false;
         } else {
            trueFalse[x][y] = true;
         }
      }
      return trueFalse;
   }

}
