package com.game.factory;

import com.game.config.BulletType;
import com.game.config.Conts;
import com.game.config.Dir;
import com.game.model.Boss;
import com.game.model.Bullet;
import com.game.model.EmPlane;
import com.game.model.Plane;
import com.game.model.Prop;

public class Factory {
	public static Plane getPlane(int pindex){
		Plane p = new Plane();
		p.setImage("player"+pindex+".png");
		p.setShadow("shadow"+pindex+".png");
		if(pindex==0){
			p.setId(pindex);
			p.setDefense(0.9);
			p.setMaxindex(2);
			p.setSpeedx(30);
			p.setSpeedy(30);
			p.setMaxHp(1000);	
			p.setAttact(120);
		}
		else if(pindex==1){
			p.setId(pindex);
			p.setDefense(0.8);
			p.setAttact(100);
			p.setMaxindex(1);
			p.setSpeedx(30);
			p.setSpeedy(20);
			p.setMaxHp(2000);	
		}
		else if(pindex==2){
			p.setId(pindex);
			p.setDefense(0.7);
			p.setAttact(80);
			p.setMaxindex(1);
			p.setSpeedx(20);
			p.setSpeedy(20);
			p.setMaxHp(3000);	
		}
		else if(pindex==3){
			p.setId(pindex);
			p.setDefense(0.6);
			p.setAttact(60);
			p.setMaxindex(1);
			p.setSpeedx(10);
			p.setSpeedy(20);
			p.setMaxHp(4000);	
		}
		p.setHp(p.getMaxHp());
		p.setBoss(-1);
		p.setHeight(Conts.IMAGES.get(p.getImage()).getHeight());
		p.setWidth(Conts.IMAGES.get(p.getImage()).getWidth()/p.getMaxindex());	
		p.setX((Conts.GAME_WEIDTH-p.getWidth())/2);
		p.setY(Conts.GAME_HEIGHT-p.getHeight()-50);
		return p;
	}
		public static void getBullet(Plane p,BulletType bt){
			if(p.getId()==0){
				if(bt==BulletType.Normal){
					Bullet b = new Bullet();
					b.setImage("bullet2.png");
					b.setAttact(p.getAttact());
					b.setBt(BulletType.Normal);
					b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
					b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
					b.setY(p.getY());
					b.setX(p.getX()+(p.getWidth()-b.getWidth())/2);
					b.setSpeedy(40);
					if(p.getBlist().size()<Conts.PLAYER_BULLTE_MAX){
						p.getBlist().add(b);
					}else{
						for (int i = 0; i < p.getBlist().size(); i++) {
						if(p.getBlist().get(i)==null){
							p.getBlist().set(i, b);
							break;
						}
					}
				}	
					}else if(bt==BulletType.Normal1){
						Bullet b = new Bullet();
						
						b.setImage("bullet03.png");
						b.setAttact(50);
						b.setBt(BulletType.Normal1);
						b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
						b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
						b.setY(p.getY());
						b.setX(p.getX()+(p.getWidth()-b.getWidth())/2);
						b.setSpeedy(10);
						b.setSpeedx(2);
						if(p.getBlist1().size()<Conts.PLAYER_BULLTE_X_MAX){
							p.getBlist1().add(b);
						}else{
							for (int i = 0; i < p.getBlist1().size(); i++) {
							if(p.getBlist1().get(i)==null){
								p.getBlist1().set(i, b);
								break;
							}
						}
					}
					}
					else{
						Bullet b = new Bullet();
						b.setImage("zhadan1.png");
						b.setAttact(500);
						b.setBt(BulletType.Boom);
						b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
						b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
						b.setY(p.getY());
						b.setX(p.getX()+(p.getWidth()-b.getWidth())/2);
						b.setSpeedy(40);
						if(p.getBlist().size()<Conts.PLAYER_BULLTE_MAX){
							p.getBlist().add(b);
						}else{
							for (int i = 0; i < p.getBlist().size(); i++) {
							if(p.getBlist().get(i)==null){
								p.getBlist().set(i, b);
								break;
							}
						}
					}
					}
			}else if(p.getId()==1){
				if(bt==BulletType.Normal){
					Bullet b = new Bullet();
					b.setImage("m1.png");
					b.setAttact(p.getAttact());
					b.setBt(BulletType.Normal);
					b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
					b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
					b.setY(p.getY());
					b.setX(p.getX()+(p.getWidth()-b.getWidth())/2);
					b.setSpeedy(60);
					if(p.getBlist().size()<Conts.PLAYER_BULLTE_MAX){
						p.getBlist().add(b);
					}else{
						for (int i = 0; i < p.getBlist().size(); i++) {
						if(p.getBlist().get(i)==null){
							p.getBlist().set(i, b);
							break;
						}
					}
				}	
					}
				else if(bt==BulletType.Normal1){
					Bullet b = new Bullet();
					int id =Conts.RAN.nextInt(100);
					if(id<50)
						b.setId(0);
					else 
						b.setId(1);
					b.setImage("m9.png");
					b.setAttact(50);
					b.setBt(BulletType.Normal1);
					b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
					b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
					b.setY(p.getY());
					b.setX(p.getX()+(p.getWidth()-b.getWidth())/2);
					b.setSpeedy(10);
					b.setSpeedx(2);
					if(p.getBlist1().size()<Conts.PLAYER_BULLTE_X_MAX){
						p.getBlist1().add(b);
					}else{
						for (int i = 0; i < p.getBlist1().size(); i++) {
						if(p.getBlist1().get(i)==null){
							p.getBlist1().set(i, b);
							break;
						}
					}
				}
				}
				else{
					Bullet b = new Bullet();
					b.setImage("m10.png");
					b.setAttact(500);
					b.setBt(BulletType.Boom);
					b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
					b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
					b.setY(p.getY());
					b.setX(p.getX()+(p.getWidth()-b.getWidth())/2);
					b.setSpeedy(25);
					if(p.getBlist().size()<Conts.PLAYER_BULLTE_MAX){
						p.getBlist().add(b);
					}else{
						for (int i = 0; i < p.getBlist().size(); i++) {
						if(p.getBlist().get(i)==null){
							p.getBlist().set(i, b);
							break;
						}
					}
				}
				}
			}
			else if(p.getId()==2){
				if(bt==BulletType.Normal){
					Bullet b = new Bullet();
					b.setImage("m8.png");
					b.setAttact(p.getAttact());
					b.setBt(BulletType.Normal);
					b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
					b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
					b.setY(p.getY());
					b.setX(p.getX()+(p.getWidth()-b.getWidth())/2);
					b.setSpeedy(60);
					
					if(p.getBlist().size()<Conts.PLAYER_BULLTE_MAX){
						p.getBlist().add(b);
					}else{
						for (int i = 0; i < p.getBlist().size(); i++) {
						if(p.getBlist().get(i)==null){
							p.getBlist().set(i, b);
							break;
						}
					}
				}	
					} else
				if(bt==BulletType.Normal1){
					Bullet b = new Bullet();
					int id =Conts.RAN.nextInt(100);
					if(id<50)
						b.setId(0);
					else 
						b.setId(1);
					b.setImage("m7.png");
					b.setAttact(40);
					b.setBt(BulletType.Normal1);
					b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
					b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
					b.setY(p.getY());
					b.setX(p.getX()+(p.getWidth()-b.getWidth())/2);
					b.setSpeedy(10);
					b.setSpeedx(5);
					if(p.getBlist1().size()<Conts.PLAYER_BULLTE_MAX){
						p.getBlist1().add(b);
					}else{
						for (int i = 0; i < p.getBlist1().size(); i++) {
						if(p.getBlist1().get(i)==null){
							p.getBlist1().set(i, b);
							break;
						}
					}
				}	
					}
				else{
					Bullet b = new Bullet();
					b.setImage("m11.png");
					b.setAttact(300);
					b.setBt(BulletType.Boom);
					b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
					b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
					b.setY(p.getY());
					b.setX(p.getX()+(p.getWidth()-b.getWidth())/2);
					b.setSpeedy(15);
					if(p.getBlist().size()<Conts.PLAYER_BULLTE_MAX){
						p.getBlist().add(b);
					}else{
						for (int i = 0; i < p.getBlist().size(); i++) {
						if(p.getBlist().get(i)==null){
							p.getBlist().set(i, b);
							break;
						}
					}
				}
				}
			}else 
				if(p.getId()==3){
					if(bt==BulletType.Normal){
						Bullet b = new Bullet();
						b.setImage("m12.png");
						b.setAttact(p.getAttact());
						b.setBt(BulletType.Normal);
						b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
						b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
						b.setY(p.getY());
						b.setX(p.getX()+(p.getWidth()-b.getWidth())/2);
						b.setSpeedy(40);
						if(p.getBlist().size()<Conts.PLAYER_BULLTE_MAX){
							p.getBlist().add(b);
						}else{
							for (int i = 0; i < p.getBlist().size(); i++) {
							if(p.getBlist().get(i)==null){
								p.getBlist().set(i, b);
								break;
							}
						}
					}	
						}else if(bt==BulletType.Normal1){
							Bullet b = new Bullet();
							
							b.setImage("m13.png");
							b.setAttact(30);
							b.setBt(BulletType.Normal1);
							b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
							b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
							b.setY(p.getY());
							b.setX(p.getX()+(p.getWidth()-b.getWidth())/2);
							b.setSpeedy(10);
							b.setSpeedx(2);
							if(p.getBlist1().size()<Conts.PLAYER_BULLTE_MAX){
								p.getBlist1().add(b);
							}else{
								for (int i = 0; i < p.getBlist1().size(); i++) {
								if(p.getBlist1().get(i)==null){
									p.getBlist1().set(i, b);
									break;
								}
							}
						}
						}
						else{
							Bullet b = new Bullet();
							b.setImage("m111.png");
							b.setAttact(250);
							b.setBt(BulletType.Boom);
							b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
							b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
							b.setY(p.getY());
							b.setX(p.getX()+(p.getWidth()-b.getWidth())/2);
							b.setSpeedy(10);
							if(p.getBlist().size()<Conts.PLAYER_BULLTE_MAX){
								p.getBlist().add(b);
							}else{
								for (int i = 0; i < p.getBlist().size(); i++) {
								if(p.getBlist().get(i)==null){
									p.getBlist().set(i, b);
									break;
								}
							}
						}
						}
					}		
	}
		public static void getEmBullet(EmPlane ep){
			if(ep.getDir()==Dir.Down){
				if(Conts.RAN.nextInt(100)<5){
					Bullet b = new Bullet();
			b.setImage("bullet00.png");
			b.setAttact(50);
			b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
			b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
			b.setY(ep.getY());
			b.setX(ep.getX()+(ep.getWidth()-b.getWidth())/2);
			b.setSpeedy(20);
				ep.getBlist().add(b);
				}
				
			}
			else if(ep.getDir()==Dir.Right){
				if(Conts.RAN.nextInt(100)<10){
					Bullet b = new Bullet();
			b.setImage("bullet3.png");
			b.setAttact(80);
			b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
			b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
			b.setY(ep.getY());
			b.setX(ep.getX()+(ep.getWidth()-b.getWidth())/2);
			b.setSpeedy(15);
				ep.getBlist().add(b);
				}
				
			}
			else if(ep.getBoss()==1){
				Bullet b = new Bullet();
				if(Conts.RAN.nextInt(100)<10){			
			b.setImage("bossb.png");
			b.setAttact(500);
				}else if(Conts.RAN.nextInt(100)<40){			
			b.setImage("m9.png");
			b.setAttact(20);
				}
				if(b.getImage()!=null){
					
			b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
			b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
			b.setY(ep.getY()+ep.getHeight()-10);
			b.setX(ep.getX()+(ep.getWidth()-b.getWidth())/2);
			b.setSpeedy(40);
				ep.getBlist().add(b);
			
				}
			}
			/**
			 * 
			 ÐèÒªÐÞ¸Ä
			 * 
			 */
			else if(ep.getBoss()==2){
				Bullet b = new Bullet();
				if(Conts.RAN.nextInt(100)<10){			
			b.setImage("bossb.png");
			b.setAttact(500);
				}else if(Conts.RAN.nextInt(100)<40){			
			b.setImage("m9.png");
			b.setAttact(20);
				}
				if(b.getImage()!=null){
					
			b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
			b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
			b.setY(ep.getY()+ep.getHeight()-10);
			b.setX(ep.getX()+(ep.getWidth()-b.getWidth())/2);
			b.setSpeedy(40);
				ep.getBlist().add(b);
			
				}
			}
			else if(ep.getBoss()==3){
				Bullet b = new Bullet();
				if(Conts.RAN.nextInt(100)<10){			
			b.setImage("bossb.png");
			b.setAttact(500);
				}else if(Conts.RAN.nextInt(100)<40){			
			b.setImage("m9.png");
			b.setAttact(20);
				}
				if(b.getImage()!=null){
					
			b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
			b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
			b.setY(ep.getY()+ep.getHeight()-10);
			b.setX(ep.getX()+(ep.getWidth()-b.getWidth())/2);
			b.setSpeedy(40);
				ep.getBlist().add(b);
			
				}
			}
			else if(ep.getBoss()==4){
				Bullet b = new Bullet();
				if(Conts.RAN.nextInt(100)<10){			
			b.setImage("bossb.png");
			b.setAttact(500);
				}else if(Conts.RAN.nextInt(100)<40){			
			b.setImage("m9.png");
			b.setAttact(20);
				}
				if(b.getImage()!=null){
					
			b.setHeight(Conts.IMAGES.get(b.getImage()).getHeight());
			b.setWidth(Conts.IMAGES.get(b.getImage()).getWidth());	
			b.setY(ep.getY()+ep.getHeight()-10);
			b.setX(ep.getX()+(ep.getWidth()-b.getWidth())/2);
			b.setSpeedy(40);
				ep.getBlist().add(b);
			
				}
			}
	
	}
		public static Prop getProp(int index){
			Prop p = new Prop();
			if(index==0){
				p.setImage("hp.png");
			}else 
				if(index==1){
					p.setImage("att.png");
				}
				else 
					if(index==2){
						p.setImage("alldeath.png");
					}else 
						if(index==3){
							p.setImage("protect.png");
						}
			return p;
				
		}
	public static Boss getBoss(int level){
		Boss boss = new Boss();
		boss.setBoss(level);
		boss.setImage("boss"+level+".png");	
		boss.setHeight(Conts.IMAGES.get(boss.getImage()).getHeight());
		boss.setWidth(Conts.IMAGES.get(boss.getImage()).getWidth());
		boss.setDir(Dir.Left);
		boss.setMaxHp(10000*level);
		boss.setHp(10000*level);
		boss.setX((Conts.GAME_WEIDTH-boss.getWidth())/2);
		boss.setY(0);
		boss.setSpeedx(5);
		return boss;
		
	}
	
