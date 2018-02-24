package xyz.raultaylor.fgp.engine.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import xyz.raultaylor.fgp.engine.math.Rect;

/**
 * Created by raultaylor.
 */

public class GroupButton extends Rect {

    private ArrayList<ScaledTouchUpButton> buttons;
    private float space;
    private float currentHeight;
    private float tempHeight;

    public GroupButton(float space){
        buttons = new ArrayList<ScaledTouchUpButton>();
        this.space = space;
    }

    public void add(ScaledTouchUpButton button){
        buttons.add(button);
        currentHeight+=space;
        currentHeight+=button.getHeight();
        setHeight(currentHeight);

    }

    public void resize(Rect worldBounds){
        currentHeight = 0;
        for(ScaledTouchUpButton button: buttons){
            button.resize(worldBounds);
            currentHeight+=space;
            currentHeight+=button.getHeight();
            button.setBottom(-currentHeight);
        }
    }

    public void update(float delta){
        for(ScaledTouchUpButton button: buttons){
            button.update(delta);
        }
    }

    public void draw(SpriteBatch batch){
        for(ScaledTouchUpButton button: buttons){
            button.draw(batch);
        }
    }

    public void setTop(float top){
        super.setTop(top);
        tempHeight = 0;
        for(ScaledTouchUpButton button: buttons){
            tempHeight+=space;
            button.setTop(top-tempHeight);
            tempHeight+=button.getHeight();
        }
    }

}
