import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Main {
    JFrame displayZoneFrame;

    public Main() throws Exception {
        // Initialize engines and the hero
        RenderEngine renderEngine;
        PhysicEngine physicEngine;
        GameEngine gameEngine;

        displayZoneFrame = new JFrame("Java Labs");
        displayZoneFrame.setSize(400,600);
        displayZoneFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DynamicSprite hero = new DynamicSprite(50, ImageIO.read(new File("./img/heroTileSheetLowRes.png")),
                48, 200, 300);

        renderEngine = new RenderEngine(displayZoneFrame);  // Create render engine with camera
        physicEngine = new PhysicEngine();
        gameEngine = new GameEngine(hero, renderEngine, physicEngine);  // Pass render engine to game engine

        Timer renderTimer = new Timer(50, (time) -> renderEngine.update());
        Timer gameTimer = new Timer(50, (time) -> gameEngine.update());
        Timer physicTimer = new Timer(50, (time) -> physicEngine.update());

        renderTimer.start();
        gameTimer.start();
        physicTimer.start();

        renderEngine.updateCamera(hero);
        renderEngine.zoomCamera(2.0);

        displayZoneFrame.getContentPane().add(renderEngine);
        displayZoneFrame.setVisible(true);

        Playground level1 = new Playground("./data/level1.txt");

        renderEngine.addToRenderList(level1.getSpriteList());
        renderEngine.addToRenderList(hero);
        physicEngine.setEnvironment(level1.getSolidSpriteList());
        physicEngine.addToMovingSpriteList(hero);

        displayZoneFrame.addKeyListener(gameEngine);
    }

    public static void main(String[] args) throws Exception {
        Main main = new Main();
    }
}
