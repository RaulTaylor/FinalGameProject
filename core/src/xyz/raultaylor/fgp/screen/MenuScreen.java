package xyz.raultaylor.fgp.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import xyz.raultaylor.fgp.Background;
import xyz.raultaylor.fgp.engine.ActionListener;
import xyz.raultaylor.fgp.engine.Base2DScreen;
import xyz.raultaylor.fgp.engine.math.Rect;
import xyz.raultaylor.fgp.engine.ui.AnimationOpacityElement;
import xyz.raultaylor.fgp.engine.ui.GroupButton;
import xyz.raultaylor.fgp.engine.utils.OpacityAnimator;
import xyz.raultaylor.fgp.star.StarsController;
import xyz.raultaylor.fgp.ui.ButtonExit;
import xyz.raultaylor.fgp.ui.ButtonNo;
import xyz.raultaylor.fgp.ui.ButtonPlay;
import xyz.raultaylor.fgp.ui.ButtonYes;
import xyz.raultaylor.fgp.ui.CloseLabel;

/**
 * Created by raultaylor.
 */

public class MenuScreen extends Base2DScreen implements ActionListener {


    private boolean openCloseMenu = false;


    private Texture bgTexture;
    private TextureAtlas atlas;
    private Background background;
    private StarsController starsController;
    private AnimationOpacityElement logoGame;

    private OpacityAnimator opacityAnimatorExit;
    private OpacityAnimator opacityAnimatorLabel;
    private OpacityAnimator opacityAnimatorYes;
    private OpacityAnimator opacityAnimatorNo;
    private OpacityAnimator opacityAnimatorPlay;

    private GroupButton groupButton;

    private ButtonPlay buttonPlay;
    private ButtonExit buttonExit;

    private Background fog;
    private ButtonYes buttonYes;
    private ButtonNo buttonNo;
    private CloseLabel closeLabel;



    public MenuScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/menuAtlas.tpack");
        bgTexture = new Texture("textures/background.png");
        background = new Background(new TextureRegion(bgTexture));
        starsController = new StarsController(atlas);
        logoGame = new AnimationOpacityElement(atlas,"logo_game",new Vector2(0,0),this);
        logoGame.setWidthProportion(0.6f);
        buttonExit = new ButtonExit(atlas,0.9f,this);
        buttonExit.setWidthProportion(0.5f);
        buttonPlay = new ButtonPlay(atlas,0.9f, this);
        buttonPlay.setWidthProportion(0.5f);

        groupButton = new GroupButton(0.03f);
        groupButton.add(buttonPlay);
        groupButton.add(buttonExit);
        groupButton.setTop(logoGame.getBottom());

        opacityAnimatorExit = new OpacityAnimator(buttonExit, 0.1f,1f);
        opacityAnimatorPlay = new OpacityAnimator(buttonPlay, 0.1f,1f);

        fog = new Background(atlas.findRegion("fog"));
        buttonYes = new ButtonYes(atlas,0.9f,this);
        buttonNo = new ButtonNo(atlas,0.9f,this);
        closeLabel = new CloseLabel(atlas);
        closeLabel.setWidthProportion(0.8f);
        buttonYes.setWidthProportion(0.2f);
        buttonNo.setWidthProportion(0.2f);
        buttonNo.setBottom(closeLabel.getBottom()+0.013f);
        buttonNo.setRight(closeLabel.getRight()-0.05f);
        buttonYes.setBottom(closeLabel.getBottom()+0.013f);
        buttonYes.setLeft(closeLabel.getLeft()+0.05f);
    }


    @Override
    public void dispose() {
        super.dispose();
        atlas.dispose();
        bgTexture.dispose();

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void update(float delta){
        starsController.update(delta);
        logoGame.update(delta);
        opacityAnimatorPlay.update(delta);
        opacityAnimatorExit.update(delta);
        groupButton.update(delta);
        if(openCloseMenu){
            buttonNo.update(delta);
            buttonYes.update(delta);
            opacityAnimatorYes.update(delta);
            opacityAnimatorNo.update(delta);
            opacityAnimatorLabel.update(delta);
        }
    }

    private void draw(){
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        starsController.draw(batch);
        logoGame.draw(batch);
        groupButton.draw(batch);
        if(openCloseMenu){
            fog.draw(batch);
            closeLabel.draw(batch);
            buttonYes.draw(batch);
            buttonNo.draw(batch);
        }
        batch.end();

    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        starsController.resize(worldBounds);
        logoGame.resize(worldBounds);
        logoGame.setTop(worldBounds.getTop()-0.1f);
        groupButton.resize(worldBounds);
        fog.resize(worldBounds);
        buttonNo.resize(worldBounds);
        buttonYes.resize(worldBounds);



    }

    @Override
    public void actionPerformed(Object src) {
        if(src == buttonExit){
            openExitMenu();
        }
        if(src == buttonPlay){
            game.setScreen(new GameScreen(game));
        }
        if(src == buttonYes){
            Gdx.app.exit();
        }

        if(src == buttonNo){
            closeExitMenu();
        }


    }

    private void openExitMenu(){

        openCloseMenu = true;
        buttonPlay.lock();
        buttonExit.lock();
        buttonNo.unlock();
        buttonYes.unlock();
        opacityAnimatorLabel = new OpacityAnimator(closeLabel,0.2f,0.8f);
        opacityAnimatorNo = new OpacityAnimator(buttonNo, 0.2f, 0.8f);
        opacityAnimatorYes = new OpacityAnimator(buttonYes,0.2f,0.8f);

    }

    private void closeExitMenu(){

        openCloseMenu = false;
        buttonExit.unlock();
        buttonPlay.unlock();
        buttonYes.lock();
        buttonNo.lock();

    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        buttonPlay.touchUp(touch,pointer);
        buttonExit.touchUp(touch,pointer);
        buttonNo.touchUp(touch,pointer);
        buttonYes.touchUp(touch, pointer);
        super.touchUp(touch, pointer);
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        buttonExit.touchDown(touch,pointer);
        buttonPlay.touchDown(touch,pointer);
        buttonYes.touchDown(touch, pointer);
        buttonNo.touchDown(touch, pointer);
        super.touchDown(touch, pointer);
    }
}
