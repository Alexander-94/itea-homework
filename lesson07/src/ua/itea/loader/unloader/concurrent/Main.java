package ua.itea.loader.unloader.concurrent;

import java.util.concurrent.Semaphore;

public class Main {
	public static void main(String[] args) {
		Cart cart = new Cart(6);
		Heap heap = new Heap();
		Semaphore semLoad = new Semaphore(1);
		Semaphore semTransp = new Semaphore(0);
		Semaphore semUnload = new Semaphore(0);

		Loader loader = new Loader(heap, cart, semLoad, semTransp);
		Transporter transporter = new Transporter(cart, semLoad, semTransp, semUnload);
		Unloader unloader = new Unloader(cart, semTransp, semUnload);
	}
}
