package com.game.config;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

public class Conts {
public static int EMMAX=250;
public static int PLAYER_BULLTE_MAX=20;
public static int PLAYER_BULLTE_X_MAX=50;
public static final Random RAN = new Random();
public static final int GAME_WEIDTH=420;
public static final int GAME_HEIGHT=650;
public static final int HP_Height=10;
public static HashMap<String,BufferedImage> IMAGES=new HashMap<String,BufferedImage>();
public static HashMap<String,File> AUDIOS=new HashMap<String,File>();

//¾²Ì¬´úÂë¿é
static{
	try {
		URI uri=Conts.class.getResource("/sucai").toURI();
		File f=new File(uri);
		File[] fs=f.listFiles();
		for (File ft : fs) {
			if(ft.getName().toLowerCase().endsWith(".jpg")||ft.getName().toLowerCase().endsWith(".png")||ft.getName().toLowerCase().endsWith(".gif")){
			
				IMAGES.put(ft.getName(), ImageIO.read(ft));
				
			}else if(ft.getName().toLowerCase().endsWith(".wav")){
				AUDIOS.put(ft.getName(), ft);
			}
		}
	} catch (URISyntaxException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}


}