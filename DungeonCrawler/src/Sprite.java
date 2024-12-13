import java.awt.*;

public class Sprite implements Displayable {
    protected final double height;
    protected final Image image;
    protected final double width;
    protected double x;
    protected double y;

    public Sprite(double height, Image image, double width, double x, double y) {
        this.height = height;
        this.image = image;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw(Graphics g) {
       g.drawImage(image, (int)x, (int)y, null);
    }
}
