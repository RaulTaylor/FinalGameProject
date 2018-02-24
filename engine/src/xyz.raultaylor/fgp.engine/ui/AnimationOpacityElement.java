package xyz.raultaylor.fgp.engine.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import xyz.raultaylor.fgp.engine.ActionListener;
import xyz.raultaylor.fgp.engine.Sprite;

/**
 * Created by raultaylor.
 */

public class AnimationOpacityElement extends Sprite {

    private final float TIME_ANIMATION = 3f;
    private final float WAITING_TIME = 1f;
    private final float WIDTH_SIZE = 0.8f;

    private float currentTimer;
    private float currentOpacity;

    private ActionListener actionListener;


    public AnimationOpacityElement(TextureAtlas atlas,String name, Vector2 pos, ActionListener actionListener) {
        super(atlas.findRegion(name));
        setWidthProportion(WIDTH_SIZE);
        this.pos.set(pos);
        this.actionListener = actionListener;
        this.currentOpacity = 0f;
        this.currentTimer = 0f;
    }


    @Override
    public void touchUp(Vector2 touch, int pointer) {
        if(currentTimer<TIME_ANIMATION){
            currentTimer = TIME_ANIMATION;
            currentOpacity = 1f;
        }
        super.touchUp(touch, pointer);
    }

    @Override
    public void draw(SpriteBatch batch) {
        setOpacity(currentOpacity);
        super.draw(batch);
    }

    @Override
    public void update(float delta) {
        if(currentTimer<TIME_ANIMATION+WAITING_TIME){
            currentTimer+=delta;
            if(currentTimer<TIME_ANIMATION){
                currentOpacity = 0.1f + (currentTimer/TIME_ANIMATION)*0.9f;
            } else {
                currentOpacity = 1f;
            }
        } else {
            actionListener.actionPerformed(this);
        }
        super.update(delta);
    }
}
