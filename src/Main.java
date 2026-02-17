import processing.core.*;

public class Main extends PApplet {

    // ---- settings (size goes here) ----
    public void settings() {
        size(800, 600);
    }

    // ---- runs once at startup ----
    public void setup() {
        background(0);
    }

    // ---- runs every frame ----
    public void draw() {
        background(128);
        fill(255);
        ellipse(mouseX, mouseY, 50, 50);
    }

    // ---- entry point ----
    public static void main(String[] args) {
        PApplet.main("Main");
    }
}
