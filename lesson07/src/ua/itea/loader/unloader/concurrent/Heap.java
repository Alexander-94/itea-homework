package ua.itea.loader.unloader.concurrent;

public class Heap {

	private int sandAmount = 100;

	public int getSandAmount() {
		return sandAmount;
	}
	
	public void decreaseHeap(int amountTake) {
		sandAmount =sandAmount-amountTake;
	}
}
