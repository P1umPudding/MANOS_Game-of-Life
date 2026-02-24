import processing.core.*;

public class Main extends PApplet {

   public void settings() {

      fullScreen();
      //size(800, 800);

   }

   int[] cubeColor = { 0, 102, 0 };
   int[] strokeColor = { 64, 64, 64 };
   int[] backgroundColor = { 0, 51, 0 };

   int breite = 10;
   boolean[][] gitter;

   public void setup() {
      frameRate(100);
      gitter = new boolean[width / breite][height / breite];
      fülleGitterMitSeed(gitter, 1234);

   }

   void fülleGitterMitSeed(boolean[][] gitter, int seed) {
      for (int y = 0; y < gitter.length; y++) {
         for (int x = 0; x < gitter[y].length; x++) {
            // pseudo-random, deterministisch, basierend auf Seed
            long val = x * 341873128712L + y * 132897987541L + seed;
            val = (val ^ (val >> 13)) * 1274126177L; // einfache Hash-Mischung
            val = val ^ (val >> 16);
            gitter[y][x] = (val & 1) == 0; // true/false deterministisch
         }
      }
   }

   public void draw() {
      currentState();
   }

   int xReduction = 0;
   int xAddition = 0;
   int yReduction = 0;
   int yAddition = 0;

   void randCheck(int x, int y) {

      if (x == 0)
         xReduction = 0;
      else
         xReduction = 1;

      if (x == gitter.length - 1)
         xAddition = 0;
      else
         xAddition = 1;

      if (y == 0)
         yReduction = 0;
      else
         yReduction = 1;

      if (y == gitter[0].length - 1)
         yAddition = 0;
      else
         yAddition = 1;
   }

   void rules() {

      boolean[][] gitter2 = new boolean[width][height];

      for (int x = 0; x < gitter.length; x++) {
         for (int y = 0; y < gitter[0].length; y++) {

            gitter2[x][y] = gitter[x][y];

            int liveCount = 0;
            randCheck(x, y);

            for (int xx = x - xReduction; xx <= x + xAddition; xx++) {
               for (int yy = y - yReduction; yy <= y + yAddition; yy++) {
                  if (gitter[xx][yy] && (xx != x || yy != y))
                     liveCount++;
               }
            }

            if ((liveCount < 2 || liveCount > 3) && gitter[x][y])
               gitter2[x][y] = false;

            if (liveCount == 3 && !gitter[x][y])
               gitter2[x][y] = true;

         }
      }

      for (int x = 0; x < gitter.length; x++) {
         for (int y = 0; y < gitter[0].length; y++) {
            gitter[x][y] = gitter2[x][y];
         }
      }

   }

   void updateRaster() {
      for (int x = 0; x < gitter.length; x++) {
         for (int y = 0; y < gitter[0].length; y++) {

            if (gitter[x][y] == true)
               fill(cubeColor[0], cubeColor[1], cubeColor[2]);
            else
               fill(backgroundColor[0], backgroundColor[1], backgroundColor[2]);

            stroke(strokeColor[0], strokeColor[1], strokeColor[2]);
            rect(x * breite, y * breite, breite, breite);

         }
      }
   }

   public static void main(String[] args) {
      PApplet.main("Main");
   }

   boolean currentState;

   void currentState() {
      if (currentState == true) {
         background(50);
         updateRaster();
         rules();
      }
      if (currentState == false) {

      }
   }

   @Override
   public void keyPressed() {
      if (keyPressed) {
         if (key == ' ') {
            if (currentState == true)
               currentState = false;
            else
               currentState = true;
            println(currentState);
         }
      }
   }
}
