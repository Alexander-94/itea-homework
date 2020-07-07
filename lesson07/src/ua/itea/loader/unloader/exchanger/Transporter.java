package ua.itea.loader.unloader.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class Transporter implements Runnable {

	private Cart cart;
	private boolean direction = true;
	private Exchanger<Cart> excLoaderTransp;
	private Exchanger<Cart> excUnloaderTransp;

	public Transporter(Cart cart, Exchanger<Cart> excLoaderTransp, Exchanger<Cart> excUnloaderTransp) {
		super();
		this.excLoaderTransp = excLoaderTransp;
		this.excUnloaderTransp = excUnloaderTransp;
		this.cart = cart;
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (true) {

			if (direction) {
				try {
					cart = excLoaderTransp.exchange(null);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} else {
				try {
					cart = excUnloaderTransp.exchange(null);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("Move " + (direction ? "forward" : "backward") + " cart.");

			try {
				TimeUnit.SECONDS.sleep(3);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			direction = !direction;
			// будим один рандомный поток
			// (из-за флага спит только кто-то один из грузчика и разгрузчика) поэтому можно
			// просто notify();
			synchronized (cart) {
				cart.notify();
			}
		}
	}
}
