import processing.core.*;

public class Main extends PApplet {
   pixelanalyse pa;
   UILayout ui;
   CurrentscreenToSvg svgExporter;
   boolean uiState = false;

   public void settings() {

      fullScreen();
      //size(800, 800);

   }

   int cubeColor = color(116, 183, 148);
   int strokeColor = color(68, 80, 66);
   int backgroundColor = color(0, 0, 0);

   int breite = 4;
   boolean[][] gitter;

   int sizeMultiplyer = 1;

   // Game of Life rule parameters (modifiable via keybinds)
   int spawnCount = 2; // neighbor count required for birth
   int despawnMin = 1; // minimum neighbors for a live cell to survive
   int despawnMax = 2; // maximum neighbors for a live cell to survive

   /*
    * void pixelanalyseStart() {
    * if (keyPressed && key == 'i') {
    * pa = new pixelanalyse(this);
    * // Rastergröße an die vorhandene gitter‑Dimension anpassen
    * gitter = pa.transformInArray(gitter.length, gitter[0].length);
    * generation = 0;
    * }
    */

   public void setup() {
      frameRate(10);
      gitter = new boolean[width * sizeMultiplyer / breite][height * sizeMultiplyer / breite];
      //fülleGitterMitSeed(gitter, 696867);
      ui = new UILayout(this);
      ui.setupColors();
      svgExporter = new CurrentscreenToSvg(this);

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

   void applyRandomSeed() {
      if (mousePressed && mouseButton == LEFT && uiState) {
         float cardW = 300;
         float cardH = 160;
         float gap = 20;
         float menuStartX = (width / 2) - cardW - gap / 2;
         float menuStartY = (height / 2) - 50 - cardH - gap / 2;

         float cx = menuStartX;
         float cy = menuStartY;

         if (mouseX > cx && mouseX < cx + cardW &&
               mouseY > cy && mouseY < cy + cardH) {
            fülleGitterMitSeed(gitter, (int) random(0, 299999999));
            uiState = false;
         }
      }
   }

   public void draw() {
      // pixelanalyseStart();
      if (!uiState) {
         addRect();
         move();

      }

      applyRandomSeed();
      currentState();
      uiElements();
      fill(255);
      text("Generation: " + generation, 10, height - 10);
   }

   int xReduction = 0;
   int xAddition = 0;
   int yReduction = 0;
   int yAddition = 0;

   void randCheck(int x, int y) {

      if (x == 0)
         xReduction = 0;
      else if (x >= 1)
         xReduction = 1;
      //   else
      //xReduction = 2;

      if (x == gitter.length - 1)
         xAddition = 0;
      else if (x <= gitter.length - 2)
         xAddition = 1;
      //   else
      //xAddition = 2;

      if (y == 0)
         yReduction = 0;
      else if (y >= 1)
         yReduction = 1;
      //   else
      //yReduction = 2;

      if (y == gitter[0].length - 1)
         yAddition = 0;
      else if (y <= gitter[0].length - 2)
         yAddition = 1;
      //  else
      //   yAddition = 2;

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

            // despawn logic using current rule parameters
            boolean shouldDespawn;
            if (despawnMin > despawnMax) {
               // special case: only survive at exactly despawnMin neighbors
               shouldDespawn = (liveCount != despawnMin) && gitter[x][y];
            } else {
               shouldDespawn = (liveCount < despawnMin || liveCount > despawnMax) && gitter[x][y];
            }
            if (shouldDespawn) {
               gitter2[x][y] = false;
            }

            // spawn logic using current spawnCount
            if ((liveCount == spawnCount) && !gitter[x][y]) {
               gitter2[x][y] = true;
            }

         }
      }
      generation++;
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

   int generation = 0;

   void currentState() {
      if (pauseButton == true) {
         background(0);
         updateRaster();
         rules();

      }

   }

   void uiElements() {
      if (uiState) {
         fill(0, 180);
         rect(0, 0, width, height);

         ui.uiScreenshot1Cards();
      }
   }

   public void keyPressed() {
      clearGrid();
      if (keyPressed) {
         if (key == ' ') {
            if (pauseButton == true)
               pauseButton = false;
            else
               pauseButton = true;

         }
      }
      if (key == 'e' || key == 'E') {
         svgExporter.exportCurrentScreenToSvg();
      }
      if (key == 'i' || key == 'I') {
         svgExporter.importScreenshotToSvg("screenshot_01");
      }
      if (key == ESC) {
         key = 0;
         uiState = !uiState;
      }

      // Game of Life rule keybinds (only when uiState is false)
      if (!uiState) {
         if (key == '1') {
            // rule 1: spawn=3, despawn if <2 or >3
            spawnCount = 3;
            despawnMin = 2;
            despawnMax = 3;
            println("Rule 1 selected (spawn 3, despawn <2 or >3)");
            fülleGitterMitSeed(gitter, (int) random(0, 299999999));
         }
         if (key == '2') {
            // rule 2: spawn=4, survive only at 2 neighbors
            spawnCount = 4;
            despawnMin = 2;
            despawnMax = 1; // signal special case
            println("Rule 2 selected (spawn 4, survive only at 2)");
            fülleGitterMitSeed(gitter, (int) random(0, 299999999));
         }
         if (key == '3') {
            // rule 3: spawn=3, despawn <1 or >4
            spawnCount = 3;
            despawnMin = 1;
            despawnMax = 4;
            println("Rule 3 selected (spawn 3, despawn <1 or >4)");
            fülleGitterMitSeed(gitter, (int) random(0, 299999999));
         }
         if (key == '4') {
            // rule 4: standard Conway
            spawnCount = 2;
            despawnMin = 1;
            despawnMax = 2;
            println("Rule 4 selected (spawn 2, despawn <1 or >2)");
            fülleGitterMitSeed(gitter, (int) random(0, 299999999));
         }
         if (key == '6') {
            println("Key 6 pressed - no rule assigned");
         }
         if (key == '7') {
            println("Key 7 pressed - no rule assigned");
         }
         if (key == '8') {
            println("Key 8 pressed - no rule assigned");
         }
         if (key == '9') {
            println("Key 9 pressed - no rule assigned");
         }
      }
   }

   void addRect() {
      if (mousePressed && (mouseButton == LEFT)) {
         for (int x = 0; x < gitter.length; x++) {
            for (int y = 0; y < gitter[0].length; y++) {
               if (mouseX + camX > x * breite && mouseX + camX < x * breite + breite) {
                  if (mouseY + camY > y * breite && mouseY + camY < y * breite + breite) {
                     if (gitter[x][y]) {
                        gitter[x][y] = false;
                        delay(100);
                     } else {

                        gitter[x][y] = true;
                        delay(100);

                     }

                  }
               }
            }
         }
         updateRaster();

      }

   }

   void clearGrid() {
      if (keyPressed) {
         if (key == 'r') {

            for (int x = 0; x < gitter.length; x++) {
               for (int y = 0; y < gitter[0].length; y++) {
                  gitter[x][y] = false;
               }

            }
            generation = 0;
            delay(10);
         }
      }
   }

   float camX = 0;
   float camY = 0;
   float speed;

   void move() {
      speed = breite;
      if (keyPressed) {
         if (key == 'w')
            camY -= speed;
         if (key == 's')
            camY += speed;
         if (key == 'a')
            camX -= speed;
         if (key == 'd')
            camX += speed;
      }

      translate(-camX, -camY);
      background(0);
      updateRaster();

   }

}
