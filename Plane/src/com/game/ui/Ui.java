package com.game.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.game.config.BulletType;
import com.game.config.Conts;
import com.game.config.Dir;
import com.game.config.GameState;
import com.game.config.SoundUtils;
import com.game.factory.Factory;
import com.game.model.Boss;
import com.game.model.EmPlane;
import com.game.model.Plane;
import com.game.model.Prop;

/**
 * 打飞机游戏
 * 
 * @author 周锦
 * 
 */
public class Ui extends JFrame implements Runnable {
	private Plane p;
	private Prop prop;
	private int source;
	private EmPlane ep;
	private File file = new File("./src/sucai/saves.sav");
	private BufferedReader br;
	private BufferedWriter bw;
	private HashMap<String, String> sourcemap = new HashMap<String, String>();
	private FileReader fr;
	private FileWriter fw;
	private String play = "play0.png";
	private String restart = "restart0.png";
	private String exit = "exit0.png";
	private GameState gs = GameState.BeforGame;
	private int delay = 10, BullteDelay = 0, MaxBoom = 3, ccount = 0,
			emcount = 0;
	private int lc = 1, Alc = 0, level = 1, bgspeed = 5, pcount = 0,
			cindex = 0;
	private int[] flag = { 0, 0, 0, 0, 0, 0, 0, 0 };
	private boolean ML, MR, MU, MD, isBossing;
	private MyPaint hb;
	private List<EmPlane> emlist = new ArrayList<EmPlane>();
	private List<Prop> proplist = new ArrayList<Prop>();
	private Boss boss;

	public Ui() {
		setTitle("Plane");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// setLocationRelativeTo(null);
		setLocation(500, 20);
		setSize(Conts.GAME_WEIDTH, Conts.GAME_HEIGHT);
		setUndecorated(true);
		// p = Factory.getPlane(cindex);
		Container c = getContentPane();
		hb = new MyPaint();

		c.setFocusable(true);
		c.addKeyListener(new MyKey());
		c.addMouseListener(new MyMouse());
		c.addMouseMotionListener(new MyMouse());
		c.add(hb);
		setVisible(true);
	}

	public static void main(String[] args) {
		Ui u = new Ui();
		Thread t = new Thread(u);
		SoundUtils.Play(Conts.AUDIOS.get("bgm.wav"), true);
		if (u.gs == GameState.Pause)
			t.stop();
		else
			t.start();

	}

	class MyKey extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {

			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				MU = true;
				break;
			case KeyEvent.VK_S:
				MD = true;
				break;
			case KeyEvent.VK_A:
				ML = true;
				break;
			case KeyEvent.VK_D:
				MR = true;
				break;
			case KeyEvent.VK_P:
				if (gs != GameState.BeforGame) {
					pcount++;
					System.out.println("pcount::::"+pcount);
					pcount = pcount % 2;
					if (pcount == 1)
						gs = GameState.Pause;
					else if (pcount == 0)
						gs = GameState.Gameing;
				}

