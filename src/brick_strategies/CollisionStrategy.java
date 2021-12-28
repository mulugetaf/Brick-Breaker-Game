package brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;

public interface CollisionStrategy {


//    private GameObjectCollection gameObjects;

//    public CollisionStrategy(GameObjectCollection gameObjects) {
//        this.gameObjects = gameObjects;
//    }

     void onColllision(GameObject thisObj, GameObject otherObj) ;
       // gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
//        System.out.println("collision with brick detected");


}
