package xyz.raultaylor.fgp.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import xyz.raultaylor.fgp.engine.ActionListener;
import xyz.raultaylor.fgp.engine.Base2DScreen;
import xyz.raultaylor.fgp.engine.math.Rect;
import xyz.raultaylor.fgp.engine.ui.AnimationOpacityElement;


/**
 * Created by raultaylor.
 */

public class LogoScreen extends Base2DScreen implements ActionListener {

    private TextureAtlas atlas;
    private AnimationOpacityElement logoGame;
    private AnimationOpacityElement logoCompany;
    private AnimationOpacityElement currentElement;

    public LogoScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/logoAtlas.tpack");
        logoGame = new AnimationOpacityElement(atlas,"logo_game",new Vector2(0,0),this);
        logoCompany = new AnimationOpacityElement(atlas,"logo_company",new Vector2(0,0), this);
        currentElement = logoCompany;
    }

    @Override
    public void dispose() {
        super.dispose();
        atlas.dispose();
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        currentElement.touchUp(touch,pointer);
        super.touchUp(touch, pointer);
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        logoGame.resize(worldBounds);
        logoCompany.resize(worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void draw(){
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        currentElement.draw(batch);
        batch.end();
    }

    private void update(float delta){
        currentElement.update(delta);
    }

    @Override
    public void actionPerformed(Object src) {
        if(src == logoCompany){
            currentElement = logoGame;
        }
        if(src == logoGame){
            game.setScreen(new MenuScreen(game));
        }
    }
}
