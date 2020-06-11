package ua.itea;

public class Mine {

	private int gold = 100;

	public synchronized int getGold() {
		return gold;
	}

	public int decrGold(int amt) {
		gold -= amt;
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

}
