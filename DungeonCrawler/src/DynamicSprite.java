import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class DynamicSprite extends SolidSprite {
    protected double speed = 5;
    protected int spriteSheetNumberOfColumn = 10;
    protected int timeBetweenFrame = 200; // 200 milliseconds between 2 images
    Direction direction = Direction.SOUTH;

    public DynamicSprite(double height, Image image, double width, double x, double y) {
        super(height, image, width, x, y);
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void move() {
        switch (direction) {
            case NORTH -> this.y -= speed;
            case SOUTH -> this.y += speed;
            case WEST -> this.x -= speed;
            case EAST -> this.x += speed;
        }
    }

    public void setPosition(Point position) {
        this.x = position.getX();
        this.y = position.getY();
    }

    private boolean isMovingPossible(ArrayList<Sprite> environment) {
        Rectangle2D.Double hitBox = new Rectangle2D.Double();
        switch (direction) {
            case NORTH -> hitBox.setRect(super.getHitBox().getX(), super.getHitBox().getY() - speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
            case SOUTH -> hitBox.setRect(super.getHitBox().getX(), super.getHitBox().getY() + speed,
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
            case WEST -> hitBox.setRect(super.getHitBox().getX() - speed, super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
            case EAST -> hitBox.setRect(super.getHitBox().getX() + speed, super.getHitBox().getY(),
                    super.getHitBox().getWidth(), super.getHitBox().getHeight());
        }
        for (Sprite e : environment) {
            if ((e instanceof SolidSprite) && (e != this) && ((SolidSprite) e).intersect(hitBox)) return false;
        }
        return true;
    }

    public void moveIfPossible(ArrayList<Sprite> environment) {
        if (isMovingPossible(environment)) {
            move();
        }
    }

    @Override
    public void draw(Graphics g) {
        int index = (int) (System.currentTimeMillis() / timeBetweenFrame) % spriteSheetNumberOfColumn;
        g.drawImage(image, (int) x, (int) y, (int) (x + width), (int) (y + height),
                (int) (index * width), (int) (direction.getFrameLineNumber() * height),
                (int) ((index + 1) * width), (int) ((direction.getFrameLineNumber() + 1) * height), null);
    }
}
