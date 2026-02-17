import processing.core.PApplet;

class Ball {
   static PApplet p;
   static float x, y, vx, vy, radius;
   static int col;

   static void init(PApplet p) {
      Ball.p = p;
      x = p.width / 2;
      y = p.height / 2;
      vx = 3;
      vy = 2;
      radius = 25;
      col = p.color(255, 100, 100);
   }

   static void update() {
      x += vx;
      y += vy;
      if (x - radius < 0 || x + radius > p.width)
         vx *= -1;
      if (y - radius < 0 || y + radius > p.height)
         vy *= -1;
   }

   static void draw() {
      p.fill(col);
      p.noStroke();
      p.ellipse(x, y, radius * 2, radius * 2);
   }
}
