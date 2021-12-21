package gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

public class CollisionStrategy {

    private GameObjectCollection gameObjects;

    public CollisionStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    public void onColllision(GameObject thisObj, GameObject otherObj) {
        gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
//        System.out.println("collision with brick detected");
    }
}
