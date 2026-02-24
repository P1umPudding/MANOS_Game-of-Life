import processing.core.*;

public class Main extends PApplet {

   public void settings() {

      fullScreen();
      //size(800, 800);

   }

   int cubeColor = color(116, 183, 148);
   int strokeColor = color(68, 80, 66);
   int backgroundColor = color(0, 0, 0);

   int breite = 10;
   boolean[][] gitter;

   public void setup() {
      frameRate(10);
      gitter = new boolean[width / breite][height / breite];
<<<<<<< HEAD
      fülleGitterMitSeed(gitter, 6767);
=======
      fülleGitterMitSeed(gitter, 696867);
>>>>>>> 7488c9fae5c33c3908c42cef5b9e3667e110efd2

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
      addRect();
      currentState();
      uiElements();
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
               fill(cubeColor);
            else
               fill(backgroundColor);

            stroke(strokeColor);
            rect(x * breite, y * breite, breite, breite);

         }
      }
   }

   public static void main(String[] args) {
      PApplet.main("Main");
   }

   boolean pauseButton;
   boolean uiState;

   void currentState() {
      if (pauseButton == true) {
         background(0);
         updateRaster();
         rules();
      }
      if (pauseButton == false) {

      }
   }

   void uiElements() {
      if (uiState == true) {

      }
   }

   public void keyPressed() {
      if (keyPressed) {
         if (key == ' ') {
            if (pauseButton == true)
               pauseButton = false;
            else
               pauseButton = true;
            println(pauseButton);
         }
      }
   }

   void addRect() {
      if (mousePressed && (mouseButton == LEFT)) {
         for (int x = 0; x < gitter.length; x++) {
            for (int y = 0; y < gitter[0].length; y++) {
               if (mouseX > x * breite && mouseX < x * breite + breite) {
                  if (mouseY > y * breite && mouseY < y * breite + breite) {
                     if (gitter[x][y]) {
                        gitter[x][y] = false;
                        delay(100);
                     } else {

                        gitter[x][y] = true;
                        delay(100);

                     }
                     updateRaster();
                  }
               }
            }
         }
      }

   }

}
