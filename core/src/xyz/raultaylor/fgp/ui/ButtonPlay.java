package xyz.raultaylor.fgp.ui;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import xyz.raultaylor.fgp.engine.ActionListener;

import xyz.raultaylor.fgp.engine.ui.ScaledTouchUpButton;


public class ButtonPlay extends ScaledTouchUpButton {

    public ButtonPlay(TextureAtlas atlas, float pressScale, ActionListener actionListener) {
        super(atlas.findRegion("btPlay"), pressScale, actionListener);
    }

}
