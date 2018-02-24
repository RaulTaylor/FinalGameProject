package xyz.raultaylor.fgp;


import com.badlogic.gdx.Game;

import xyz.raultaylor.fgp.screen.LogoScreen;


public class SFGGame extends Game {

	@Override
	public void create() {
		setScreen(new LogoScreen(this));
	}

}
