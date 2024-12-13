import java.util.ArrayList;

public class PhysicEngine implements Engine {
    protected ArrayList<Sprite> environment;
    protected ArrayList<DynamicSprite> movingSpriteList;

    public PhysicEngine() {
        environment = new ArrayList<>();
        movingSpriteList = new ArrayList<>();
    }

    public void setEnvironment(ArrayList<Sprite> environment) {
        this.environment = environment;
    }

    public void addToMovingSpriteList(DynamicSprite sprite){
        if (!movingSpriteList.contains(sprite)){
            movingSpriteList.add(sprite);
        }
    }

    @Override
    public void update() {
        for (DynamicSprite s : movingSpriteList){
            s.moveIfPossible(environment);
        }
    }
}
