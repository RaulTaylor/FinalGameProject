package xyz.raultaylor.fgp.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import xyz.raultaylor.fgp.Background;
import xyz.raultaylor.fgp.bullet.Bullet;
import xyz.raultaylor.fgp.bullet.BulletPool;
import xyz.raultaylor.fgp.engine.ActionListener;
import xyz.raultaylor.fgp.engine.Base2DScreen;
import xyz.raultaylor.fgp.engine.font.Font;
import xyz.raultaylor.fgp.engine.math.Rect;
import xyz.raultaylor.fgp.explosion.ExplosionPool;
import xyz.raultaylor.fgp.ship.EnemyEmmiter;
import xyz.raultaylor.fgp.ship.EnemyPool;
import xyz.raultaylor.fgp.ship.EnemyShip;
import xyz.raultaylor.fgp.ship.MainShip;
import xyz.raultaylor.fgp.star.StarsController;
import xyz.raultaylor.fgp.ui.ButtonBackToMenu;
import xyz.raultaylor.fgp.ui.ButtonNewGame;
import xyz.raultaylor.fgp.ui.MessageGameOver;


public class GameScreen extends Base2DScreen implements ActionListener {

    private enum State {PLAYING, GAME_OVER}

    private State state;

    private static final int STAR_COUNT = 56;
    private static final float STAR_HEIGHT = 0.01f;
    private static final float FONT_SIZE = 0.02f;

    private Texture backgroundTexture;
    private Background background;

    private TextureAtlas atlas;
    private TextureAtlas atlas2;

    private MainShip mainShip;


    private final BulletPool bulletPool = new BulletPool();
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;

    private EnemyEmmiter enemyEmmiter;

    private Sound soundExplosion;
    private Sound soundLaser;
    private Sound soundBullet;
    private Music music;

    private StarsController starsController;

    private int frags;

    private Font font;
    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHP = new StringBuilder();
    private StringBuilder sbStage = new StringBuilder();

    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;
    private ButtonBackToMenu buttonBackToMenu;

    public GameScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        super.show();

        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        soundExplosion = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        soundLaser = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        soundBullet = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));

        backgroundTexture = new Texture("textures/background.png");
        background = new Background(new TextureRegion(backgroundTexture));


        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        atlas2 = new TextureAtlas("textures/gameAtlas2.tpack");

        starsController = new StarsController(atlas2);

        this.explosionPool = new ExplosionPool(atlas, soundExplosion);
        mainShip = new MainShip(atlas2, bulletPool, explosionPool, worldBounds, soundLaser);

        this.enemyPool = new EnemyPool(bulletPool, explosionPool, worldBounds, mainShip, soundBullet);
        this.enemyEmmiter = new EnemyEmmiter(enemyPool, worldBounds, atlas);

        this.messageGameOver = new MessageGameOver(atlas);
        this.buttonNewGame = new ButtonNewGame(atlas2, this);
        this.buttonBackToMenu = new ButtonBackToMenu(atlas2, 0.9f, this);
        buttonBackToMenu.setHeightProportion(0.1f);
        buttonBackToMenu.setTop(buttonNewGame.getBottom());


        this.font = new Font("font/font.fnt", "font/font.png");
        this.font.setWordSize(FONT_SIZE);

        startNewGame();
    }

    public void printInfo() {
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbStage.setLength(0);
        font.draw(batch, sbFrags.append("Frags:").append(frags), worldBounds.getLeft(), worldBounds.getTop());
        font.draw(batch, sbHP.append("HP:").append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        font.draw(batch, sbStage.append("Stage:").append(enemyEmmiter.getStage()), worldBounds.getRight(), worldBounds.getTop(), Align.right);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        if (state == State.PLAYING) {
            checkCollisions();
        }
        deleteAllDestroyed();
        update(delta);
        draw();
    }

    public void checkCollisions() {

        // столкновение кораблей
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        for (EnemyShip enemy : enemyShipList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.setDestroyed(true);
                enemy.boom();
                mainShip.damage(mainShip.getHp());
                return;
            }
        }

        // нанесение урона вражескому кораблю
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (EnemyShip enemy : enemyShipList) {
            if (enemy.isDestroyed()) {
                continue;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBulletCollision(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.setDestroyed(true);
                    if (enemy.isDestroyed()) {
                        frags++;
                        break;
                    }
                }
            }
        }

        // нанесение урона игровому кораблю
        for (Bullet bullet : bulletList) {
            if (bullet.isDestroyed() || bullet.getOwner() == mainShip) {
                continue;
            }
            if (mainShip.isBulletCollision(bullet)) {
                mainShip.damage(bullet.getDamage());
                bullet.setDestroyed(true);
            }
        }
    }

    public void deleteAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
        enemyPool.freeAllDestroyedActiveObjects();
    }

    public void update(float delta) {
        starsController.update(delta);
        explosionPool.updateActiveObjects(delta);
        switch (state) {
            case PLAYING:
                bulletPool.updateActiveObjects(delta);
                enemyPool.updateActiveObjects(delta);
                mainShip.update(delta);
                enemyEmmiter.generateEnemy(delta, frags);
                if (mainShip.isDestroyed()) {
                    state = State.GAME_OVER;
                }
                break;
            case GAME_OVER:
                break;

        }
    }

    public void draw() {
        Gdx.gl.glClearColor(0.7f, 0.3f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        starsController.draw(batch);
        explosionPool.drawActiveObjects(batch);
        printInfo();

        if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
            buttonBackToMenu.draw(batch);
        } else {
            bulletPool.drawActiveObjects(batch);
            enemyPool.drawActiveObjects(batch);
            mainShip.draw(batch);
        }
        batch.end();
    }

    @Override
    protected void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        starsController.resize(worldBounds);
        mainShip.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        backgroundTexture.dispose();
        atlas.dispose();
        atlas2.dispose();
        bulletPool.dispose();
        soundLaser.dispose();
        soundBullet.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        soundExplosion.dispose();
        font.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            mainShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    protected void touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchDown(touch, pointer);
        } else {
            buttonNewGame.touchDown(touch, pointer);
            buttonBackToMenu.touchDown(touch, pointer);
        }
    }

    @Override
    protected void touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            mainShip.touchUp(touch, pointer);
        } else {
            buttonNewGame.touchUp(touch, pointer);
            buttonBackToMenu.touchUp(touch, pointer);
        }
    }

    private void startNewGame() {
        enemyEmmiter.setToNewGame();

        state = State.PLAYING;
        frags = 0;

        mainShip.setToNewGame();

        bulletPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
    }

    @Override
    public void actionPerformed(Object src) {
        if (src == buttonNewGame) {
            startNewGame();
        } else if (src == buttonBackToMenu) {
            game.setScreen(new MenuScreen(game));
        } else {
            throw new RuntimeException("Unknown src");
        }

    }
}