	public static EmPlane getEmPlane(){
		EmPlane ep = new EmPlane();
		if(Conts.RAN.nextInt(100)<80){
			if(Conts.RAN.nextInt(100)<5)
				ep.setProp(Conts.RAN.nextInt(4) );
			else
				ep.setProp(7 );
		ep.setImage("a2-1.png");
		ep.setDir(Dir.Down);
		ep.setSpeedy(10);
		ep.setBoss(0);
		ep.setMaxHp(200);
		ep.setHp(ep.getMaxHp());
		ep.setHeight(Conts.IMAGES.get(ep.getImage()).getHeight());
		ep.setWidth(Conts.IMAGES.get(ep.getImage()).getWidth());	
		ep.setX(Conts.RAN.nextInt(Conts.GAME_WEIDTH-ep.getWidth()));
		ep.setY(-ep.getHeight());
		}else if(Conts.RAN.nextInt(100)<15){
			if(Conts.RAN.nextInt(100)<8)
				ep.setProp(Conts.RAN.nextInt(4) );
			else
				ep.setProp(7);
			ep.setBoss(0);
			ep.setDir(Dir.Right);
			ep.setImage("r.png");
			ep.setMaxHp(400);
			ep.setHp(ep.getMaxHp());
			ep.setSpeedx(6);
			ep.setHeight(Conts.IMAGES.get(ep.getImage()).getHeight());
			ep.setWidth(Conts.IMAGES.get(ep.getImage()).getWidth());	
			ep.setX(-ep.getWidth());
			ep.setY(Conts.RAN.nextInt(Conts.GAME_WEIDTH/2-ep.getHeight()));
		}else if(Conts.RAN.nextInt(100)<20){
			if(Conts.RAN.nextInt(100)<5)
				ep.setProp(Conts.RAN.nextInt(4) );
			else
				ep.setProp(7 );
			ep.setBoss(0);
			ep.setDir(Dir.Left);
			ep.setImage("l.png");
			ep.setSpeedx(45);
			ep.setMaxHp(200);
			ep.setHp(ep.getMaxHp());
			ep.setHeight(Conts.IMAGES.get(ep.getImage()).getHeight());
			ep.setWidth(Conts.IMAGES.get(ep.getImage()).getWidth());	
			ep.setX(Conts.GAME_WEIDTH);
			ep.setY(Conts.RAN.nextInt(Conts.GAME_WEIDTH/2-ep.getHeight()));
		}
		
		return ep;
	}
}
