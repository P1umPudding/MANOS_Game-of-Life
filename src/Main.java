import processing.core.*;

public class Main extends PApplet {

   public void settings() {

      fullScreen();
      //size(800, 800);

   }

   int breite = 10;
   boolean[][] Gitter = new boolean[width][height];

   public void setup() {

      Gitter[3][2] = true;

   }

   public void draw() {
      background(50);

      updateRaster();

      Rules();

      delay(200);
   }

   int xr = 0;
   int xa = 0;
   int yr = 0;
   int ya = 0;

   void RandCheck(int x, int y) {

      if (x == 0)
         xr = 0;
      else
         xr = 1;

      if (x == Gitter.length - 1)
         xa = 0;
      else
         xa = 1;

      if (y == 0)
         yr = 0;
      else
         yr = 1;

      if (y == Gitter.length - 1)
         ya = 0;
      else
         ya = 1;
   }

   void Rules() {

      boolean[][] Gitter2 = new boolean[width][height];

      for (int x = 0; x < Gitter.length; x++) {
         for (int y = 0; y < Gitter.length; y++) {

            Gitter2[x][y] = Gitter[x][y];

            int liveCount = 0;
            RandCheck(x, y);

            for (int xx = x - xr; xx <= x + xa; xx++) {
               for (int yy = y - yr; yy <= y + ya; yy++) {
                  if (Gitter[xx][yy] && (xx != x || yy != y))
                     liveCount++;
               }
            }

            if (liveCount > 0)
               println(liveCount);
            if ((liveCount < 2 || liveCount > 3) && Gitter[x][y])
               Gitter2[x][y] = false;

            if (liveCount == 3 && !Gitter[x][y])
               Gitter2[x][y] = true;

         }
      }

      for (int x = 0; x < Gitter.length; x++) {
         for (int y = 0; y < Gitter.length; y++) {
            Gitter[x][y] = Gitter2[x][y];
         }
      }

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
