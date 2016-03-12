package com.stickhero.ui;

import com.stickhero.action.*;
import com.stickhero.config.Config;
import com.stickhero.util.ImageLoader;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

public class GamePlayingPanel extends JPanel{
	private static final long serialVersionUID = 1L;
    private BufferedImage image = new BufferedImage(Config.MAIN_FRAME_WIDTH, Config.MAIN_FRAME_HEIGHT,
			BufferedImage.TYPE_3BYTE_BGR);
    private Image panelImage;
    private Graphics offg = image.getGraphics();
    private ImageLoader imgLoader;
	private Timer timer = null;
	private Service service;
	 
	 public GamePlayingPanel(){
		 timeRepaint();
		 service = new Service(offg);
		 initLand();
		 MouseListener myMouseListener = new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				service.timeBridge = true;
				if(getGameState()==false){
					 service = new Service(offg);
					 initLand();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				service.timeBridge = false;
            	service.rotateBridge();
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}

	        };
	       this.addMouseListener(myMouseListener);
	 }
	 
	 public void timeRepaint(){
	    	TimerTask task = new TimerTask() {
	            public void run() {
	            		repaint();
	            }
	        };
	        timer = new Timer();
	        timer.schedule(task, 0, 1);
	    }

	    public void paint(Graphics g) {
	    	try {
				this.loadImage();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
	    	service.drawLand();
	    	service.drawMan();
	    	service.drawBridge();
	    	g.drawImage(image, 0, 0, this);
	    }

	    public void initLand() {
	        service.firstLand();
	        service.runMan();
	    }
	    
	    public int getScore() {
	    	return service.score;
	        }
	    
	    public boolean getGameState() {
	    	return service.gameisPlaying;
	        }
	    
	    private void loadImage() throws IOException {
			 this.imgLoader = new ImageLoader(Config.Frame_BACKGROUND_IMG);
			 this.panelImage= this.imgLoader.getImage(Config.GAME_BACKGROUND_IMG_POS_X,Config.GAME_BACKGROUND_IMG_POS_Y,
						Config.GAME_BACKGROUND_IMG_WIDTH,Config.GAME_BACKGROUND_IMG_HEIGHT);
			 offg.drawImage(panelImage, 0, 0, null);
			 Font font=new Font("楷体",Font.BOLD,30);
			 offg.setFont(font);
			 offg.drawString("Score:"+service.score, 150, 70);
			 if(this.getGameState()==false){
					offg.drawString("Game Over", 140, 300);
					offg.drawString("If you want again", 80, 350);
					offg.drawString("Please press the mouse", 30, 380);
			 }
		 }
 }

    
