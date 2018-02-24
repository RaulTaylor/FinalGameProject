package xyz.raultaylor.fgp.star;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import xyz.raultaylor.fgp.engine.Sprite;
import xyz.raultaylor.fgp.engine.math.Rect;
import xyz.raultaylor.fgp.engine.math.Rnd;

public class Star extends Sprite {

    private final Vector2 v = new Vector2();
    private Rect worldBounds;
    private float enterWidth;

    private TextureAtlas atlas;

    private final String[] NAMES_REGION = {"red_star","yellow_star","blue_star"};

    public Star(TextureAtlas atlas, float vx, float vy, float width) {
        super(atlas.findRegion("red_star"));
        this.atlas = atlas;
        changeTexture();
        setAngle(Rnd.nextFloat(0,90f));
        v.set(vx, vy);
        enterWidth = width;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (checkAndHandleBounds()) {
            pos.set(Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight()), pos.y);
            setWidthProportion(Rnd.nextFloat( enterWidth/ 2, enterWidth * 2));
        }
    }

    protected boolean checkAndHandleBounds() {
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
            return true;
        }
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
            return true;
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop()+Rnd.nextFloat(0,1f));
            return true;
        }
//        if (getBottom() > worldBounds.getTop()) {
//            setTop(worldBounds.getBottom());
//            return true;
//        }
        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;

    }

    public void reset(){
        setWidthProportion(Rnd.nextFloat(enterWidth / 2, enterWidth * 2));
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
//        float posX = worldBounds.getLeft() + 0.2f;
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
//        float posY = worldBounds.getTop();
        changeTexture();
        setAngle(Rnd.nextFloat(0,90f));
        pos.set(posX, posY);
    }

    public void changeTexture(){
        int i = (int)Rnd.nextFloat(0,3);
        this.setRegion(atlas.findRegion(NAMES_REGION[i]),0);
    }
}
