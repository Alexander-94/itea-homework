package ua.itea;

import java.util.concurrent.TimeUnit;

public class Logger implements Runnable {

	private Mine mine;
	private static final int LOG_FREQUENCY = 1;

	public Logger(Mine mine) {
		super();
		this.mine = mine;
		Thread thread = new Thread(this);
		thread.run();
	}

	@Override
	public void run() {
		while (mine.getGold() > 0) {
			System.out.println("|------");
			for (Man m : MineApp.miners) {
				System.out.println("|Logger:" + m.getName() + ":" + m.getGold());
			}

			System.out.println("|Logger:Mine:" + mine.getGold());
			try {
				TimeUnit.SECONDS.sleep(LOG_FREQUENCY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
