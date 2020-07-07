package ua.itea.loader.unloader.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Unloader implements Runnable {

	private final int UNLOADER_TAKE = 3; // kg
	private Cart cart;
	private Exchanger<Cart> excUnloaderTransp;

	public Unloader(Cart cart, Exchanger<Cart> excUnloaderTransp) {
		super();
		this.cart = cart;
		this.excUnloaderTransp = excUnloaderTransp;
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (true) {

			synchronized (cart) {
				if (cart.isloaderTurn()) {
					try {
						//присыпляем разгрузчика после отправки телеги
						cart.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			// System.out.println("---Unloader turn");
			if (!cart.isCartEmpty()) {
				cart.filledCart(-UNLOADER_TAKE);
				System.out.println("Unloader unload " + UNLOADER_TAKE + " kg of sand.");
				System.out.println("Cart filled on " + cart.getCurrentCapacity() + " of " + cart.getCapacityCart());

				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			} else {
				System.out.println("Cart is empty.");
				try {
					excUnloaderTransp.exchange(cart);
					cart.setloaderTurn(true);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

	}
}
