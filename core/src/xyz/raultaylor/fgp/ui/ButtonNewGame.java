package xyz.raultaylor.fgp.ui;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import xyz.raultaylor.fgp.engine.ActionListener;
import xyz.raultaylor.fgp.engine.ui.ScaledTouchUpButton;

public class ButtonNewGame extends ScaledTouchUpButton {

    private static final float HEIGHT = 0.1f;
    private static final float TOP = -0.012f;
    private static final float PRESS_SCALE = 0.9f;

    public ButtonNewGame(TextureAtlas atlas, ActionListener actionListener) {
        super(atlas.findRegion("btPlay"), PRESS_SCALE, actionListener);
        setHeightProportion(HEIGHT);
        setTop(TOP);
    }
}
