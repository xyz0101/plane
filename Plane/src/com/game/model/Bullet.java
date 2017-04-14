package com.game.model;

import java.awt.Rectangle;

import com.game.config.BulletType;
import com.game.config.Conts;

public class Bullet extends GameModel {
	private int attact;
	private BulletType bt;
	public boolean Move(){
		int yy=this.getY();
		yy-=this.getSpeedy();
		if(yy<=-this.getHeight()){
			return false;
		}
		else {
			setY(yy);
			return true;
		}
			
	}
	public boolean MoveXL(){
		int yy=this.getY();
		int xx=this.getX();
		xx-=this.getSpeedx();
		yy-=this.getSpeedy();
		if(yy<=-this.getHeight()||xx<=-this.getWidth()){
			return false;
		}
		else {
			setY(yy);
			setX(xx);
			return true;
		}
			
	}
	public boolean MoveXR(){
		int yy=this.getY();
		int xx=this.getX();
		xx+=this.getSpeedx();
		yy-=this.getSpeedy();
		if(yy<=-this.getHeight()||xx>=this.getWidth()+Conts.GAME_WEIDTH){
			return false;
		}
		else {
			setY(yy);
			setX(xx);
			return true;
		}
			
	}
	public boolean moveDown(){
		int yy=this.getY();
		yy+=this.getSpeedy();
		if(yy>=this.getHeight()+Conts.GAME_HEIGHT){
			return false;
		}
		else {
			setY(yy);
			return true;
		}
			
	}
		public Rectangle getHurtRec(){
			return new Rectangle(getX(),getY(),getWidth(),getHeight());
		}
		public int getAttact() {
			return attact;
		}
		public void setAttact(int attact) {
			this.attact = attact;
		}
		public BulletType getBt() {
			return bt;
		}
		public void setBt(BulletType bt) {
			this.bt = bt;
		}
}
