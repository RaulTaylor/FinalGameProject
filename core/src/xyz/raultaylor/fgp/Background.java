package xyz.raultaylor.fgp;


import com.badlogic.gdx.graphics.g2d.TextureRegion;

import xyz.raultaylor.fgp.engine.Sprite;
import xyz.raultaylor.fgp.engine.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }
}
