import processing.core.*;

public class UILayout extends PApplet {

   int colorBg;
   int colorPanel;
   int colorGreen;
   int colorCyan;
   int colorTextWhite;
   int colorTextGrey;

   public void settings() {
      size(1200, 800);
   }

   public void setup() {
      colorBg = color(15, 15, 15);
      colorPanel = color(35, 35, 35);
      colorGreen = color(0, 200, 130);
      colorCyan = color(0, 180, 220);
      colorTextWhite = color(240);
      colorTextGrey = color(150);
   }

   public void draw() {
      background(colorBg);
      uiElements();
   }

   public void uiElements() {
      uiScreenshot1Cards();
      uiScreenshot2ControlBar();
   }

   public void uiScreenshot1Cards() {
      pushStyle();
      float cardW = 300;
      float cardH = 160;
      float gap = 20;
      float menuStartX = (width / 2) - cardW - gap / 2;
      float menuStartY = (height / 2) - 50 - cardH - gap / 2;

      String[] titles = { "Zufälliger Seed", "Glider", "Pulsar", "Gosper Glider Gun" };
      String[] desc = {
            "Generiert ein zufälliges Muster im\nZentrum.",
            "A small pattern that travels across\nthe grid.",
            "A large, beautiful period-3 oscillator.",
            "The first known pattern that\nproduces infinite growth."
      };
      boolean[] active = { true, false, false, false };

      for (int i = 0; i < 4; i++) {
         float cx = menuStartX + (i % 2) * (cardW + gap);
         float cy = menuStartY + (i / 2) * (cardH + gap);

         if (active[i]) {
            stroke(colorGreen);
            strokeWeight(1.5f);
            fill(20, 30, 25);
         } else {
            stroke(50);
            strokeWeight(1);
            fill(colorPanel);
         }
         rect(cx, cy, cardW, cardH, 15);

         noStroke();
         fill(active[i] ? colorGreen : colorCyan);
         rect(cx + 20, cy + 20, 25, 25, 5);
         fill(colorBg);
         rect(cx + 25, cy + 25, 15, 15, 2);

         fill(colorTextWhite);
         textSize(18);
         textAlign(LEFT, TOP);
         text(titles[i], cx + 20, cy + 70);

         fill(colorTextGrey);
         textSize(13);
         textLeading(18);
         text(desc[i], cx + 20, cy + 100);
      }
      popStyle();
   }

   public void uiScreenshot2ControlBar() {
      pushStyle();
      float barWidth = 700;
      float barHeight = 70;
      float startX = (width / 2) - barWidth / 2;
      float barY = height - 60;

      fill(colorPanel);
      noStroke();
      rect(startX, barY - barHeight / 2, barWidth, barHeight, 35);

      fill(colorGreen);
      ellipse(startX + 50, barY, 50, 50);
      fill(10);
      triangle(startX + 43, barY - 10, startX + 43, barY + 10, startX + 60, barY);

      fill(50);
      ellipse(startX + 115, barY, 45, 45);
      fill(colorTextWhite);
      triangle(startX + 108, barY - 8, startX + 108, barY + 8, startX + 118, barY);
      rect(startX + 120, barY - 8, 3, 16);

      stroke(70);
      line(startX + 160, barY - 15, startX + 160, barY + 15);

      noStroke();
      fill(colorTextGrey);
      textAlign(LEFT, CENTER);
      textSize(11);
      text("SPEED", startX + 180, barY - 12);
      textAlign(RIGHT, CENTER);
      text("100%", startX + 380, barY - 12);

      fill(20);
      rect(startX + 180, barY + 5, 200, 6, 3);
      fill(colorGreen);
      rect(startX + 180, barY + 5, 120, 6, 3);
      ellipse(startX + 300, barY + 8, 16, 16);

      float btnX = startX + 440;
      for (int i = 0; i < 5; i++) {
         fill(50);
         noStroke();
         ellipse(btnX + (i * 55), barY, 45, 45);
         fill(colorTextWhite);
         ellipse(btnX + (i * 55), barY, 5, 5);
      }
      popStyle();
   }

   public static void main(String[] args) {
      PApplet.main("UILayout");
   }
}