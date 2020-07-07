package ua.itea.loader.unloader.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Unloader implements Runnable {

	private final int UNLOADER_TAKE = 3; // kg
	private Cart cart;
	private Semaphore semaphoreUnloader;
	private Semaphore semaphoreTransp;

	public Unloader(Cart cart, Semaphore semaphoreTransp, Semaphore semaphoreUnloader) {
		super();
		this.cart = cart;
		this.semaphoreTransp = semaphoreTransp;
		this.semaphoreUnloader = semaphoreUnloader;
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				semaphoreUnloader.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (!cart.isCartEmpty()) {
				cart.filledCart(-UNLOADER_TAKE);
				System.out.println("Unloader unload " + UNLOADER_TAKE + " kg of sand.");
				System.out.println("Cart filled on " + cart.getCurrentCapacity() + " of " + cart.getCapacityCart());

				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				semaphoreUnloader.release();

			} else {
				System.out.println("Cart is empty.");
				semaphoreTransp.release();
			}

		}

	}
}
