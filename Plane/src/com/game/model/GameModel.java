package com.game.model;

public class GameModel {
	private int x,y,index,width,height,maxindex,speedx,speedy,attact,id,prop;
	private boolean isDeathing;
	private double defense;
	private String image;
	private String shadow;
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getMaxindex() {
		return maxindex;
	}

	public void setMaxindex(int maxindex) {
		this.maxindex = maxindex;
	}

	public int getSpeedx() {
		return speedx;
	}

	public void setSpeedx(int speedx) {
		this.speedx = speedx;
	}

	public int getSpeedy() {
		return speedy;
	}

	public void setSpeedy(int speedy) {
		this.speedy = speedy;
	}
	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getShadow() {
		return shadow;
	}

	public void setShadow(String shadow) {
		this.shadow = shadow;
	}

	public int getAttact() {
		return attact;
	}

	public void setAttact(int attact) {
		if(attact<=200)
		this.attact = attact;
		else 
			this.attact = 200;
	}

	public double getDefense() {
		return defense;
	}

	public void setDefense(double defense) {
		this.defense = defense;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProp() {
		return prop;
	}

	public void setProp(int prop) {
		this.prop = prop;
	}

	public boolean isDeathing() {
		return isDeathing;
	}

	public void setDeathing(boolean isDeathing) {
		this.isDeathing = isDeathing;
	}
}
