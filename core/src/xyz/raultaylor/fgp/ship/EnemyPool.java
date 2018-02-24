package xyz.raultaylor.fgp.ship;

import com.badlogic.gdx.audio.Sound;

import xyz.raultaylor.fgp.bullet.BulletPool;
import xyz.raultaylor.fgp.engine.math.Rect;
import xyz.raultaylor.fgp.engine.pool.SpritesPool;
import xyz.raultaylor.fgp.explosion.ExplosionPool;


public class EnemyPool extends SpritesPool<EnemyShip> {

    private final BulletPool bulletPool;
    private final ExplosionPool explosionPool;
    private final Rect worldBounds;
    private final MainShip mainShip;
    private final Sound bulletSound;

    public EnemyPool(BulletPool bulletPool, ExplosionPool explosionPool, Rect worldBounds, MainShip mainShip, Sound bulletSound) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.worldBounds = worldBounds;
        this.mainShip = mainShip;
        this.bulletSound = bulletSound;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool, worldBounds, mainShip, bulletSound);
    }
}
