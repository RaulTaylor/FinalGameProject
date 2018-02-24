package xyz.raultaylor.fgp.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import xyz.raultaylor.fgp.engine.ActionListener;
import xyz.raultaylor.fgp.engine.ui.ScaledTouchUpButton;

/**
 * Created by raultaylor.
 */

public class ButtonNo extends ScaledTouchUpButton {
    public ButtonNo(TextureAtlas atlas, float pressScale, ActionListener actionListener) {
        super(atlas.findRegion("btnNo"), pressScale, actionListener);
    }
}
