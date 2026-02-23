import processing.core.*;

public class Main extends PApplet {

   public void settings() {
      size(400, 400);

   }

   int breite = 20;
   boolean[][] Gitter = new boolean[width][height];

   public void setup() {}

   public void draw() {
      background(50);

      Gitter[0][0] = true;

      updateRaster();

   }

   void updateRaster() {
      for (int x = 0; x < Gitter.length; x++) {
         for (int y = 0; y < Gitter.length; y++) {

            if (Gitter[x][y] == true)
               fill(255);
            else
               fill(0);

            stroke(150);
            rect(x * breite, y * breite, breite, breite);

         }
      }
   }

   public static void main(String[] args) {
      PApplet.main("Main");
   }

}
