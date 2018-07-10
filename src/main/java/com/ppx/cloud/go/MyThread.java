package com.ppx.cloud.go;

import java.util.concurrent.atomic.AtomicBoolean;

public class MyThread extends Thread {
	private AtomicBoolean b;
	
	public MyThread(AtomicBoolean b) {
		this.b = b;
	}
	
	@Override
	public void run() {
		b.set(false);
	}
}
