package com.stickhero.entity;

import com.stickhero.config.Config;

public class Land {
    private int height = Config.LAND_HEIGHT;
    private int width = 0;
    private int x = -1;
    public Land(){
        
    }
    public void setWidth(int landWidth) {
        this.width = landWidth;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight(){
        return height;
    }
}

