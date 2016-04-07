package com.stickhero.action;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.stickhero.config.Config;
import com.stickhero.entity.Bridge;
import com.stickhero.entity.Land;
import com.stickhero.entity.Man;
import com.stickhero.util.*;
public class Service {
	 /**
     * ����½��
     */
    Land[] land = new Land[10];
    Man man = new Man();
    Graphics offg;
	public boolean gameisPlaying=true;//������־
    public int score=0;
    final Color landColor = Color.BLACK;
    final Color bridgeColor = Color.BLACK;
    private Random random = new Random();
    public Bridge bridge = new Bridge();
    public Timer timerBridge = new Timer(),timerBridgeAudio = new Timer();
    Timer timerMoveAll = new Timer();
    Timer timerRunMan = new Timer();
    TimerTask taskRunMan;
    private SoundPlayer bridgeRotateSoundPlayer;
    private SoundPlayer bridgeGrowSoundPlayer;
    private SoundPlayer deadAudioSoundPlayer;
    private SoundPlayer scoreAudioSoundPlayer;
    private final static BasicStroke stokeLine = new BasicStroke( 2.0f );
    
    public Service(Graphics offg) {
    	this.offg = offg;
        Graphics2D g2d = ( Graphics2D )offg;
        g2d.setStroke( stokeLine);
        moveAll();
        try
        {
        	this.initSoundPlayer();
        }
        catch(Exception e){
        	System.out.println("����תʱ��Ƶ��ȡ����");
        }
        changeBridge();
    }

    public void firstLand() {
        land[0] = new Land();
        land[0].setX(0);
        land[0].setWidth(Config.LAND_FIRST_WIDTH);
        for(int i=1;i<10;i++)
            setLandx(i);
        man.setX(0);
    }
    
    public void buildLand() {
                for (int h = 1; h < 10; h++) {
                    land[h - 1] = land[h];
                }
                land[9] = new Land();
                setLandx(9);
    }
    
    private void setLandx(int i){
        int x = 0, width = 0;
        if(land[i] == null)
        {
            land[i] = new Land();
        }
        x = land[i - 1].getX() + land[i-1].getWidth() + random.nextInt(Config.LAND_MAX_DISTANCE);
        width = random.nextInt(Config.LAND_MAX_WIDTH)+Config.LAND_MIN_WIDTH;
        land[i].setX(x);
        land[i].setWidth(width);
    }
    
    public void drawLand() {
        for (int i = 0; i < 10; i++) {
            offg.setColor(Color.BLACK);
            offg.fillRect(land[i].getX(), Config.LAND_POS_Y, land[i].getWidth(), land[i].getHeight());
        }
    }
    
