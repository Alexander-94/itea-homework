package ua.itea.loader.unloader.concurrent;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Loader implements Runnable {

	private final int LOADER_TAKE = 2; // kg
	private Heap heap;
	private Cart cart;
	private Semaphore semaphoreLoader;
	private Semaphore semaphoreTransp;

	public Loader(Heap heap, Cart cart, Semaphore semaphoreLoader, Semaphore semaphoreTransp) {
		super();
		this.semaphoreLoader = semaphoreLoader;
		this.semaphoreTransp = semaphoreTransp;
		this.heap = heap;
		this.cart = cart;
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (heap.getSandAmount() > 0) {
			try {
				semaphoreLoader.acquire();
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (!cart.isCartFull()) {
				heap.decreaseHeap(LOADER_TAKE);
				cart.filledCart(LOADER_TAKE);
				System.out.println("Loader load " + LOADER_TAKE + " kg of sand.");
				System.out.println("Cart filled on " + cart.getCurrentCapacity() + " of " + cart.getCapacityCart());
				System.out.println("Sand left " + heap.getSandAmount());
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				semaphoreLoader.release();

			} else {
				System.out.println("Cart full.");
				semaphoreTransp.release();
				// cart.filledCart(-6);
			}

		}
	}

}
