import processing.core.*;

public class Main extends PApplet {

   public void settings() {
      size(400, 400);

   }

   int breite = 20;
   boolean[][] Gitter = new boolean[width][height];

   public void setup() {
      Gitter[1][0] = true;

   }

   public void draw() {
      background(50);

      Rule1();
      Rule2();
      Rule3();
      Rule4();

      updateRaster();
      delay(1);
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

      if (x == Gitter.length)
         xa = 0;
      else
         xa = 1;

      if (y == 0)
         yr = 0;
      else
         yr = 1;

      if (y == Gitter.length)
         ya = 0;
      else
         ya = 1;
   }

   void Rule1() {
      for (int x = 0; x < Gitter.length; x++) {
         for (int y = 0; y < Gitter.length; y++) {
            int liveCount = 0;
            RandCheck(x, y);

            if (Gitter[x][y]) {
               for (int xx = x - xr; xx <= x + xa; xx++) {
                  for (int yy = y - yr; yy <= y + ya; yy++) {
                     if (Gitter[xx][yy])
                        liveCount++;
                  }
               }
            }
            if (liveCount < 3)
               Gitter[x][y] = false;
         }
      }
   }

   void Rule2() {
      for (int x = 0; x < Gitter.length; x++) {
         for (int y = 0; y < Gitter.length; y++) {
            int liveCount = 0;
            RandCheck(x, y);
            if (Gitter[x][y]) {
               for (int xx = x - xr; xx <= x + xa; xx++) {
                  for (int yy = y - yr; yy <= y + ya; yy++) {
                     if (Gitter[xx][yy])
                        liveCount++;

                  }
               }
            }
            if (liveCount == 3 || liveCount == 4)
               Gitter[x][y] = true;

         }

      }

   }

   void Rule3() {
      for (int x = 0; x < Gitter.length; x++) {
         for (int y = 0; y < Gitter.length; y++) {
            int liveCount = 0;
            RandCheck(x, y);
            if (Gitter[x][y]) {
               for (int xx = x - xr; xx <= x + xa; xx++) {
                  for (int yy = y - yr; yy <= y + ya; yy++) {
                     if (Gitter[xx][yy])
                        liveCount++;

                  }
               }
            }
            if (liveCount > 4)
               Gitter[x][y] = false;

         }

      }

   }

   void Rule4() {
      for (int x = 0; x < Gitter.length; x++) {
         for (int y = 0; y < Gitter.length; y++) {
            int liveCount = 0;
            RandCheck(x, y);
            if (Gitter[x][y]) {
               for (int xx = x - xr; xx <= x + xa; xx++) {
                  for (int yy = y - yr; yy <= y + ya; yy++) {
                     if (Gitter[xx][yy])
                        liveCount++;

                  }
               }
            }
            if (liveCount == 4)
               Gitter[x][y] = true;

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
