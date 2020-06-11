package ua.itea;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Barracks implements Runnable {

	private Mine mine;
	private static final int ADD_FREQUENCY = 5;

	public Barracks(Mine mine) {
		super();
		this.mine = mine;
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		Random rnd = new Random();

		while (mine.getGold() > 0) {
			Man man = new Man(mine, "ManB" + rnd.nextInt(50));
			MineApp.miners.add(man);
			try {
				TimeUnit.SECONDS.sleep(ADD_FREQUENCY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
