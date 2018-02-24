package xyz.raultaylor.fgp.ui;


import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import xyz.raultaylor.fgp.Background;
import xyz.raultaylor.fgp.engine.Sprite;

/**
 * Created by raultaylor.
 */

public class CloseLabel extends Sprite {
    public CloseLabel(TextureAtlas atlas) {
        super(atlas.findRegion("close_label"));
    }
}
