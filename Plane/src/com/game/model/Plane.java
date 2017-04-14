package com.game.model;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;

import com.game.config.Conts;
public class Plane extends GameModel{
	private int hp;
	private int maxHp,boss;
	private boolean isDeathing;
		private ArrayList<Bullet> blist = new ArrayList<Bullet>();
		private ArrayList<Bullet> blist1 = new ArrayList<Bullet>();
	//	private HashMap<String, ArrayList<Bullet>> mapblist = new HashMap<String, ArrayList<Bullet>>();
		public Rectangle getHurtRec(){
			return new Rectangle((getX()+getWidth()/4),(getHeight()/4+getY()),getWidth()/2,getHeight()/2);
			
		}
		public void moveLeft(){
			int x1=getX();
			x1-=getSpeedx();
			if(x1<0)
				x1=0;
			setX(x1);
		}
		public void moveRight(){
			int x1=getX();
			x1+=getSpeedx();
			if(x1>Conts.GAME_WEIDTH-getWidth())
				x1=Conts.GAME_WEIDTH-getWidth();
			setX(x1);
		}
		public void moveUp(){
			int y1=getY();
			y1-=getSpeedy();
			if(y1<0)
				y1=0;
			setY(y1);
		}
		public void moveDown(){
			int y1=getY();
			y1+=getSpeedy();
			if(y1>Conts.GAME_HEIGHT-getHeight())
				y1=Conts.GAME_HEIGHT-getHeight();
			setY(y1);
		}

		
		public void bs(){
			int index = getIndex();
			index++;
			
			if(index>=getMaxindex())
				setIndex(0);
			else
				setIndex(index);
		}
		public ArrayList<Bullet> getBlist() {
			return blist;
		}
		public void setBlist(ArrayList<Bullet> blist) {
			this.blist = blist;
		}
		public boolean isDeathing() {
			return isDeathing;
		}
		public void setDeathing(boolean isDeathing) {
			this.isDeathing = isDeathing;
		}
		public int getHp() {
			return hp;
		}
		public void setHp(int hp) {
			this.hp = hp;
		}
		public int getMaxHp() {
			return maxHp;
		}
		public void setMaxHp(int maxHp) {
			this.maxHp = maxHp;
		}
		public ArrayList<Bullet> getBlist1() {
			return blist1;
		}
		public void setBlist1(ArrayList<Bullet> blist1) {
			this.blist1 = blist1;
		}
		public int getBoss() {
			return boss;
		}
		public void setBoss(int boss) {
			this.boss = boss;
		}
}
