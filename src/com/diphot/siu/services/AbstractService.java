package com.diphot.siu.services;

public class AbstractService {

	protected volatile Boolean running = true;
	
	public void terminate() {
        this.running = false;
    }

	protected void pause(int minutes){
		try {
			Thread.sleep(minutes * 60 * 1000);
		} catch(InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
}
