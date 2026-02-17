import processing.core.*;

public class Main extends PApplet {

   public void settings() {
      size(800, 600);
   }

   public void setup() {
      Ball.init(this);
   }

   public void draw() {
      background(0);
      Ball.update();
      Ball.draw();
   }

   public static void main(String[] args) {
      PApplet.main("Main");
   }
}
