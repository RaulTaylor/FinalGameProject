package xyz.raultaylor.fgp.ship;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import xyz.raultaylor.fgp.bullet.Bullet;
import xyz.raultaylor.fgp.bullet.BulletPool;
import xyz.raultaylor.fgp.engine.math.Rect;
import xyz.raultaylor.fgp.explosion.ExplosionPool;


public class MainShip extends Ship {

    private static final float SHIP_WIDTH = 0.18f;
    private static final float BOTTOM_MARGIN = 0.05f;
    private static final int INVALID_POINTER = -1;

    private final Vector2 v0 = new Vector2(0.5f, 0.0f);
    
    private boolean pressedLeft;
    private boolean pressedRight;



    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;


    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, Sound laserSound) {
        super(atlas.findRegion("mainShip"), 1, 2, 2, bulletPool, explosionPool, worldBounds, laserSound);
        setWidthProportion(SHIP_WIDTH);
        this.bulletRegion = atlas.findRegion("plazma");
    }

    public void setToNewGame() {
        pos.x = worldBounds.pos.x;
        this.bulletHeight = 0.03f;
        this.bulletV.set(0, 0.5f);
        this.bulletDamage = 1;
        this.reloadInterval = 0.2f;
        leftPointer = -1;
        rightPointer = -1;
        stop();

        hp = 100;
        setDestroyed(false);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    public void keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
        }
    }

    public void keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if (pressedRight) {
                    moveRight();
                } else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if (pressedLeft) {
                    moveLeft();
                } else {
                    stop();
                }
                break;
            case Input.Keys.UP:
                shoot();
                break;
        }
    }

    @Override
    public void touchDown(Vector2 touch, int pointer) {
        if (worldBounds.pos.x > touch.x) {
            if (leftPointer != INVALID_POINTER) return;
            leftPointer = pointer;
            moveLeft();
        } else {
            if (rightPointer != INVALID_POINTER) return;
            rightPointer = pointer;
            moveRight();
        }
    }

    @Override
    public void touchUp(Vector2 touch, int pointer) {
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) moveRight(); else stop();
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) moveLeft(); else stop();
        }
    }

    public boolean isBulletCollision(Rect bullet) {
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom()
        );
    }

    private void moveRight() {
        v.set(v0);
    }

    private void moveLeft() {
        v.set(v0).rotate(180);
    }

    private void stop() {
        v.setZero();
    }

    @Override
    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, bulletDamage);
        bullet.setTop(this.getTop());
        shootSound.play();
    }
}