    int temp = 0;
    public boolean timeMoveAll = false;
    public void moveAll(){
        TimerTask taskMoveAll = new TimerTask(){
        	int te =0;
            public void run() {
            	if(timeMoveAll)
            	{
            		timeRunMan = false;
            		if(te ==0 )
            		{
            			te++;
	            		try{
		            		scoreAudioSoundPlayer.play();
		            		score++;
	            		}catch(Exception e){
	            			System.out.println("���ųɹ�ʱ��Ƶ��ȡ����");
	            		}
            		}
	                for(int i=0;i<10;i++)
	                {
	                    land[i].setX(land[i].getX()-6);
	                }
	                man.setX(man.getX()-6);
	                if(land[1].getX()<=0)
	                {
	                	te=0;
	                    timeMoveAll = false;
	                    bridge = new Bridge();
	                    buildLand();
	                    timeRunMan = true;
	                }
            	}
            }
        };
        timerMoveAll.schedule(taskMoveAll, 0,5);
    }
    public void drawMan(){
        offg.drawImage(man.getManImage(),man.getX(), 
        		Config.GAME_BACKGROUND_IMG_HEIGHT-man.getHeight()-land[1].getHeight()+man.getY(),
        		man.getWidth(),man.getHeight(), null);
    }
    public boolean timeRunMan = true;
    public void runMan(){
        taskRunMan = new TimerTask(){
            public void run() {
            	if(timeRunMan)
            	{
	            	judgement();
	                if(man.getRunning()==true)
	                {
	                	bridge.setChangeLong(false);
	                    man.setX(man.getX()+5);
	                    man.setManSign(man.getManSign()*(-1));//ͨ��*-1��1��-1֮���л����ϱ任С��ͼƬ���γ���·Ч��
	                }
	                else
	                {
	                    man.setManSign(1);//С��ֹͣ������Ϊվ��״
	                }
            
            	}
            }
        };
        timerRunMan.schedule(taskRunMan, 0,50);
    }
    public void manDown(){
    	final Timer timerManDown = new Timer();
    	TimerTask taskManDown = new TimerTask(){
			@Override
			public void run() {
				bridgeRotateSoundPlayer.play();
				man.setY(man.getY()+5);
				if(man.getY()>land[0].getHeight())
				{	
					setGameState(false);
					bridge.setChangeLong(false);
					timerManDown.cancel();
					deadAudioSoundPlayer.play();
					try {
						FileUtil.writeScore(score, Config.userInfo_FILE);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
    	};
    	timerManDown.schedule(taskManDown, 0,5);
    }
    public void judgement(){
    	if(bridge.getAngle()!=0)//��δ����ȥʱ��
    	{
            if(man.getX()+man.getWidth()+3<land[0].getWidth())//С�˵�û��½��ͷ
                man.setRunning(true);
            else if(man.getX()+man.getWidth()+3>=land[0].getWidth()&&man.getX()+man.getWidth()+3<land[0].getWidth()+5)
            {
                man.setRunning(false);
                bridge.setChangeLong(true);
            }
    	}
    	else//�Ż���ȥ���Ѿ���ת
        {
        	if(land[0].getWidth()+bridge.getBridgeLong()<land[1].getX()
        			||land[0].getWidth()+bridge.getBridgeLong()>land[1].getX()+land[1].getWidth())//�ж��Ƿ�ʧ��
        	{
        		man.setRunning(true);
            	if(man.getX()+man.getWidth()+3>=land[0].getWidth()+bridge.getBridgeLong())
            	{
            		//���ʧ�ܶ���С���ߵ��ŵľ�ͷִ��----����ת��С�˵���ȥ
            		man.setRunning(false);
            		angle = -Math.PI/2;
            		rotateBridge();
            		manDown();
            	}
        	}
        	else//���û��ʧ��
        	{
        		man.setRunning(true);
        		if(man.getX()+man.getWidth()+3>=land[1].getX()+land[1].getWidth())
	            {
	            	if(man.getRunning())
	            	{
	            		man.setRunning(false);
	            		timeMoveAll = true;
	            	}
	            }
        	}
        }
    	
    }
    public boolean timeBridge = false;
	int delay = -1;
    public void changeBridge(){
        TimerTask taskBridge = new TimerTask(){
            @Override
            public void run() {
            	if(timeBridge)
	            	if(bridge.isChangeLong())
	            	{
		                bridge.setBridgeLong(bridge.getBridgeLong()+4);
	            	}
            }
        };
        timerBridge.schedule(taskBridge, 0,20);
        TimerTask taskBridgeAudio = new TimerTask(){
			@Override
			public void run() {
				if(timeBridge)
	            	if(bridge.isChangeLong())
	            	{
	            		try
	                    {
	            			bridgeGrowSoundPlayer.play();
	                    }
	                    catch(Exception e){
	                    	System.out.println("�ų�����Ƶ��ȡ����");
	                    }
	            	}
			}
        };
        timerBridgeAudio.schedule(taskBridgeAudio,0,80);
    }
    double angle = 0;
    public void rotateBridge(){
    	if(bridge.getBridgeLong()>0)
    	{
	    	final Timer timer4 = new Timer();
	    	TimerTask task4 = new TimerTask(){
	    		@Override
	    		public void run(){
	    			bridge.setAngle(bridge.getAngle()-0.05);
	    			if(Math.sin(bridge.getAngle())<=angle&&angle == 0)
	    			{
	    				bridge.setAngle(0);
	    				judgement();
	    				timer4.cancel();
	    			}
	    			else if(bridge.getAngle()<=-Math.PI/2&&angle != 0)
	    			{
	    				timer4.cancel();
	    			}
	    		}
	    	};
	    	timer4.schedule(task4, 0,10);
    	}
    }
    public void drawBridge(){
    	offg.drawLine(
    			land[0].getX()+land[0].getWidth(),
    			Config.MAIN_FRAME_HEIGHT-land[0].getHeight(),
    			(int) (land[0].getX()+land[0].getWidth()+bridge.getBridgeLong()*Math.cos(bridge.getAngle())),
    			(int) (Config.MAIN_FRAME_HEIGHT-land[0].getHeight()-bridge.getBridgeLong()*Math.sin(bridge.getAngle()))
    			);
    }

    public void setGameState(boolean game){
    	this.gameisPlaying=game;
    }
    
    private void initSoundPlayer() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
    	bridgeRotateSoundPlayer=new SoundPlayer(Config.STICK_ROLL_UP_DOWN_AUDIO);
    	bridgeGrowSoundPlayer=new SoundPlayer(Config.STICK_GROW_AUDIO);
        deadAudioSoundPlayer=new SoundPlayer(Config.HERO_DEAD_AUDIO);
        scoreAudioSoundPlayer=new SoundPlayer(Config.CROSS_BRIDGE_AUDIO);
        }
}
