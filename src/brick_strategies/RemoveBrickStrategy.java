package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

public class RemoveBrickStrategy implements CollisionStrategy {
    private GameObjectCollection gameObjects;

    public RemoveBrickStrategy(GameObjectCollection gameObjects) {

        this.gameObjects = gameObjects;
    }



    @Override
    public void onColllision(GameObject thisObj, GameObject otherObj) {
            gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
    }
}
