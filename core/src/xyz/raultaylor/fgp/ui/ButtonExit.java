package xyz.raultaylor.fgp.ui;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import xyz.raultaylor.fgp.engine.ActionListener;

import xyz.raultaylor.fgp.engine.ui.ScaledTouchUpButton;


public class ButtonExit extends ScaledTouchUpButton {

    public ButtonExit(TextureAtlas atlas, float pressScale, ActionListener actionListener) {
        super(atlas.findRegion("btExit"), pressScale, actionListener);
    }

}
