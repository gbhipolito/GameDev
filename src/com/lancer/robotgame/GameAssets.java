package com.lancer.robotgame;

import com.lancer.robotgame.framework.GamePixmap;
import com.lancer.robotgame.framework.GameMusic;
import com.lancer.robotgame.framework.GameSound;

public class GameAssets {

	public static GamePixmap menu, splash, background, character, character2,
			character3, heliboy, heliboy2, heliboy3, heliboy4, heliboy5;
	public static GamePixmap tiledirt, tilegrassTop, tilegrassBot,
			tilegrassLeft, tilegrassRight, characterJump, characterDown;
	public static GamePixmap button;
	public static GameSound click;
	public static GameMusic theme;

	public static void load( SampleGame sampleGame ) {
		// TODO Auto-generated method stub
		theme = sampleGame.getAudio().createMusic( "menutheme.mp3" );
		theme.setLooping( true );
		theme.setVolume( 0.85f );
		theme.play();
	}

}