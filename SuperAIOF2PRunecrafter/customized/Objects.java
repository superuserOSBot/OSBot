package customized;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.script.Script;

public class Objects {

	public static RS2Object find(Script script, String... names) {
		return script.getObjects().closest(names);
	}

	public static boolean interact(Script script, String action, RS2Object object) throws InterruptedException {

		if (script.getInventory().isItemSelected()) {
			return script.getInventory().deselectItem();
		}

		if (object != null) {
			if (object.exists()) {
				if (object.isVisible()) {

					Paint.action = action + " " + object.getName();
					object.interact(action);

					long t = System.currentTimeMillis();
					while (!script.myPlayer().isAnimating()) {
						Script.sleep(Script.random(250, 500));
						if (Time.timeFromMark(t) > 3000)
							break;
					}

					return true;
				}

				return Walking.walkToObject(script, object);
			}
		}

		return false;
	}

	public static boolean interact(Script script, String action, RS2Object object, Area area)
			throws InterruptedException {

		if (script.getInventory().isItemSelected()) {
			return script.getInventory().deselectItem();
		}

		if (object != null) {
			if (object.exists()) {
				if (object.isVisible()) {

					Paint.action = action + " " + object.getName();
					object.interact(action);

					long t = System.currentTimeMillis();
					while (!area.contains(script.myPlayer())) {
						Script.sleep(Script.random(250, 500));
						if (Time.timeFromMark(t) > 3000)
							break;
					}

					return true;
				}

				return Walking.walkToObject(script, object);
			}
		}

		return false;
	}

	public static boolean interact(Script script, String action, RS2Object object, String itemName)
			throws InterruptedException {

		if (script.getInventory().isItemSelected()) {
			return script.getInventory().deselectItem();
		}

		if (object != null) {
			if (object.exists()) {
				if (object.isVisible()) {

					Paint.action = action + " " + object.getName();
					object.interact(action);

					long t = System.currentTimeMillis();
					while (!script.getInventory().contains(itemName)) {
						Script.sleep(Script.random(250, 500));
						if (Time.timeFromMark(t) > 3000)
							break;
					}

					return true;
				}

				return Walking.walkToObject(script, object);
			}
		}

		return false;
	}
}
