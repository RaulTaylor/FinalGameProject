package xyz.raultaylor.fgp.engine.utils;

import xyz.raultaylor.fgp.engine.Sprite;

/**
 * Created by raultaylor.
 */

public class OpacityAnimator {

    private Sprite sprite;
    private float timer;
    private float currentTimer;
    private float currentOpacity;

    public OpacityAnimator(Sprite sprite, float beginOpacity, float timer) {
        this.sprite = sprite;
        this.currentOpacity = beginOpacity;
        this.timer = timer;
    }

    public void update(float delta) {
        if (currentTimer < timer) {
            currentTimer += delta;
            currentOpacity += delta / timer * 0.9f;
            if(currentOpacity>1f){
                currentOpacity = 1f;
            }
        } else {
            currentOpacity = 1f;
        }


        sprite.setOpacity(currentOpacity);

    }

}
