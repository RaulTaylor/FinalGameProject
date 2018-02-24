package xyz.raultaylor.fgp.star;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import xyz.raultaylor.fgp.engine.math.Rect;

/**
 * Created by raultaylor.
 */

public class StarsController {
    private Star[][] stars;
    private Rect worldBounds;
    private boolean firstStart;

    private float maxSpeed = 0.15f;
    private int COUNT_LAYER = 4;
    private int COUNT_STARS = 50;
    private float WIDTH_STAR = 0.015f;


    public StarsController(TextureAtlas atlas){
        stars = new Star[COUNT_LAYER][COUNT_STARS];
        for(int i=0;i<COUNT_LAYER;i++){
            for(int j=0;j<COUNT_STARS;j++){
                stars[i][j] = new Star(atlas,0, -(maxSpeed/(COUNT_LAYER-i)),WIDTH_STAR/(COUNT_LAYER-i));
            }
        }
        firstStart = true;
        worldBounds = new Rect();
    }

    public void update(float delta){
        for(Star[] arr: stars){
            for(Star star: arr){
                star.update(delta);
            }
        }
    }

    public void reset(){
        for(Star[] arr: stars){
            for(Star star: arr){
                star.reset();
            }
        }
    }



    public void draw(SpriteBatch spriteBatch){
        for(Star[] arr: stars){
            for(Star star: arr){
                star.draw(spriteBatch);
            }
        }
    }

    public void resize(Rect worldBounds){
        for(Star[] arr: stars){
            for(Star star: arr){
                star.resize(worldBounds);
            }
        }
    }

    public void setWorldBounds(Rect worldBounds){
        this.worldBounds.set(worldBounds);
        if(firstStart){
            resize(worldBounds);
            reset();
            firstStart = false;
        }else{
            resize(worldBounds);
        }
    }
}
