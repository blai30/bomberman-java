package gameobjects;

import util.ResourceCollection;

public class ExplosionCross extends GameObject {

    ExplosionCross(int power) {
        // power * sprite length * 2 for vertical and horizontal sprite
        // two colliders, one for each
        // center sprite
        super(ResourceCollection.Images.EXPLOSION.getImage());
    }

}
