import processing.core.*;

public class Main extends PApplet {
   pixelanalyse pa;
   UILayout ui;
   boolean uiState = false;

   public void settings() {

      fullScreen();
      //size(800, 800);

   }

   int cubeColor = color(116, 183, 148);
   int strokeColor = color(68, 80, 66);
   int backgroundColor = color(0, 0, 0);

   int breite = 10;
   boolean[][] gitter;

   int sizeMultiplyer = 2;

   public void setup() {
      textAlign(LEFT, BOTTOM);
      textSize(24);

      pa = new pixelanalyse(this);
      pa.setup();
      frameRate(20);
      gitter = new boolean[width * sizeMultiplyer / breite][height * sizeMultiplyer / breite];
      //fülleGitterMitSeed(gitter, 696867);
      ui = new UILayout(this);
      ui.setupColors();

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
      if (keyPressed) {
         if (key == 'q') {
            fülleGitterMitSeed(gitter, 1234);
         }
      }
   }

   public void draw() {
      if (!uiState) {
         addRect();
         move();
         applyRandomSeed();

      }

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

            if ((liveCount < 1 || liveCount > 2) && gitter[x][y])
               gitter2[x][y] = false;

            if ((liveCount == 2 || liveCount == 2) && !gitter[x][y])
               gitter2[x][y] = true;

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
      if (key == ESC) {
         key = 0;
         uiState = !uiState;
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
