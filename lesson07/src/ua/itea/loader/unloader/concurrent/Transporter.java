package ua.itea.loader.unloader.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Transporter implements Runnable {

	private Cart cart;
	private boolean direction = true;
	private Semaphore semaphoreLoader;
	private Semaphore semaphoreUnloader;
	private Semaphore semaphoreTransp;

	public Transporter(Cart cart, Semaphore semaphoreLoader, Semaphore semaphoreTransp, Semaphore semaphoreUnloader) {
		super();
		this.semaphoreLoader = semaphoreLoader;
		this.semaphoreTransp = semaphoreTransp;
		this.semaphoreUnloader = semaphoreUnloader;
		this.cart = cart;
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				semaphoreTransp.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			System.out.println("Move " + (direction ? "forward" : "backward") + " cart.");

			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (direction) {
				semaphoreUnloader.release();
			} else {
				semaphoreLoader.release();
			}
			direction = !direction;
		}

	}

}
