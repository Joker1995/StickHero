package com.stickhero.main;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.stickhero.ui.*;

public class StickHero {
	public static void main(String[] args) throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        // TODO code application logic here
        GameFrame frm=new GameFrame();
        frm.MenuPanel();
    }
}
