import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.awt.geom.Rectangle2D;

public class GameEngine implements Engine, KeyListener {
    private final DynamicSprite hero;
    private Playground currentLevel;
    private final Playground level1;
    private final Playground level2;
    private boolean isLevel1 = true;
    private final RenderEngine renderEngine;
    private final PhysicEngine physicEngine;
    private boolean transitioning = false; // Ajout d'un flag pour gérer les transitions

    public GameEngine(DynamicSprite hero, RenderEngine renderEngine, PhysicEngine physicEngine) {
        this.hero = hero;
        this.renderEngine = renderEngine;
        this.physicEngine = physicEngine;
        Camera camera = renderEngine.getCamera();

        level1 = new Playground("./data/level1.txt");
        level2 = new Playground("./data/level2.txt");
        currentLevel = level1;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                hero.setDirection(Direction.NORTH);
                break;
            case KeyEvent.VK_DOWN:
                hero.setDirection(Direction.SOUTH);
                break;
            case KeyEvent.VK_LEFT:
                hero.setDirection(Direction.WEST);
                break;
            case KeyEvent.VK_RIGHT:
                hero.setDirection(Direction.EAST);
                break;
            case KeyEvent.VK_A:
                renderEngine.zoomCamera(1.1);  // Zoom in
                break;
            case KeyEvent.VK_Z:
                renderEngine.zoomCamera(0.9);  // Zoom out
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void update() {
        renderEngine.updateCamera(hero);
        renderEngine.getRenderList().clear();
        renderEngine.getRenderList().addAll(currentLevel.getSpriteList());
        renderEngine.getRenderList().add(hero);

        // Vérifier la transition uniquement si aucune transition n'est en cours
        if (!transitioning && checkTransition()) {
            transitioning = true; // Bloquer les transitions supplémentaires
            switchLevel();
        }

        // Mettre à jour l'environnement physique (pour la détection des collisions et autres)
        physicEngine.setEnvironment(currentLevel.getSolidSpriteList());

        // Configurer la caméra pour le nouveau niveau
        int mapWidth = currentLevel.getMapWidth();  // Largeur de la carte en pixels
        int mapHeight = currentLevel.getMapHeight(); // Hauteur de la carte en pixels
        renderEngine.setMapSize(mapWidth, mapHeight);
    }

    private boolean checkTransition() {
        Rectangle2D heroPosition = hero.getHitBox();

        for (Point transitionPoint : currentLevel.getTransitionPoints()) {
            Rectangle transitionRect = new Rectangle(
                    transitionPoint.x * Playground.TILE_SIZE,
                    transitionPoint.y * Playground.TILE_SIZE,
                    Playground.TILE_SIZE,
                    Playground.TILE_SIZE
            );
            if (heroPosition.intersects(transitionRect)) {
                return true;
            }
        }
        return false;
    }

    private void switchLevel() {
        if (isLevel1) {
            currentLevel = level2;
            isLevel1 = false;
        } else {
            currentLevel = level1;
            isLevel1 = true;
        }

        // Move the hero to the starting position of the new level
        Point transitionPoint = currentLevel.getTransitionPoints().get(0); // Example: First transition point
        hero.setPosition(new Point(transitionPoint.x * Playground.TILE_SIZE, transitionPoint.y * Playground.TILE_SIZE));

        // Delay re-enabling transitions to avoid multiple triggers
        new Thread(() -> {
            try {
                Thread.sleep(1500); // Wait 1200 ms before allowing another transition
            } catch (InterruptedException ignored) {
            }
            transitioning = false;
        }).start();
    }
}

