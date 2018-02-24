package xyz.raultaylor.fgp.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import xyz.raultaylor.fgp.engine.ActionListener;
import xyz.raultaylor.fgp.engine.ui.ScaledTouchUpButton;

/**
 * Created by raultaylor.
 */

public class ButtonBackToMenu extends ScaledTouchUpButton {
    public ButtonBackToMenu(TextureAtlas atlas, float pressScale, ActionListener actionListener) {
        super(atlas.findRegion("btnToMenu"), pressScale, actionListener);
    }
}
