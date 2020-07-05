package ua.itea;

import java.util.concurrent.CountDownLatch;

public class MusicStopThread implements Runnable {

	private static final int COUNT = 3;
	private CountDownLatch musicStopLatch;
	private boolean isThreadStopped;

	private Player player;

	public MusicStopThread() {

	}

	public MusicStopThread(Player player) {
		this.musicStopLatch = new CountDownLatch(COUNT);
		this.player = player;
		Thread t = new Thread(this);
		t.setDaemon(true);
		t.start();
	}

	@Override
	public void run() {
		while (!isThreadStopped) {
			try {
				musicStopLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			player.stop();
			isThreadStopped = true;
		}

	}

	public CountDownLatch getMusicStopLatch() {
		return musicStopLatch;
	}

}
