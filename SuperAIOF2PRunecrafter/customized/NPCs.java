package customized;

import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.script.Script;

public class NPCs {

	public static NPC find(Script script, String... names) {
		return script.getNpcs().closest(names);
	}

	public static boolean interact(Script script, String action, NPC npc) throws InterruptedException {

		if (script.getInventory().isItemSelected()) {
			return script.getInventory().deselectItem();
		}

		if (npc != null) {
			if (npc.exists()) {
				if (script.getMap().canReach(npc) && npc.isVisible()) {

					Paint.action = action + " " + npc.getName();
					npc.interact(action);

					long t = System.currentTimeMillis();
					while (!script.myPlayer().isInteracting(npc)) {
						Script.sleep(Script.random(250, 500));
						if (Time.timeFromMark(t) > 3000)
							break;
					}

					return true;
				}

				return Walking.walkToNPC(script, npc);
			}
		}

		return false;
	}
}
