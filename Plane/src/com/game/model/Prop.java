package com.game.model;

import java.awt.Rectangle;

public class Prop extends GameModel {
	public Prop(int x,int y,int w,int h,int p,String img){
		setX(x);
		setY(y);
		setWidth(w);
		setHeight(h);
		setProp(p);
		setImage(img);
	}
	public Prop(){
		
	}
	public Rectangle getHurtRec(){
		return new Rectangle(getX(),getY(),getWidth(),getHeight());
	}
}
