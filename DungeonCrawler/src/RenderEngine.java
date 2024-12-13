import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class RenderEngine extends JPanel implements Engine {
    protected ArrayList<Displayable> renderList;
    private final Camera camera;

    public RenderEngine(JFrame jFrame) {
        renderList = new ArrayList<>();
        camera = new Camera(jFrame.getWidth(), jFrame.getHeight());
    }

    public void setMapSize(int mapWidth, int mapHeight) {
        camera.setMapSize(mapWidth, mapHeight);
    }

    public void addToRenderList(Displayable displayable) {
        if (!renderList.contains(displayable)){
            renderList.add(displayable);
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        camera.apply(g);
        renderList.forEach(l -> l.draw(g));
    }

    @Override
    public void update() {
        repaint();
    }

    public void addToRenderList(ArrayList<Displayable> displayables){
        for (Displayable displayable : displayables) {
            if (!renderList.contains(displayable)) {
                renderList.add(displayable);
            }
        }
    }

    // Replaces the current render list with a new one
    public void setRenderList(ArrayList<Displayable> newRenderList) {
        this.renderList = newRenderList;
    }

    // Method to zoom the camera in and out based on user input
    public void zoomCamera(double zoomFactor) {
        camera.zoom(zoomFactor);
    }

    public void updateCamera(DynamicSprite hero) {
        camera.followHero(hero.getHitBox());
    }

    public Camera getCamera() {
        return this.camera;
    }

    public ArrayList<Displayable> getRenderList() {
        return renderList;
    }

}