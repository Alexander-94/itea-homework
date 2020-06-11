package ua.itea;

import java.util.concurrent.TimeUnit;

public class Man implements Runnable {

	private Mine mine;
	private String name;
	private int gold;
	private Thread thread;
	private static final int MINE_FREQUENCY = 1;
	private static final int MINE_GOLD = 2;

	public int getGold() {
		return gold;
	}

	public void setGold(int gold) {
		this.gold = gold;
	}

	public String getName() {
		return name;
	}

	public Man(Mine mine, String name) {
		super();
		this.mine = mine;
		this.name = name;
		thread = new Thread(this);
		thread.start();
	}

	public Thread getThread() {
		return thread;
	}

	@Override
	public void run() {
		while (mine.getGold() > 0) {
			synchronized (mine) {
				if (mine.getGold() > 1) {
					gold += MINE_GOLD;
					mine.decrGold(MINE_GOLD);
				} else if (mine.getGold() == 1) {
					gold += 1;
					mine.setGold(0);
				}
			}

			// System.out.println(name + ":" + gold + " mine:" + mine.getGold());
			try {
				TimeUnit.SECONDS.sleep(MINE_FREQUENCY);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// System.out.println(name + " end" + ":" + gold + " mine:" + mine.getGold());
	}

}
