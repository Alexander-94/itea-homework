package ua.itea.loader.unloader.exchanger;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class Loader implements Runnable {

	private final int LOADER_TAKE = 2; // kg
	private Heap heap;
	private Cart cart;
	private Exchanger<Cart> excLoaderTransp;

	public Loader(Heap heap, Cart cart, Exchanger<Cart> excLoaderTransp) {
		super();
		this.excLoaderTransp = excLoaderTransp;
		this.heap = heap;
		this.cart = cart;
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		while (heap.getSandAmount() > 0) {

			synchronized (cart) {
				if(!cart.isloaderTurn()) {
					try {
						// присыпляем грузчика после отправки телеги
						cart.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
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

			} else {
				System.out.println("Cart full.");
				try {
					excLoaderTransp.exchange(cart);
					cart.setloaderTurn(false);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		}
	}

}
