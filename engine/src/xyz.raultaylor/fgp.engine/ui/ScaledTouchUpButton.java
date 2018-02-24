package xyz.raultaylor.fgp.engine.ui;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import xyz.raultaylor.fgp.engine.ActionListener;
import xyz.raultaylor.fgp.engine.Sprite;


public class ScaledTouchUpButton extends Sprite{

    private boolean isLocked = false;
    private int pointer;
    private boolean pressed;
    private float pressScale;
    private final ActionListener actionListener;

    public ScaledTouchUpButton(TextureRegion region, float pressScale, ActionListener actionListener) {
        super(region);
        this.pressScale = pressScale;
        this.actionListener = actionListener;
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        if (isLocked || pressed || !isMe(touch)) {
            return;
        }
        this.pointer = pointer;
        scale = pressScale;
        pressed = true;
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        if (isLocked || this.pointer != pointer || !pressed) {
            return;
        }
        if (isMe(touch)) {
            actionListener.actionPerformed(this);
        }
        pressed = false;
        scale = 1f;
    }

    public void lock(){
        isLocked = true;
    }

    public void unlock(){
        isLocked = false;
    }
}
