package customized;

import org.osbot.rs07.script.Script;

public class Configs {
	public static boolean haveConfig(Script script, int id, int value) {
		return script.getConfigs().isSet(id, value);
	}
}
