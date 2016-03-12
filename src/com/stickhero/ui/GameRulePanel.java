package com.stickhero.ui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JPanel;

import com.stickhero.util.FileUtil;
import com.stickhero.config.*;

public class GameRulePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public GameRulePanel(final GameFrame mainFrame){
		Dimension d = new Dimension(Config.MAIN_FRAME_WIDTH, Config.MAIN_FRAME_HEIGHT);
		this.setSize(d);
		this.setPreferredSize(d);
		MouseListener myMouseListener = new MouseListener(){
			public void mouseClicked(MouseEvent e) {
				mainFrame.MenuPanel();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		this.addMouseListener(myMouseListener);
	}
	
	public void paint(Graphics g){ 
		Font font=new Font("ËÎÌו",Font.BOLD,15);
		g.setFont(font);
		ArrayList<String> rule=new ArrayList<String>();
		try {
			rule=FileUtil.readFileToString(Config.ruleInfo_FILE);
		} catch (IOException e) {
		e.printStackTrace();
		}
		for(int i=1;i<rule.size();i++){
		 g.drawString(rule.get(i), 30, 70+40*i);
	 }
		
	}

	
}
