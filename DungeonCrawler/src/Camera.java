import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Camera {
    private double x, y; // Position de la caméra
    private double zoomLevel; // Niveau de zoom
    private final int windowWidth, windowHeight; // Taille de la fenêtre
    private int mapWidth, mapHeight; // Dimensions de la carte

    public Camera(int width, int height) {
        this.windowWidth = width;
        this.windowHeight = height;
        this.zoomLevel = 1.0; // Zoom par défaut
    }

    // Définit les dimensions de la carte
    public void setMapSize(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    // Suit le héros
    public void followHero(Rectangle2D heroPosition) {
        double newX = heroPosition.getX() + heroPosition.getWidth() / 2 - (windowWidth / (2 * zoomLevel));
        double newY = heroPosition.getY() + heroPosition.getHeight() / 2 - (windowHeight / (2 * zoomLevel));

        // Contraindre les coordonnées de la caméra dans les limites de la carte
        x = Math.max(0, Math.min(newX, mapWidth - (windowWidth / zoomLevel)));
        y = Math.max(0, Math.min(newY, mapHeight - (windowHeight / zoomLevel)));
    }

    // Applique les transformations de la caméra
    public void apply(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Appliquer le zoom et la translation
        g2d.scale(zoomLevel, zoomLevel);
        g2d.translate(-x, -y);
    }

    // Zoom in or out
    public void zoom(double zoomFactor) {
        this.zoomLevel *= zoomFactor;

        // Clamp zoom level (optional; can adjust these bounds)
        if (this.zoomLevel < 1) this.zoomLevel = 1;  // Minimum zoom (50%)
        if (this.zoomLevel > 3.0) this.zoomLevel = 3.0;  // Maximum zoom (300%)
    }
}
