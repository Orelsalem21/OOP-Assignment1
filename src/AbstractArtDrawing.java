import biuoop.GUI;
import biuoop.DrawSurface;
import java.util.Random;
import java.awt.Color;

public class AbstractArtDrawing {

    public void drawArt() {
        GUI gui = new GUI("Abstract Art", 800, 600);
        DrawSurface d = gui.getDrawSurface();
        Random rand = new Random();

        // array of 10 lines
        Line[] lines = new Line[10];

        // create and draw 10 random lines
        for (int i = 0; i < lines.length; i++) {
            int x1 = rand.nextInt(800) + 1;
            int y1 = rand.nextInt(600) + 1;
            int x2 = rand.nextInt(800) + 1;
            int y2 = rand.nextInt(600) + 1;

            Line line = new Line(x1, y1, x2, y2);
            lines[i] = line;

            d.setColor(Color.BLACK);
            d.drawLine((int) line.start().getX(),
                    (int) line.start().getY(),
                    (int) line.end().getX(),
                    (int) line.end().getY());
        }
        // draw middle points in BLUE
        for (int i = 0; i < lines.length; i++) {
            Point mid = lines[i].middle();

            d.setColor(Color.BLUE);
            d.fillCircle((int) mid.getX(), (int) mid.getY(), 3);
        }
        // draw intersections in RED
        for (int i = 0; i < lines.length; i++) {
            for (int j = i + 1; j < lines.length; j++) {

                Point inter = lines[i].intersectionWith(lines[j]);

                if (inter != null) {
                    d.setColor(Color.RED);
                    d.fillCircle((int) inter.getX(), (int) inter.getY(), 3);
                }
            }
        }
        gui.show(d);
    }

    public static void main(String[] args) {
        AbstractArtDrawing art = new AbstractArtDrawing();
        art.drawArt();
    }
}
