package com.stickhero.entity;

import java.awt.Image;
import java.io.IOException;

import com.stickhero.config.Config;
import com.stickhero.util.ImageLoader;

public class Man {
		private ImageLoader imgLoader;
	 	final int width = 15;
	    final int height = 20;
	    private int x = 0;
	    private int y = 0;
	    private int manSign = 1;
	    private boolean running = false;
	    private Image heroImage1;
	    private Image heroImage2;
	    
	    public Man(){
	        try {
	        	this.imgLoader = new ImageLoader(Config.HeroImage1);
				this.heroImage1 = this.imgLoader.getImage(Config.HERO_IMG1_POS_X,Config.HERO_IMG1_POS_Y,
						Config.HERO_IMG1_WIDTH,Config.HERO_IMG1_HEIGHT);
				this.imgLoader=new ImageLoader(Config.HeroImage2);
				this.heroImage2=this.imgLoader.getImage(Config.HERO_IMG2_POS_X,Config.HERO_IMG2_POS_Y,
						Config.HERO_IMG2_WIDTH,Config.HERO_IMG2_HEIGHT);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    public int getX(){
	        return x;
	    }
	    public void setX(int x){
	        this.x = x;
	    }
	    public void setManSign(int sign){
	        manSign = sign;
	    }
	    public int getManSign(){
	        return manSign;
	    }
	    public Image getManImage(){
	        if(manSign == 1)//
	            return heroImage1;
	        else
	            return heroImage2;
	    }
	    public void setRunning(boolean a){
	        running = a;
	    }
	    public boolean getRunning(){
	        return running;
	    }
	    public int getWidth(){
	        return width;
	    }
	    public int getHeight(){
	        return height;
	    }
		public int getY() {
			return y;
		}
		public void setY(int y) {
			this.y = y;
		}
}
