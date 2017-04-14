package com.game.model;

import com.game.config.Conts;
import com.game.config.Dir;

public class EmPlane extends Plane {
		private Dir dir;

		public Dir getDir() {
			return dir;
		}

		public void setDir(Dir dir) {
			this.dir = dir;
		}
		public boolean emMoveLeft(){
			int xx=this.getX();
			xx-=this.getSpeedx();
			if(xx<=-this.getWidth()){
				return false;
			}
			else {
				setX(xx);
				return true;
			}
				
		}
		public boolean emMoveRight(){
			int xx=this.getX();
			xx+=this.getSpeedx();
			if(xx>=Conts.GAME_WEIDTH+this.getWidth()){
				return false;
			}
			else {
				setX(xx);
				return true;
			}
				
		}
		public boolean emMoveDown(){
			int yy=this.getY();
			yy+=this.getSpeedy();
			if(yy>=Conts.GAME_HEIGHT+this.getHeight()){
				return false;
			}
			else {
				setY(yy);
				return true;
			}
				
		}
		
}
