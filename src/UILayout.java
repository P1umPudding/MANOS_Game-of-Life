import processing.core.*;

public class UILayout {

   Main p; // Referenz auf dein Hauptfenster

   int colorBg;
   int colorPanel;
   int colorGreen;
   int colorCyan;
   int colorTextWhite;
   int colorTextGrey;

   // Konstruktor: Empfängt das Main-Applet
   public UILayout(Main p) {
      this.p = p;
   }

   public void setupColors() {
      colorBg = p.color(15, 15, 15);
      colorPanel = p.color(35, 35, 35);
      colorGreen = p.color(0, 200, 130);
      colorCyan = p.color(0, 180, 220);
      colorTextWhite = p.color(240);
      colorTextGrey = p.color(150);
   }

   public void uiScreenshot1Cards() {
      p.pushStyle();
      float cardW = 300;
      float cardH = 160;
      float gap = 20;
      // p.width und p.height anstatt width und height
      float menuStartX = (p.width / 2) - cardW - gap / 2;
      float menuStartY = (p.height / 2) - 50 - cardH - gap / 2;

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
            p.stroke(colorGreen);
            p.strokeWeight(1.5f);
            p.fill(20, 30, 25);
         } else {
            p.stroke(50);
            p.strokeWeight(1);
            p.fill(colorPanel);
         }
         p.rect(cx, cy, cardW, cardH, 15);

         p.noStroke();
         p.fill(active[i] ? colorGreen : colorCyan);
         p.rect(cx + 20, cy + 20, 25, 25, 5);
         p.fill(colorBg);
         p.rect(cx + 25, cy + 25, 15, 15, 2);

         p.fill(colorTextWhite);
         p.textSize(18);
         p.textAlign(PApplet.LEFT, PApplet.TOP);
         p.text(titles[i], cx + 20, cy + 70);

         p.fill(colorTextGrey);
         p.textSize(13);
         p.textLeading(18);
         p.text(desc[i], cx + 20, cy + 100);
      }
      p.popStyle();
   }

   public void uiScreenshot2ControlBar() {
      p.pushStyle();
      float barWidth = 700;
      float barHeight = 70;
      float startX = (p.width / 2) - barWidth / 2;
      float barY = p.height - 60;

      p.fill(colorPanel);
      p.noStroke();
      p.rect(startX, barY - barHeight / 2, barWidth, barHeight, 35);

      p.fill(colorGreen);
      p.ellipse(startX + 50, barY, 50, 50);
      p.fill(10);
      p.triangle(startX + 43, barY - 10, startX + 43, barY + 10, startX + 60, barY);

      p.fill(50);
      p.ellipse(startX + 115, barY, 45, 45);
      p.fill(colorTextWhite);
      p.triangle(startX + 108, barY - 8, startX + 108, barY + 8, startX + 118, barY);
      p.rect(startX + 120, barY - 8, 3, 16);

      p.stroke(70);
      p.line(startX + 160, barY - 15, startX + 160, barY + 15);

      p.noStroke();
      p.fill(colorTextGrey);
      p.textAlign(PApplet.LEFT, PApplet.CENTER);
      p.textSize(11);
      p.text("SPEED", startX + 180, barY - 12);
      p.textAlign(PApplet.RIGHT, PApplet.CENTER);
      p.text("100%", startX + 380, barY - 12);

      p.fill(20);
      p.rect(startX + 180, barY + 5, 200, 6, 3);
      p.fill(colorGreen);
      p.rect(startX + 180, barY + 5, 120, 6, 3);
      p.ellipse(startX + 300, barY + 8, 16, 16);

      float btnX = startX + 440;
      for (int i = 0; i < 5; i++) {
         p.fill(50);
         p.noStroke();
         p.ellipse(btnX + (i * 55), barY, 45, 45);
         p.fill(colorTextWhite);
         p.ellipse(btnX + (i * 55), barY, 5, 5);
      }
      p.popStyle();
   }
}