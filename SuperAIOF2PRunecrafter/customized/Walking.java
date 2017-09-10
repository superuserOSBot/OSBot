package customized;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.model.NPC;
import org.osbot.rs07.api.model.RS2Object;
import org.osbot.rs07.event.WalkingEvent;
import org.osbot.rs07.event.WebWalkEvent;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.utility.Condition;

public class Walking {
	public static WalkingEvent wEvent;
	public static WebWalkEvent wwEvent;

	public static boolean walkToPosition(Script script, Position position) {
		Paint.action = "Walk to position";
		wEvent = new WalkingEvent(position);
		wEvent.setBreakCondition(new Condition() {
			public boolean evaluate() {
				if (script.getMap().distance(position) <= 2) {
					return true;
				}
				return false;
			}
		});
		script.execute(wEvent);
		return false;
	}

	public static boolean walkToObject(Script script, RS2Object object) {
		Paint.action = "Walk to object";
		wEvent = new WalkingEvent(object);
		wEvent.setBreakCondition(new Condition() {
			public boolean evaluate() {
				if (object.isVisible()) {
					return true;
				}
				return false;
			}
		});
		script.execute(wEvent);
		return false;
	}

	public static boolean walkToNPC(Script script, NPC npc) {
		Paint.action = "Walk to NPC";
		wEvent = new WalkingEvent(npc);
		wEvent.setBreakCondition(new Condition() {
			public boolean evaluate() {
				if (npc.isVisible()) {
					return true;
				}
				return false;
			}
		});
		script.execute(wEvent);
		return false;
	}

	public static boolean webWalkToPosition(Script script, Position position) {
		Paint.action = "Webwalk to position";
		wwEvent = new WebWalkEvent(position);
		wwEvent.setBreakCondition(new Condition() {
			public boolean evaluate() {
				if (script.getMap().distance(position) <= 2) {
					return true;
				}
				return false;
			}
		});
		script.execute(wwEvent);
		return false;
	}

	public static boolean webWalkToPosition(Script script, Position position, Area area) {
		Paint.action = "Webwalk to position";
		wwEvent = new WebWalkEvent(position);
		wwEvent.setBreakCondition(new Condition() {
			public boolean evaluate() {
				if (area.contains(script.myPlayer())) {
					return true;
				}
				return false;
			}
		});
		script.execute(wwEvent);
		return false;
	}
}
