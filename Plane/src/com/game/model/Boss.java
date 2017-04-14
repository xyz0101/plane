package com.game.model;

public class Boss extends EmPlane {
		public void bossMoveLeft(){
			int xx=getX();
			xx-=getSpeedx();
			setX(xx);
		}
		public void bossMoveRight(){
			int xx=getX();
			xx+=getSpeedx();
			setX(xx);
		}
		
		
}
