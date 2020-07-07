package ua.itea.loader.unloader.exchanger;

import java.util.concurrent.Exchanger;

public class Main {
	public static void main(String[] args) {
		Cart cart = new Cart(6);
		Heap heap = new Heap();
		Exchanger<Cart> excLoadTr = new Exchanger<Cart>();
		Exchanger<Cart> excUnloadTr = new Exchanger<Cart>();

		Loader loader = new Loader(heap, cart, excLoadTr);
		Transporter transporter = new Transporter(cart, excLoadTr, excUnloadTr);
		Unloader unloader = new Unloader(cart, excUnloadTr);
	}
}
