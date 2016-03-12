package com.stickhero.ui;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.stickhero.config.Config;
import com.stickhero.util.*;

public class GameFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
    private ImageLoader imgLoader;
    private MenuPanel menuPanel;
    private GamePlayingPanel gamePanel;
    private Top10ScorePanel scorePanel;
    private GameRulePanel rulePanel;
    private SoundPlayer achievementSoundPlayer;
	
	public GameFrame() throws IOException, LineUnavailableException, UnsupportedAudioFileException{
		setTitle("StackHero");//设置标题
		setSize(Config.MAIN_FRAME_WIDTH,Config.MAIN_FRAME_HEIGHT);//设置大小
		setLocationRelativeTo(null);//设置位置(居中)
		setResizable(false);//设置不允许改变窗体大小
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭窗口时退出程序
		setIconImage(new ImageIcon(Config.LOGO_IMG).getImage());
		this.loadImage();
		this.initSoundPlayer();
		this.setBackgroundImage();
		setVisible(true);
	}
	
	 private void loadImage() throws IOException {
		 this.imgLoader = new ImageLoader(Config.Frame_BACKGROUND_IMG);
			Images.Frame_BACKGROUND_IMG = this.imgLoader.getImage(Config.GAME_BACKGROUND_IMG_POS_X,Config.GAME_BACKGROUND_IMG_POS_Y,
					Config.GAME_BACKGROUND_IMG_WIDTH,Config.GAME_BACKGROUND_IMG_HEIGHT);
	 }
	
	private void setBackgroundImage() {
		ImageIcon bgImgIcon = new ImageIcon(Images.Frame_BACKGROUND_IMG);
		JLabel bgLabel = new JLabel(bgImgIcon);
		this.getLayeredPane().add(bgLabel, new Integer(Integer.MIN_VALUE));
		bgLabel.setBounds(0, 0, bgImgIcon.getIconWidth(), bgImgIcon.getIconHeight());
		((JPanel) this.getContentPane()).setOpaque(false);
	    }
	
    private void initSoundPlayer() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
	achievementSoundPlayer = new SoundPlayer(Config.ACHIEVEMENT_AUDIO);
    }
	
    public void MenuPanel() {
	Container c = this.getContentPane();
	c.removeAll();
	this.repaint();
	if (this.menuPanel == null) {
	    this.menuPanel = new MenuPanel(this);
	}
	BoxLayout boxLayout = new BoxLayout(c, BoxLayout.Y_AXIS);
	c.setLayout(boxLayout);
	c.add(Box.createVerticalGlue());
	c.add(this.menuPanel);
	c.add(Box.createVerticalGlue());
	this.validate();
    }
    
    public void GamePanel(){
    	if(this.menuPanel!=null)
    	this.remove(menuPanel);
    	this.repaint();
    	this.gamePanel = new GamePlayingPanel();
    	gamePanel.setBounds(0, 0, 480, 700);
    	this.add(gamePanel);
    }
    
    public void scorePanel(){
    	Container c = this.getContentPane();
    	c.removeAll();
    	this.repaint();
    	if(this.scorePanel==null){
    	    this.scorePanel = new Top10ScorePanel(this);
    	}
    	this.scorePanel.loadScore();
        	BoxLayout boxLayout = new BoxLayout(c, BoxLayout.Y_AXIS);
        	c.setLayout(boxLayout);
        	c.add(Box.createVerticalGlue());
        	c.add(this.scorePanel);
        	c.add(Box.createVerticalGlue());
        	this.validate();
    }
    
    public void rulePanel(){
    	Container c = this.getContentPane();
    	c.removeAll();
    	this.repaint();
    	if(this.rulePanel==null)
    	this.rulePanel = new GameRulePanel(this);
    	BoxLayout boxLayout = new BoxLayout(c, BoxLayout.Y_AXIS);
    	c.setLayout(boxLayout);
    	c.add(Box.createVerticalGlue());
    	c.add(this.rulePanel);
    	c.add(Box.createVerticalGlue());
    	this.validate();
    }
    
    public void actionPerformed(ActionEvent e) {
	String actionCmd = e.getActionCommand();
	if (actionCmd.equals(MenuPanel.START_GAME_BUTTON)) {
	    startGameAction();
	} else if (actionCmd.equals(MenuPanel.TOP_10_SCORES_BUTTON)) {
	    this.achievementSoundPlayer.play();
	    scoreAction();
	} else if (actionCmd.equals(MenuPanel.EXIT_GAME_BUTTON)) {
	    exitGameAction();
	} else if (actionCmd.equals(MenuPanel.RULE_BUTTON)) {
	    helpAction();
	} else if (actionCmd.equals(Top10ScorePanel.BACK_BUTTON)) {
	    this.MenuPanel();
	}
    }
    
    private void startGameAction() {
    	this.GamePanel();
    }
	
    private void exitGameAction() {
    	System.exit(0);
    }
    
    private void helpAction() {
    	this.rulePanel();
    }
    
    private void scoreAction(){
	    this.scorePanel();
    }
}
