import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Playground {
    private final ArrayList<Sprite> environment = new ArrayList<>();
    private final ArrayList<Point> transitionPoints = new ArrayList<>();
    public static int TILE_SIZE = 0;

    private int mapWidth = 0;  // Largeur de la carte en nombre de tuiles
    private int mapHeight = 0; // Hauteur de la carte en nombre de tuiles

    public Playground(String pathName) {
        // Charger l'image et déterminer TILE_SIZE (inchangé)
        Image imageTree = null;
        try {
            imageTree = ImageIO.read(new File("./img/tree.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        TILE_SIZE = imageTree != null ? imageTree.getWidth(null) : 32;

        try {
            final Image imageGrass = ImageIO.read(new File("./img/grass.png"));
            final Image imageRock = ImageIO.read(new File("./img/rock.png"));

            BufferedReader bufferedReader = new BufferedReader(new FileReader(pathName));
            String line = bufferedReader.readLine();

            // Déterminer la largeur de la carte
            if (line != null) {
                mapWidth = line.length(); // Longueur de la première ligne = largeur en tuiles
            }

            while (line != null) {
                for (int columnNumber = 0; columnNumber < line.length(); columnNumber++) {
                    char element = line.charAt(columnNumber);

                    int x = columnNumber * TILE_SIZE;
                    int y = mapHeight * TILE_SIZE;

                    switch (element) {
                        case 'T' -> environment.add(new SolidSprite(TILE_SIZE, imageTree, TILE_SIZE, x, y));
                        case ' ' -> environment.add(new Sprite(TILE_SIZE, imageGrass, TILE_SIZE, x, y));
                        case 'R' -> environment.add(new SolidSprite(TILE_SIZE, imageRock, TILE_SIZE, x, y));
                        case '.' -> {
                            environment.add(new Sprite(TILE_SIZE, imageGrass, TILE_SIZE, x, y));
                            transitionPoints.add(new Point(columnNumber, mapHeight));
                        }
                    }
                }
                mapHeight++;  // Incrémente la hauteur à chaque ligne lue
                line = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getters pour la largeur et la hauteur
    public int getMapWidth() {
        return mapWidth * TILE_SIZE; // Retourne la largeur en pixels
    }

    public int getMapHeight() {
        return mapHeight * TILE_SIZE; // Retourne la hauteur en pixels
    }

    public ArrayList<Sprite> getSolidSpriteList() {
        ArrayList<Sprite> solidSpriteArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            if (sprite instanceof SolidSprite) solidSpriteArrayList.add(sprite);
        }
        return solidSpriteArrayList;
    }

    public ArrayList<Displayable> getSpriteList() {
        ArrayList<Displayable> displayableArrayList = new ArrayList<>();
        for (Sprite sprite : environment) {
            displayableArrayList.add((Displayable) sprite);
        }
        return displayableArrayList;
    }

    public ArrayList<Point> getTransitionPoints() {
        return transitionPoints;
    }
}

