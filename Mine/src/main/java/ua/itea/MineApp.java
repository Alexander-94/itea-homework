package ua.itea;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MineApp {

	public static List<Man> miners = new CopyOnWriteArrayList<Man>();

	public static void main(String[] args) {

		Mine mine = new Mine();
		Man man1 = new Man(mine, "Man1");
		miners.add(man1);
		Man man2 = new Man(mine, "Man2");
		miners.add(man2);
		Man man3 = new Man(mine, "Man3");
		miners.add(man3);
		new Barracks(mine);
		new Logger(mine);

		try {
			for (Man m : miners) {
				m.getThread().join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("|------");
		System.out.println("|Results:");
		int rez = 0;
		for (Man m : miners) {
			System.out.println("|" + m.getName() + ":" + m.getGold());
			rez += m.getGold();
		}
		System.out.println("|Mined gold:" + rez + ", Mine gold:" + mine.getGold());

	}
}
