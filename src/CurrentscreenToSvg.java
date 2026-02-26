import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentscreenToSvg {

   Main main;
   String outputFolder = "svg_exports";

   public CurrentscreenToSvg(Main main) {
      this.main = main;
      createOutputFolder();
   }

   // Create the output folder if it doesn't exist
   void createOutputFolder() {
      File folder = new File(outputFolder);
      if (!folder.exists()) {
         folder.mkdirs();
      }
   }

   // Convert current grid to SVG and save
   public void exportCurrentScreenToSvg() {
      String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
      String filename = outputFolder + "/grid_" + timestamp + ".svg";

      try {
         FileWriter writer = new FileWriter(filename);
         writer.write(generateSvg(main.gitter, main.breite, main.cubeColor, main.backgroundColor));
         writer.close();
         System.out.println("SVG exported: " + filename);
      } catch (IOException e) {
         System.err.println("Error saving SVG: " + e.getMessage());
      }
   }

   // Generate SVG content from grid
   String generateSvg(boolean[][] gitter, int cellSize, int aliveColor, int deadColor) {
      int width = gitter.length * cellSize;
      int height = gitter[0].length * cellSize;

      StringBuilder svg = new StringBuilder();
      svg.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
      svg.append("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + width + "\" height=\"" + height + "\">\n");

      // Add background
      svg.append("  <rect width=\"" + width + "\" height=\"" + height + "\" fill=\"" + colorToHex(deadColor)
            + "\"/>\n");

      // Generate rectangles for each cell
      for (int x = 0; x < gitter.length; x++) {
         for (int y = 0; y < gitter[x].length; y++) {
            if (gitter[x][y]) { // If cell is alive
               int posX = x * cellSize;
               int posY = y * cellSize;
               svg.append("  <rect x=\"" + posX + "\" y=\"" + posY + "\" width=\"" + cellSize + "\" height=\""
                     + cellSize + "\" fill=\"" + colorToHex(aliveColor) + "\"/>\n");
            }
         }
      }

      svg.append("</svg>");
      return svg.toString();
   }

   // Convert Processing color to hex format
   String colorToHex(int color) {
      // Extract RGB components
      int r = (color >> 16) & 0xFF;
      int g = (color >> 8) & 0xFF;
      int b = color & 0xFF;
      return String.format("#%02x%02x%02x", r, g, b);
   }

   // Overload for custom filename
   public void exportCurrentScreenToSvg(String filename) {
      String fullPath = outputFolder + "/" + filename;

      try {
         FileWriter writer = new FileWriter(fullPath);
         writer.write(generateSvg(main.gitter, main.breite, main.cubeColor, main.backgroundColor));
         writer.close();
         System.out.println("SVG exported: " + fullPath);
      } catch (IOException e) {
         System.err.println("Error saving SVG: " + e.getMessage());
      }
   }
}
