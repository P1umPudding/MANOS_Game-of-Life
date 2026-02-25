import processing.core.*;

public class pixelanalyse extends PApplet {
   PApplet p;
   PImage img;

   pixelanalyse(PApplet parent) {
      p = parent;
   }

   public void setup() {
      img = p.loadImage("image.png");

   }

   void setGray() {
      loadPixels();
      for (int p = 0; p < img.pixels.length; p++) {
         // Loop through every pixel row

      }

   }

}