				break;
			case KeyEvent.VK_SPACE:
				if (MaxBoom >= 1) {
					Factory.getBullet(p, BulletType.Boom);
					SoundUtils.Play(Conts.AUDIOS.get("big.wav"), false);
					MaxBoom--;
				}
				break;
			}

		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_W:
				MU = false;
				break;
			case KeyEvent.VK_S:
				MD = false;
				break;
			case KeyEvent.VK_A:
				ML = false;
				break;
			case KeyEvent.VK_D:
				MR = false;
				break;

			}

		}

	}

	class MyMouse extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			super.mouseClicked(e);

			System.out.println("click");
			if (gs == GameState.Pause) {
				if (new Rectangle((Conts.GAME_WEIDTH - Conts.IMAGES.get(exit)
						.getWidth()) / 2, (Conts.GAME_HEIGHT - Conts.IMAGES
						.get(exit).getHeight())
						/ 2
						+ 20
						+ Conts.IMAGES.get(exit).getHeight(), Conts.IMAGES.get(
						exit).getWidth(), Conts.IMAGES.get(exit).getHeight())
						.contains(e.getPoint())) {
					{
						System.out.println("exitsuccess");
						SoundUtils.Play(Conts.AUDIOS.get("click.wav"), false);
						if (source >= Integer.valueOf(sourcemap.get("player"
								+ p.getId()))) {
							int choice = 0;
							choice = JOptionPane.showConfirmDialog(null,
									"您已超越最高积分，是否需要保存？", "退出游戏",
									JOptionPane.YES_NO_OPTION);
							if (choice == 1)
								System.exit(0);
							else if (choice == 0) {
								if (p != null) {
									int s;
									s = Integer.valueOf(sourcemap.get("player"
											+ p.getId()));
									if (source >= s)
										sourcemap.put("player" + p.getId(),
												String.valueOf(source));
									System.out.println("pid=::" + p.getId());
									try {
										fw = new FileWriter(file);
										System.out.println("filename:::"
												+ file.getName());
										bw = new BufferedWriter(fw);
										for (int i = 0; i <= 3; i++) {
											bw.write("player" + i);
											bw.newLine();
											bw.write(sourcemap
													.get("player" + i));
											bw.newLine();
										}

										// fw.close();
										bw.close();
										System.exit(0);
									} catch (IOException e1) {
										e1.printStackTrace();
									}
								}
							}
						} else
							System.exit(0);
					}
				}
			}
			if (gs == GameState.BeforGame) {
				if (getChooseId(e) >= 0) {
					cindex = getChooseId(e);
					SoundUtils.Play(Conts.AUDIOS.get("click.wav"), false);
					p = Factory.getPlane(cindex);
					ccount++;
				}

				if (new Rectangle((Conts.GAME_WEIDTH - Conts.IMAGES.get(play)
						.getWidth()) / 2, (Conts.GAME_HEIGHT - Conts.IMAGES
						.get(play).getHeight()) / 2, Conts.IMAGES.get(play)
						.getWidth(), Conts.IMAGES.get(play).getHeight())
						.contains(e.getPoint())
						&& cindex >= 0) {
					{
						SoundUtils.Play(Conts.AUDIOS.get("click.wav"), false);
						System.out.println("clicksuccess");
						gs = GameState.Gameing;

					}
				}
				if (new Rectangle((Conts.GAME_WEIDTH - Conts.IMAGES.get(exit)
						.getWidth()) / 2, (Conts.GAME_HEIGHT - Conts.IMAGES
						.get(exit).getHeight())
						/ 2
						+ 20
						+ Conts.IMAGES.get(exit).getHeight(), Conts.IMAGES.get(
						exit).getWidth(), Conts.IMAGES.get(exit).getHeight())
						.contains(e.getPoint())) {
					{
						System.out.println("exitsuccess");
						SoundUtils.Play(Conts.AUDIOS.get("click.wav"), false);
						System.exit(0);
					}
				}

			} else if (gs == GameState.Over) {
				if (new Rectangle((Conts.GAME_WEIDTH - Conts.IMAGES
						.get(restart).getWidth()) / 2,
						(Conts.GAME_HEIGHT - Conts.IMAGES.get(restart)
								.getHeight()) / 2 + 50, Conts.IMAGES.get(
								restart).getWidth(), Conts.IMAGES.get(restart)
								.getHeight()).contains(e.getPoint())) {
					SoundUtils.Play(Conts.AUDIOS.get("click.wav"), false);
					gs = GameState.BeforGame;

				}
				if (new Rectangle((Conts.GAME_WEIDTH - Conts.IMAGES.get(exit)
						.getWidth()) / 2, (Conts.GAME_HEIGHT - Conts.IMAGES
						.get(exit).getHeight())
						/ 2
						+ 10
						+ 50
						+ Conts.IMAGES.get(exit).getHeight(), Conts.IMAGES.get(
						exit).getWidth(), Conts.IMAGES.get(exit).getHeight())
						.contains(e.getPoint())) {
					SoundUtils.Play(Conts.AUDIOS.get("click.wav"), false);
					System.exit(0);
				}
			} else if (gs == GameState.Pass) {
				if (new Rectangle((Conts.GAME_WEIDTH - Conts.IMAGES.get(exit)
						.getWidth()) / 2, (Conts.GAME_HEIGHT - Conts.IMAGES
						.get(exit).getHeight())
						/ 2
						+ 10
						+ 50
						+ Conts.IMAGES.get(exit).getHeight(), Conts.IMAGES.get(
						exit).getWidth(), Conts.IMAGES.get(exit).getHeight())
						.contains(e.getPoint())) {
					SoundUtils.Play(Conts.AUDIOS.get("click.wav"), false);
					System.exit(0);
				}
			}
			repaint();
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			System.out.println(1);
			if (gs == GameState.Pause) {
				if (new Rectangle((Conts.GAME_WEIDTH - Conts.IMAGES.get(exit)
						.getWidth()) / 2, (Conts.GAME_HEIGHT - Conts.IMAGES
						.get(exit).getHeight())
						/ 2
						+ 10
						+ Conts.IMAGES.get(exit).getHeight(), Conts.IMAGES.get(
						exit).getWidth(), Conts.IMAGES.get(exit).getHeight())
						.contains(e.getPoint())) {
					if (flag[1] == 0)
						SoundUtils.Play(Conts.AUDIOS.get("choose.wav"), false);
					flag[1] = 1;
					exit = "exit1.png";
					System.out.println("success");
				} else {
					exit = "exit0.png";
					flag[1] = 0;
				}
			}
			if (gs == GameState.BeforGame) {
				getChooseId(e);
				if (new Rectangle((Conts.GAME_WEIDTH - Conts.IMAGES.get(play)
						.getWidth()) / 2, (Conts.GAME_HEIGHT - Conts.IMAGES
						.get(play).getHeight()) / 2, Conts.IMAGES.get(play)
						.getWidth(), Conts.IMAGES.get(play).getHeight())
						.contains(e.getPoint())) {
					play = "play1.png";
					if (flag[0] == 0)
						SoundUtils.Play(Conts.AUDIOS.get("choose.wav"), false);
					flag[0] = 1;
					System.out.println("success");
				} else {
					play = "play0.png";
					flag[0] = 0;
				}
				if (new Rectangle((Conts.GAME_WEIDTH - Conts.IMAGES.get(exit)
						.getWidth()) / 2, (Conts.GAME_HEIGHT - Conts.IMAGES
						.get(exit).getHeight())
						/ 2
						+ 10
						+ Conts.IMAGES.get(exit).getHeight(), Conts.IMAGES.get(
						exit).getWidth(), Conts.IMAGES.get(exit).getHeight())
						.contains(e.getPoint())) {
					if (flag[1] == 0)
						SoundUtils.Play(Conts.AUDIOS.get("choose.wav"), false);
					flag[1] = 1;
					exit = "exit1.png";
					System.out.println("success");
				} else {
					exit = "exit0.png";
					flag[1] = 0;
				}
			} else if (gs == GameState.Over) {
				if (new Rectangle((Conts.GAME_WEIDTH - Conts.IMAGES
						.get(restart).getWidth()) / 2,
						(Conts.GAME_HEIGHT - Conts.IMAGES.get(restart)
								.getHeight()) / 2 + 50, Conts.IMAGES.get(
								restart).getWidth(), Conts.IMAGES.get(restart)
								.getHeight()).contains(e.getPoint())) {
					if (flag[2] == 0)
						SoundUtils.Play(Conts.AUDIOS.get("choose.wav"), false);
					flag[2] = 1;
					restart = "restart1.png";
					System.out.println("success");
				} else {
					restart = "restart0.png";
					flag[2] = 0;
				}
				if (new Rectangle((Conts.GAME_WEIDTH - Conts.IMAGES.get(exit)
						.getWidth()) / 2, (Conts.GAME_HEIGHT - Conts.IMAGES
						.get(exit).getHeight())
						/ 2
						+ 10
						+ 50
						+ Conts.IMAGES.get(exit).getHeight(), Conts.IMAGES.get(
						exit).getWidth(), Conts.IMAGES.get(exit).getHeight())
						.contains(e.getPoint())) {
					if (flag[3] == 0)
						SoundUtils.Play(Conts.AUDIOS.get("choose.wav"), false);
					flag[3] = 1;
					exit = "exit1.png";
					System.out.println("success");
				} else {
					exit = "exit0.png";
					flag[3] = 0;
				}
			} else if (gs == GameState.Pass) {
				if (new Rectangle((Conts.GAME_WEIDTH - Conts.IMAGES.get(exit)
						.getWidth()) / 2, (Conts.GAME_HEIGHT - Conts.IMAGES
						.get(exit).getHeight())
						/ 2
						+ 10
						+ 50
						+ Conts.IMAGES.get(exit).getHeight(), Conts.IMAGES.get(
						exit).getWidth(), Conts.IMAGES.get(exit).getHeight())
						.contains(e.getPoint())) {
					if (flag[3] == 0)
						SoundUtils.Play(Conts.AUDIOS.get("choose.wav"), false);
					flag[3] = 1;
					exit = "exit1.png";
					System.out.println("success");
				} else {
					exit = "exit0.png";
					flag[3] = 0;
				}

			}
			repaint();

		}

	}

	public int getChooseId(MouseEvent e) {
		if (gs == GameState.BeforGame && ccount % 2 == 0) {

			if (new Rectangle(10, 470, 100, 130).contains(e.getPoint())) {
				if (flag[4] == 0)
					SoundUtils.Play(Conts.AUDIOS.get("choose.wav"), false);
				flag[4] = 1;
				cindex = 0;
			} else if (new Rectangle(110, 470, 100, 130).contains(e.getPoint())) {
				if (flag[5] == 0)
					SoundUtils.Play(Conts.AUDIOS.get("choose.wav"), false);
				flag[5] = 1;
				cindex = 1;
			} else if (new Rectangle(210, 470, 100, 130).contains(e.getPoint())) {
				if (flag[6] == 0)
					SoundUtils.Play(Conts.AUDIOS.get("choose.wav"), false);
				flag[6] = 1;
				cindex = 2;
			} else if (new Rectangle(310, 470, 100, 130).contains(e.getPoint())) {
				if (flag[7] == 0)
					SoundUtils.Play(Conts.AUDIOS.get("choose.wav"), false);
				flag[7] = 1;
				cindex = 3;
			} else {
				flag[4] = 0;
				flag[5] = 0;
				flag[6] = 0;
				flag[7] = 0;
				cindex = -5;
			}

		}
		return cindex;

	}

	class MyPaint extends JPanel {

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (gs == GameState.Pass) {
				drawPass(g);

			}

			if (gs != GameState.BeforGame) {
				if (level <= 4)

					DrawBackGround(g);

				drawEmPlane(g);
				drawBoss(g);
				drawProp(g);
				drawPlane(g);

				if (!p.isDeathing()) {
					drawBullet(g);
					drawBossBullet(g);
					drawEmBullet(g);
				}
				if (p.isDeathing()) {
					// 最高记录存档
					if (p != null) {
						int s;
						s = Integer
								.valueOf(sourcemap.get("player" + p.getId()));
						if (source >= s)
							sourcemap.put("player" + p.getId(),
									String.valueOf(source));
						System.out.println("pid=::" + p.getId());
						try {
							fw = new FileWriter(file);
							System.out.println("filename:::" + file.getName());
							bw = new BufferedWriter(fw);
							for (int i = 0; i <= 3; i++) {
								bw.write("player" + i);
								bw.newLine();
								bw.write(sourcemap.get("player" + i));
								bw.newLine();
							}
							bw.close();

						} catch (IOException e) {
							e.printStackTrace();
						}
					}

					DrawOver(g);

				}

				drawTips(g);

			}
			if (gs == GameState.BeforGame) {
				drawStart(g);

				try {
					fr = new FileReader(file);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				br = new BufferedReader(fr);

				for (int i = 0; i <= 3; i++) {
					try {
						String key = br.readLine();
						String value = br.readLine();
						System.out.println(key + "==" + value);
						sourcemap.put(key, value);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				proplist.clear();
				lc = 1;
				isBossing = false;
				boss = null;
				emlist.clear();
				source = 0;
				Alc = 0;
				level = 1;
				emcount = 0;
				MaxBoom = 3;
			}
			if (gs == GameState.Pause) {
				drawPause(g);
			}

		}

		private void drawPass(Graphics g) {
			g.drawImage(Conts.IMAGES.get("bg4_5.jpg"), 0, 0, this);
			g.drawImage(Conts.IMAGES.get("pass.png"),
					(Conts.GAME_WEIDTH - Conts.IMAGES.get("pass.png")
							.getWidth()) / 2, (Conts.GAME_HEIGHT - Conts.IMAGES
							.get("pass.png").getHeight()) / 2, this);
			g.drawImage(
					Conts.IMAGES.get(exit),
					(Conts.GAME_WEIDTH - Conts.IMAGES.get(exit).getWidth()) / 2,
					(Conts.GAME_HEIGHT - Conts.IMAGES.get(exit).getHeight())
							/ 2 + Conts.IMAGES.get(exit).getHeight() + 10 + 50,
					this);

		}

		private void drawProp(Graphics g) {
			for (int i = 0; i < proplist.size(); i++) {
				Prop prop1 = proplist.get(i);

				if (prop1 != null) {

					g.drawImage(Conts.IMAGES.get(prop1.getImage()),
							prop1.getX(), prop1.getY(), prop1.getWidth(),
							prop1.getHeight(), this);

					System.out.println("index::::::::::::" + prop1.getIndex());
					g.setFont(new Font("微软雅黑", Font.BOLD, 20));
					g.setColor(Color.RED);
					if (prop1.getProp() == 0)
						g.drawString("血量加满", prop1.getX() + 45, prop1.getY());
					else if (prop1.getProp() == 1)
						g.drawString("炸弹+1", prop1.getX() + 45, prop1.getY());
					else if (prop1.getProp() == 2)
						g.drawString("消灭所有敌机", prop1.getX() + 45, prop1.getY());
					else if (prop1.getProp() == 3)
						g.drawString("攻击力+50", prop1.getX() + 45, prop1.getY());

				}
			}
		}

		private void DrawOver(Graphics g) {
			g.drawImage(
					Conts.IMAGES.get(p.getImage()).getSubimage(
							p.getIndex() * p.getWidth(), 0, p.getWidth(),
							p.getHeight()), p.getX(), p.getY(),
					p.getWidth() * 2, p.getHeight() * 2, this);
			g.drawImage(Conts.IMAGES.get("fail.png"),
					(Conts.GAME_WEIDTH - Conts.IMAGES.get("fail.png")
							.getWidth()) / 2, (Conts.GAME_HEIGHT - Conts.IMAGES
							.get("fail.png").getHeight()) / 2 - 100, this);

			g.drawImage(
					Conts.IMAGES.get(restart),
					(Conts.GAME_WEIDTH - Conts.IMAGES.get(restart).getWidth()) / 2,
					(Conts.GAME_HEIGHT - Conts.IMAGES.get(restart).getHeight()) / 2 + 50,
					this);
			g.drawImage(
					Conts.IMAGES.get(exit),
					(Conts.GAME_WEIDTH - Conts.IMAGES.get(exit).getWidth()) / 2,
					(Conts.GAME_HEIGHT - Conts.IMAGES.get(exit).getHeight())
							/ 2 + Conts.IMAGES.get(exit).getHeight() + 10 + 50,
					this);
		}

		private void drawPause(Graphics g) {
			g.drawImage(Conts.IMAGES.get("pause.png"),
					(Conts.GAME_WEIDTH - Conts.IMAGES.get("pause.png")
							.getWidth()) / 2, (Conts.GAME_HEIGHT - Conts.IMAGES
							.get("pause.png").getHeight()) / 2, this);
			g.drawImage(
					Conts.IMAGES.get(exit),
					(Conts.GAME_WEIDTH - Conts.IMAGES.get(exit).getWidth()) / 2,
					(Conts.GAME_HEIGHT - Conts.IMAGES.get(exit).getHeight())
							/ 2 + Conts.IMAGES.get(exit).getHeight() + 10, this);
		}

		private void drawStart(Graphics g) {
			g.drawImage(Conts.IMAGES.get("startbg.jpg"), 0, 0,
					Conts.GAME_WEIDTH, Conts.GAME_HEIGHT, this);
			g.drawImage(
					Conts.IMAGES.get(play),
					(Conts.GAME_WEIDTH - Conts.IMAGES.get(play).getWidth()) / 2,
					(Conts.GAME_HEIGHT - Conts.IMAGES.get(play).getHeight()) / 2,
					this);
			g.drawImage(
					Conts.IMAGES.get(exit),
					(Conts.GAME_WEIDTH - Conts.IMAGES.get(exit).getWidth()) / 2,
					(Conts.GAME_HEIGHT - Conts.IMAGES.get(exit).getHeight())
							/ 2 + Conts.IMAGES.get(exit).getHeight() + 10, this);
			g.drawImage(
					Conts.IMAGES.get("player0.png").getSubimage(0, 0,
							Conts.IMAGES.get("player0.png").getWidth() / 2,
							Conts.IMAGES.get("player0.png").getHeight()), 15,
					480, this);
			g.drawImage(Conts.IMAGES.get("pbg.png"), 10 + (cindex * 100), 470,
					this);
			g.setFont(new Font("宋体", Font.BOLD, 15));
			g.setColor(Color.red);
			g.drawString("生命值:" + 1000, 10, 615);
			g.drawString("攻击力:" + 120, 10, 630);
			g.drawString("防御值:" + 10, 10, 645);
			for (int i = 1; i <= 3; i++) {
				g.drawImage(Conts.IMAGES.get("player" + i + ".png"),
						18 + i * 100, 500, this);
				g.drawImage(Conts.IMAGES.get("pbg.png"), 10 + (cindex * 100),
						470, this);
				g.setFont(new Font("宋体", Font.BOLD, 15));
				g.setColor(Color.red);
				g.drawString("生命值:" + (1000 + i * 1000), 18 + i * 100, 615);
				g.drawString("攻击力:" + (120 - i * 20), 18 + i * 100, 630);
				g.drawString("防御值:" + (10 + i * 10), 18 + i * 100, 645);
			}
		}

		private void drawBossBullet(Graphics g) {

			if (boss != null)
				for (int i = 0; i < boss.getBlist().size(); i++) {
					if (boss.getBlist().get(i) != null) {
						g.drawImage(
								Conts.IMAGES.get(
										boss.getBlist().get(i).getImage())
										.getSubimage(
												0,
												0,
												boss.getBlist().get(i)
														.getWidth(),
												boss.getBlist().get(i)
														.getHeight()), boss
										.getBlist().get(i).getX(), boss
										.getBlist().get(i).getY(), this);
					}
				}
		}

		private void drawBoss(Graphics g) {
			if (isBossing == true && boss != null) {
				emlist.clear();
				if (!boss.isDeathing()) {
					g.drawImage(Conts.IMAGES.get(boss.getImage()), boss.getX(),
							boss.getY(), null);
					g.drawRect(boss.getX(), boss.getY() + boss.getHeight(),
							boss.getWidth(), Conts.HP_Height);
					g.setColor(Color.red);
					g.fillRect(boss.getX(), boss.getY() + boss.getHeight(),
							(int) (boss.getWidth() * (boss.getHp() * 1.0 / boss
									.getMaxHp())), Conts.HP_Height);
				} else {
					g.drawImage(
							Conts.IMAGES.get(boss.getImage()).getSubimage(
									boss.getIndex() * boss.getWidth(), 0,
									boss.getWidth(), boss.getHeight()),
							boss.getX(), boss.getY(), this);

				}

			}
		}

		private void drawTips(Graphics g) {
			g.setFont(new Font("微软雅黑", Font.BOLD, 20));
			g.drawString("炸弹剩余数量: " + MaxBoom, 50, 50);
			g.drawString("里程: " + Alc, 50, 80);
			g.setFont(new Font("微软雅黑", Font.BOLD, 14));
			g.drawString("摧毁敌机" + emcount + "架", 300, 30);
			g.drawString("当前攻击力:" + p.getAttact(), 260, 600);
			g.drawString("当前关卡:" + level, 260, 580);
			int s;
			System.out.println("qqqqqqqqqq"
					+ sourcemap.get("player" + p.getId()));
			s = Integer.valueOf(sourcemap.get("player" + p.getId()));
			if (source >= s)
				s = source;
			int hp = p.getHp();
			if (hp <= 0)
				hp = 0;
			g.drawString("当前HP:" + hp, 20, 620);
			g.drawString("当前积分:" + source, 260, 560);
			g.drawString("当前飞机最高记录:" + s, 260, 540);

		}

		private void drawEmBullet(Graphics g) {

			for (int j = 0; j < emlist.size(); j++) {
				ep = emlist.get(j);
				if (ep != null)
					for (int i = 0; i < ep.getBlist().size(); i++) {
						if (ep.getBlist().get(i) != null) {
							g.drawImage(
									Conts.IMAGES.get(ep.getBlist().get(i)
											.getImage()), ep.getBlist().get(i)
											.getX(), ep.getBlist().get(i)
											.getY(), this);
						}
					}
			}
		}

		private void drawBullet(Graphics g) {
			for (int i = 0; i < p.getBlist().size(); i++) {
				if (p.getBlist().get(i) != null) {
					int py;
					if (cindex == 0)
						py = 20;
					else
						py = -20;
					g.drawImage(
							Conts.IMAGES.get(p.getBlist().get(i).getImage()), p
									.getBlist().get(i).getX(), p.getBlist()
									.get(i).getY()
									+ py, this);
				}
			}
			for (int i = 0; i < p.getBlist1().size(); i++) {
				if (p.getBlist1().get(i) != null) {
					if (cindex > 0 && cindex < 3) {
						if (p.getBlist1().get(i).getId() == 0)
							g.drawImage(
									Conts.IMAGES.get(p.getBlist1().get(i)
											.getImage()), p.getBlist1().get(i)
											.getX() - 30, p.getBlist1().get(i)
											.getY() + 40, this);
						else
							g.drawImage(
									Conts.IMAGES.get(p.getBlist1().get(i)
											.getImage()), p.getBlist1().get(i)
											.getX() + 30, p.getBlist1().get(i)
											.getY() + 40, this);
					} else if (cindex == 0 || cindex == 3) {
						int py;
						if (cindex == 0)
							py = 80;
						else
							py = -40;
						g.drawImage(
								Conts.IMAGES.get(p.getBlist1().get(i)
										.getImage()), p.getBlist1().get(i)
										.getX() - 30, p.getBlist1().get(i)
										.getY()
										+ py, this);

						g.drawImage(
								Conts.IMAGES.get(p.getBlist1().get(i)
										.getImage()), p.getBlist1().get(i)
										.getX() + 30, p.getBlist1().get(i)
										.getY()
										+ py, this);
					}

				}

			}

		}

		private void drawEmPlane(Graphics g) {
			for (int i = 0; i < emlist.size(); i++) {
				EmPlane ep = emlist.get(i);
				if (ep != null) {
					if (ep.isDeathing()) {
						System.out.println(ep.getX() + "----" + ep.getY());
						try {
							g.drawImage(
									Conts.IMAGES.get(ep.getImage())
											.getSubimage(
													ep.getIndex()
															* ep.getWidth(), 0,
													ep.getWidth(),
													ep.getHeight()), ep.getX(),
									ep.getY(), this);
						} catch (java.awt.image.RasterFormatException e) {

						}

					} else {
						g.drawImage(Conts.IMAGES.get(ep.getImage()), ep.getX(),
								ep.getY(), this);
						g.drawRect(ep.getX(), ep.getY() + ep.getHeight(),
								ep.getWidth(), Conts.HP_Height);
						g.setColor(Color.red);
						g.fillRect(ep.getX(), ep.getY() + ep.getHeight(),
								(int) (ep.getWidth() * (ep.getHp() * 1.0 / ep
										.getMaxHp())), Conts.HP_Height);
					}

				}
			}

		}

		private void drawPlane(Graphics g) {
			System.out.println(cindex + "    飞机编号:::" + p.getImage());
			// if(p.isDeathing()==false&&gs!=GameState.Over)
			g.drawImage(
					Conts.IMAGES.get(p.getImage()).getSubimage(
							p.getIndex() * p.getWidth(), 0, p.getWidth(),
							p.getHeight()), p.getX(), p.getY(), this);
			g.drawRect(0, Conts.GAME_HEIGHT - Conts.HP_Height,
					Conts.GAME_WEIDTH, Conts.HP_Height);
			g.setColor(Color.red);
			g.fillRect(
					0,
					Conts.GAME_HEIGHT - Conts.HP_Height,
					(int) (Conts.GAME_WEIDTH * (p.getHp() * 1.0 / p.getMaxHp())),
					Conts.HP_Height);
			if (!p.isDeathing())
				g.drawImage(Conts.IMAGES.get(p.getShadow()), p.getX() + 20,
						p.getY() + 20, this);

		}

		private void DrawBackGround(Graphics g) {
			int upindex = lc / Conts.GAME_HEIGHT + 2;
			int downindex = lc / Conts.GAME_HEIGHT + 1;
			if (upindex >= 6)
				upindex = 1;
			g.drawImage(
					Conts.IMAGES.get("bg" + level + "_" + downindex + ".jpg"),
					0, lc % Conts.GAME_HEIGHT, this);
			g.drawImage(
					Conts.IMAGES.get("bg" + level + "_" + upindex + ".jpg")
							.getSubimage(0,
									Conts.GAME_HEIGHT - lc % Conts.GAME_HEIGHT,
									Conts.GAME_WEIDTH, lc % Conts.GAME_HEIGHT),
					0, 0, this);

		}

	}

	@Override
	public void run() {

		// TODO Auto-generated method stub
		while (true) {
			if (emlist.size() == Conts.EMMAX)
				emlist.clear();
			// p.bs();
			if (gs == GameState.Gameing) {
				if (p != null && p.isDeathing()) {
					int index = p.getIndex();
					index++;
					if (index < p.getMaxindex()) {
						p.setIndex(index);
					} else {
						gs = GameState.Over;
						SoundUtils.Play(Conts.AUDIOS.get("over.wav"), false);
					}

				}

				if (level == 5) {
					gs = GameState.Pass;
					SoundUtils.Play(Conts.AUDIOS.get("pass.wav"), false);
				}
				System.out.println("move");
				if (gs != GameState.Pass) {

					SoundUtils.Play(Conts.AUDIOS.get("bullet.wav"), false);
					crateEmPlane();
					System.out.println("敌机数目：：：" + emlist.size());
					crateBoss();
					crateBullet();
					bossBulletMove();
					emPlaneMove();
					bossMove();
					propEat();
					playerMove();
					bulletMove();
					bullet1Move();
					emBulletMove();
					moveLc();

				}
			}
			try {
				Thread.sleep(100);
				delay++;
				BullteDelay++;
				// EmBullteDelay++;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			hb.repaint();

		}

	}

	private void propEat() {

		for (int i = 0; i < proplist.size(); i++) {
			Prop prop = proplist.get(i);
			System.out.println("check!!!!!!!!!");
			if (prop != null && p.getHurtRec().intersects(prop.getHurtRec())) {
				System.out.println("碰撞！！！！！！！");
				SoundUtils.Play(Conts.AUDIOS.get("prop.wav"), false);
				checkProp(prop);	
				proplist.set(i, null);
				
			}
		}
	}



	private void bossMove() {
		System.out.println(boss);
		if (isBossing == true && boss != null) {
			if (boss.isDeathing()) {
				int index = boss.getIndex();
				index++;
				if (index < boss.getMaxindex()) {
					boss.setIndex(index);
				} else {
					setBossDeath();
				}

			} else {
				if (boss.getDir() == Dir.Left) {
					boss.bossMoveLeft();
					if (!boss.isDeathing()
							&& boss.getHurtRec().intersects(p.getHurtRec())) {
						int hp = boss.getHp();
						hp -= 2000;
						boss.setHp(hp);
						setPlaneDeath(p);
					}
					if (boss.getX() <= 0)
						boss.setDir(Dir.Right);
				}
				if (boss.getDir() == Dir.Right) {
					boss.bossMoveRight();
					if (!boss.isDeathing()
							&& boss.getHurtRec().intersects(p.getHurtRec())) {
						int hp = boss.getHp();
						hp -= 2000;
						boss.setHp(hp);
						setPlaneDeath(p);
					}
					if (boss.getX() >= Conts.GAME_WEIDTH - boss.getWidth())
						boss.setDir(Dir.Left);
				}
				Factory.getEmBullet(boss);
			}

		}
	}

	private void crateBoss() {
		System.out.println(level);
		if (isBossing == true && boss == null) {
			boss = Factory.getBoss(level);
		}

	}

	private void moveLc() {
		lc += bgspeed;
		if (isBossing == false) {
			Alc += 10;
		}

		if (lc >= 4 * Conts.GAME_HEIGHT - 20) {
			bgspeed = 0;
			isBossing = true;
		}

	}

	private void bossBulletMove() {
		if (boss != null) {
			for (int i = 0; i < boss.getBlist().size(); i++) {
				if (boss.getBlist().get(i) != null) {
					if (boss.getBlist().get(i).moveDown() == false) {
						boss.getBlist().set(i, null);
					} else {
						if (p != null)
							if (p.isDeathing() == false
									&& boss.getBlist().get(i).getHurtRec()
											.intersects(p.getHurtRec())) {
								int hp = p.getHp();
								hp -= boss.getBlist().get(i).getAttact()
										* p.getDefense();
								if (hp <= 1)
									setPlaneDeath(p);
								else
									p.setHp(hp);

								boss.getBlist().set(i, null);
							}
					}
				}
			}
		}

	}

	private void emBulletMove() {
		for (int j = 0; j < emlist.size(); j++) {
			ep = emlist.get(j);
			if (ep != null) {
				for (int i = 0; i < ep.getBlist().size(); i++) {
					if (ep.getBlist().get(i) != null) {
						if (ep.getBlist().get(i).moveDown() == false) {
							ep.getBlist().set(i, null);
						} else {
							if (p != null)
								if (p.isDeathing() == false
										&& ep.getBlist().get(i).getHurtRec()
												.intersects(p.getHurtRec())) {
									int hp = p.getHp();
									hp -= ep.getBlist().get(i).getAttact()
											* p.getDefense();
									if (hp <= 1)
										setPlaneDeath(p);

									else
										p.setHp(hp);

									ep.getBlist().set(i, null);
								}
						}
					}
				}
			}
		}

	}

	private void bulletMove() {
		for (int i = 0; i < p.getBlist().size(); i++) {
			if (p.getBlist().get(i) != null) {
				boolean move = false;
				move = p.getBlist().get(i).Move();
				if (move == false) {
					p.getBlist().set(i, null);
				} else {
					// if (isBossing == false) {
					for (int j = 0; j < emlist.size(); j++) {
						ep = emlist.get(j);
						if (ep != null) {
							if (p.getBlist().get(i) != null)
								if (ep.isDeathing() == false
										&& p.getBlist()
												.get(i)
												.getHurtRec()
												.intersects(
														emlist.get(j)
																.getHurtRec())) {
									int hp = ep.getHp();
									hp -= p.getBlist().get(i).getAttact();
									if (hp <= 0) {
										if (ep.getDir() == Dir.Left) {
											source += 50;
										} else if (ep.getDir() == Dir.Right) {
											source += 30;
										} else if (ep.getDir() == Dir.Down) {
											source += 10;
										}
										setEmDeath(ep);
										emcount++;
									} else
										ep.setHp(hp);
									if (p.getBlist().get(i).getBt() == BulletType.Normal)
										p.getBlist().set(i, null);
								}
						}
					}
					// } else
					{
						if (boss != null) {
							if (p.getBlist().get(i) != null)
								if (boss.isDeathing() == false
										&& p.getBlist().get(i).getHurtRec()
												.intersects(boss.getHurtRec())) {
									int hp = boss.getHp();
									hp -= p.getBlist().get(i).getAttact();
									if (hp <= 0) {
										source += 100 * level;
										setEmDeath(boss);
										emcount++;
									}

									else
										boss.setHp(hp);
									if (p.getBlist().get(i).getBt() == BulletType.Normal)
										p.getBlist().set(i, null);
								}
						}
					}
				}
			}
		}
	}

	private void setBossDeath() {
		isBossing = false;
		level++;
		lc = 1;
		bgspeed = 5;

		int k = 0;
		for (int i = 0; i < proplist.size(); i++) {
			if (proplist.get(i) == null && k < 3) {
				int r = Conts.RAN.nextInt(3);
				proplist.set(i, new Prop(boss.getX() + Conts.RAN.nextInt(50)
						+ 50, boss.getY() + Conts.RAN.nextInt(80) + 80, 40, 40,
						r, Factory.getProp(r).getImage()));
			}
		}
		boss = null;
		emlist.clear();
	}

	private void bullet1Move() {
		for (int i = 0; i < p.getBlist1().size(); i++) {
			boolean d = false;
			if (p.getBlist1().get(i) != null) {
				if (cindex == 0 || cindex == 3) {
					if (cindex == 0)
						p.getBlist1().get(i).setSpeedy(60);
					else {
						p.getBlist1().get(i).setSpeedy(20);
					}

					d = p.getBlist1().get(i).Move();
				}

				if (cindex == 1) {
					if (p.getBlist1().get(i).getId() == 0)
						d = p.getBlist1().get(i).MoveXL();
					else
						d = p.getBlist1().get(i).MoveXR();
				} else if (cindex == 2) {
					if (p.getBlist1().get(i).getId() == 0)
						d = p.getBlist1().get(i).MoveXR();
					else
						d = p.getBlist1().get(i).MoveXL();
				}

				if (d == false) {
					p.getBlist1().set(i, null);
				} else {
					// if (isBossing == false) {
					for (int j = 0; j < emlist.size(); j++) {
						ep = emlist.get(j);
						if (ep != null) {
							if (p.getBlist1().get(i) != null)
								if (ep.isDeathing() == false
										&& p.getBlist1()
												.get(i)
												.getHurtRec()
												.intersects(
														emlist.get(j)
																.getHurtRec())) {
									int hp = ep.getHp();
									hp -= p.getBlist1().get(i).getAttact();
									if (hp <= 0) {
										if (ep.getDir() == Dir.Left) {
											source += 50;
										} else if (ep.getDir() == Dir.Right) {
											source += 30;
										} else if (ep.getDir() == Dir.Down) {
											source += 10;
										}

										emcount++;
										setEmDeath(ep);
									} else
										ep.setHp(hp);
									if (p.getBlist1().get(i).getBt() == BulletType.Normal1)
										p.getBlist1().set(i, null);
								}
						}
					}
					// } else
					{
						if (boss != null) {
							if (p.getBlist1().get(i) != null)
								if (boss.isDeathing() == false
										&& p.getBlist1().get(i).getHurtRec()
												.intersects(boss.getHurtRec())) {
									int hp = boss.getHp();
									hp -= p.getBlist1().get(i).getAttact();
									if (hp <= 0) {
										source += 100 * level;
										setEmDeath(boss);
										emcount++;
									}

									else
										boss.setHp(hp);
									if (p.getBlist1().get(i).getBt() == BulletType.Normal1)
										p.getBlist1().set(i, null);
								}
						}
					}
				}
			}
		}
	}

	private void setPlaneDeath(Plane p2) {
		// gs=GameState.Over;
		p2.setDeathing(true);
		SoundUtils.Play(Conts.AUDIOS.get("boom.wav"), false);
		// p.getBlist().set(i, null);
		p2.setImage("boom.png");
		p2.setIndex(0);
		p2.setMaxindex(7);

		p2.setWidth(Conts.IMAGES.get(p2.getImage()).getWidth()
				/ p2.getMaxindex());
		p2.setHeight(Conts.IMAGES.get(p2.getImage()).getHeight());
	}

	private void setEmDeath(EmPlane ep2) {
		ep2.setDeathing(true);
		SoundUtils.Play(Conts.AUDIOS.get("boom.wav"), false);
		// p.getBlist().set(i, null);
		if (ep2 == boss)
			ep2.setImage("boom01.png");

		else {
			ep2.setImage("boom.png");
			if (ep2.getProp() >= 0 && ep2.getProp() < 4) {
				prop = Factory.getProp(ep2.getProp());
				if (ep2.getX() < 40)
					prop.setX(Conts.RAN.nextInt(300) + 80);
				else if (ep2.getX() > 400)
					prop.setX(Conts.RAN.nextInt(300) + 80);
				else
					prop.setX(ep2.getX());
				if (ep2.getY() < 40)
					prop.setY(Conts.RAN.nextInt(500) + 100);
				else if (ep2.getY() > 620)
					prop.setY(Conts.RAN.nextInt(500) + 100);
				else
					prop.setY(ep2.getY());
				prop.setWidth(40);
				prop.setHeight(40);

				prop.setProp(ep2.getProp());
				if (proplist.size() < 5) {
					proplist.add(prop);
				} else {
					for (int i = 0; i < proplist.size(); i++) {
						if (proplist.get(i) == null) {
							proplist.set(i, prop);
							break;
						}
					}
				}
			}
		}

		ep2.setIndex(0);
		ep2.setMaxindex(7);

		ep2.setWidth(Conts.IMAGES.get(ep2.getImage()).getWidth()
				/ ep2.getMaxindex());
		ep2.setHeight(Conts.IMAGES.get(ep2.getImage()).getHeight());
	}

	private void crateBullet() {
		if (BullteDelay > 1) {
			Factory.getBullet(p, BulletType.Normal1);
			if (p.getId() != 3)
				Factory.getBullet(p, BulletType.Normal);
			else if (p.getId() == 3 && p.getHp() <= 2000)
				Factory.getBullet(p, BulletType.Normal);
			BullteDelay = 0;

		}

	}

	private void emPlaneMove() {

		for (int i = 0; i < emlist.size(); i++) {
			EmPlane ep = emlist.get(i);
			if (ep != null) {
				if (ep.isDeathing()) {
					int index = ep.getIndex();
					index++;
					if (index < ep.getMaxindex()) {
						ep.setIndex(index);
					} else
						emlist.set(i, null);

				} else {
					if (ep.getDir() == Dir.Down) {
						if (ep.emMoveDown() == false) {
							emlist.set(i, null);
						} else if (!ep.isDeathing()
								&& ep.getHurtRec().intersects(p.getHurtRec())) {
							setEmDeath(ep);
							int hp = p.getHp();
							hp -= 500;
							p.setHp(hp);
						}

						else
							Factory.getEmBullet(ep);
					} else if (ep.getDir() == Dir.Left) {
						if (ep.emMoveLeft() == false)
							emlist.set(i, null);
						else if (!ep.isDeathing()
								&& ep.getHurtRec().intersects(p.getHurtRec())) {
							setEmDeath(ep);
							int hp = p.getHp();
							hp -= 500;
							p.setHp(hp);
						}

						// else
						// Factory.getEmBullet(ep);
						// 子弹未添加

					}

					else {
						if (ep.emMoveRight() == false)
							emlist.set(i, null);
						else if (!ep.isDeathing()
								&& ep.getHurtRec().intersects(p.getHurtRec())) {
							setEmDeath(ep);
							int hp = p.getHp();
							hp -= 500;
							p.setHp(hp);
						}

						else
							Factory.getEmBullet(ep);
						// 子弹未添加
					}
				}
			}
		}

	}

	private void checkProp(Prop p2) {
		if (p2.getProp() == 0) {
			p.setHp(p.getMaxHp());
		} else if (p2.getProp() == 1) {
			MaxBoom++;
		} else if (p2.getProp() == 2) {
			for (int i = 0; i < emlist.size(); i++) {
				if (emlist.get(i) != null)
					setEmDeath(emlist.get(i));
			}
		} else if (p2.getProp() == 3) {
			if (p.getAttact() <= 200)
				p.setAttact(p.getAttact() + 50);
		}
	}

	private void crateEmPlane() {
		if (isBossing == false) {
			if (emlist.size() < Conts.EMMAX) {
				if (delay >= 5) {
					System.out.println("new");
					emlist.add(Factory.getEmPlane());
					// Collections.shuffle(emlist);

					delay = 0;
				}

			} else {
				for (int i = 0; i < emlist.size(); i++) {
					if (emlist.get(i) == null) {
						System.out.println("new");
						emlist.set(i, Factory.getEmPlane());
						// Collections.shuffle(emlist);
						// break;
					}
				}
			}
		}

	}

	private void playerMove() {
		if (p != null && !p.isDeathing()) {

			if (ML)
				p.moveLeft();
			if (MR)
				p.moveRight();
			if (MU)
				p.moveUp();
			if (MD)
				p.moveDown();

			// TODO Auto-generated method stub

		}
	}

}
