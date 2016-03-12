package com.stickhero.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.stickhero.config.*;
import com.stickhero.util.*;

public class Top10ScorePanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JLabel top10ScoreLabel;
    private GameButton backButton;
    private GameButton[] scoreButtons;
    private int SCORE_COUNT = 10;
    public static String BACK_BUTTON = "BACK_BUTTON";

    public Top10ScorePanel(GameFrame mainFrame) {
    	this.initComponents(mainFrame);
    }

    private void initComponents(GameFrame mainFrame) {
	this.top10ScoreLabel = new JLabel("<html><font size='5'>Top 10 Scores</font></html>");
	JPanel labelPanel = new JPanel();
	labelPanel.setOpaque(false);
	labelPanel.add(top10ScoreLabel);

	JPanel scorePanel = new JPanel();
	GridLayout gridLayout = new GridLayout(12, 1, 0, 5);
	scorePanel.setLayout(gridLayout);
	scorePanel.setOpaque(false);

	scorePanel.add(labelPanel);

	this.scoreButtons = new GameButton[SCORE_COUNT];
	for (int i = 0; i < SCORE_COUNT; i++) {
	    this.scoreButtons[i] = new GameButton();
	    scorePanel.add(this.scoreButtons[i]);
	}

	this.loadScore();

	this.backButton = new GameButton("BACK");
	this.backButton.setActionCommand(BACK_BUTTON);
	this.backButton.addActionListener(mainFrame);
	scorePanel.add(backButton);

	Dimension d = new Dimension(Config.POP_UP_SCORE_PANEL_WIDTH, Config.POP_UP_SCORE_PANEL_HEIGHT);
	scorePanel.setSize(d);
	scorePanel.setPreferredSize(d);

	this.add(scorePanel);
	this.setOpaque(false);
    }

    public void loadScore() {
    	ArrayList<String> scoreList = new ArrayList<String>();
    	scoreList=FileUtil.readScore(Config.userInfo_FILE);
    	int scoreSize = scoreList.size();
	for (int i = 0; i < scoreSize; i++) {
	    String score = scoreList.get(i);
	    this.scoreButtons[i].setText(score);
	}
    }
}
