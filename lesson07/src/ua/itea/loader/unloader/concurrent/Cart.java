package ua.itea.loader.unloader.concurrent;

public class Cart {

	private int capacityCart;
	private int currentCapacity;

	public Cart(int capacityCart) {
		super();
		this.capacityCart = capacityCart;
	}

	public int getCapacityCart() {
		return capacityCart;
	}

	public int getCurrentCapacity() {
		return currentCapacity;
	}

	public void filledCart(int amountSand) {
		if (amountSand > 0) {
			if ((amountSand + currentCapacity) <= capacityCart) {
				currentCapacity += amountSand;
			}
		} else {
			if ((currentCapacity + amountSand) >= 0) {
				currentCapacity += amountSand;
			}
		}

	}

	public boolean isCartFull() {
		return (currentCapacity == capacityCart);
	}

	public boolean isCartEmpty() {
		return (currentCapacity == 0);
	}
}
