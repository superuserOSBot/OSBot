package customized;

import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.script.Script;

public class MyPlayer {
	
	public static boolean equip(Script script, EquipmentSlot slot, String itemName) throws InterruptedException {
		if (script.getBank().isOpen()) {
			return script.getBank().close();
		}

		if (script.getInventory().isItemSelected()) {
			return script.getInventory().deselectItem();
		}

		Paint.action = "Equip " + itemName;
		script.getEquipment().equip(slot, itemName);

		long t = System.currentTimeMillis();
		while (!script.getEquipment().isWearingItem(slot, itemName)) {
			Script.sleep(Script.random(250, 500));
			if (Time.timeFromMark(t) > 3000) {
				return true;
			}
		}
		return false;
	}
}
