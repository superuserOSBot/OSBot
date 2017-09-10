package customized;

import org.osbot.rs07.script.Script;

public class Bank {
	public static boolean isOpen(Script script) {
		return script.getBank().isOpen();
	}

	public static boolean contains(Script script, String... itemNames) {
		return script.getBank().contains(itemNames);
	}

	public static boolean withdraw(Script script, int amount, String itemName) throws InterruptedException {

		Paint.action = "Withdraw " + amount + " " + itemName;
		script.getBank().withdraw(itemName, amount);

		long t = System.currentTimeMillis();
		while (!script.getInventory().contains(itemName)) {
			Script.sleep(Script.random(250, 500));
			if (Time.timeFromMark(t) > 3000)
				return true;
		}

		return false;
	}

	public static boolean withdrawAll(Script script, String itemName) throws InterruptedException {
		int random = Script.random(1, 4);

		if (random == 1) {
			Paint.action = "Withdraw All " + itemName;
			script.getBank().withdrawAll(itemName);
		} else if (random == 2) {
			Paint.action = "Withdraw All But One " + itemName;
			script.getBank().withdrawAllButOne(itemName);
		} else {
			Paint.action = "Withdraw 28 " + itemName;
			script.getBank().withdraw(itemName, 28);
		}

		long t = System.currentTimeMillis();
		while (!script.getInventory().contains(itemName)) {
			Script.sleep(Script.random(250, 500));
			if (Time.timeFromMark(t) > 3000)
				return true;
		}

		return false;
	}

	public static boolean depositAll(Script script) throws InterruptedException {
		
		Paint.action = "Deposit All";
		script.getBank().depositAll();

		long t = System.currentTimeMillis();
		while (!script.getInventory().isEmpty()) {
			Script.sleep(Script.random(250, 500));
			if (Time.timeFromMark(t) > 3000)
				return true;
		}

		return false;
	}
	
	public static boolean depositAllExcept(Script script, String... itemNames) throws InterruptedException {
		
		Paint.action = "Deposit All Except " + itemNames[0];
		script.getBank().depositAllExcept(itemNames);

		long t = System.currentTimeMillis();
		while (!script.getInventory().isEmptyExcept(itemNames)) {
			Script.sleep(Script.random(250, 500));
			if (Time.timeFromMark(t) > 3000)
				return true;
		}

		return false;
	}

	public static boolean closeBank(Script script) throws InterruptedException {
		
		Paint.action = "Close Bank";
		script.getBank().close();

		long t = System.currentTimeMillis();
		while (script.getBank().isOpen()) {
			Script.sleep(Script.random(250, 500));
			if (Time.timeFromMark(t) > 3000)
				return true;
		}

		return false;
	}

	public static boolean openBank(Script script) throws InterruptedException {
		if (script.getInventory().isItemSelected()) {
			return script.getInventory().deselectItem();
		}

		Paint.action = "Open Bank";
		script.getBank().open();

		long t = System.currentTimeMillis();
		while (!script.getBank().isOpen()) {
			Script.sleep(Script.random(250, 500));
			if (Time.timeFromMark(t) > 3000) {
				return true;
			}
		}
		return false;
	}
}
