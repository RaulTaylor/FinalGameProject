package xyz.raultaylor.fgp.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import xyz.raultaylor.fgp.engine.ActionListener;
import xyz.raultaylor.fgp.engine.ui.ScaledTouchUpButton;

/**
 * Created by raultaylor.
 */

public class ButtonYes extends ScaledTouchUpButton {
    public ButtonYes(TextureAtlas atlas, float pressScale, ActionListener actionListener) {
        super(atlas.findRegion("btnYes"), pressScale, actionListener);
    }
}
