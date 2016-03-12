package com.stickhero.entity;

public class Bridge {
    private int bridgeLong = 0;
    private boolean changeLong = false;
    private double angle = Math.PI/2;
    public void setBridgeLong(int bridgeLong) {
        this.bridgeLong = bridgeLong;
    }
    public int getBridgeLong(){
        return bridgeLong;
    }
	public boolean isChangeLong() {
		return changeLong;
	}
	public void setChangeLong(boolean changeLong) {
		this.changeLong = changeLong;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
}

