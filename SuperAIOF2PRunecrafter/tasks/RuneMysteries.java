package tasks;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.script.Script;

import customized.Configs;
import customized.NPCs;
import customized.Walking;

public class RuneMysteries {

	public static String[] dukeHoracioOptions = { "Have you any quests for me?", "Sure, no problem." };
	public static Area dukeHoracioArea = new Area(new Position(3208, 3218, 1), new Position(3213, 3225, 1));
	public static Position dukeHoracioPosition = new Position(3210, 3221, 1);

	public static String[] sedridorOptions = { "I'm looking for the head wizard.", "Ok, here you are.",
			"Yes, certainly." };
	public static Area sedridorArea = new Area(new Position(3096, 9566, 0), new Position(3107, 9574, 0));
	public static Position sedridorPosition = new Position(3104, 9570, 0);

	public static String[] auburyOptions = { "I have been sent here with a package for you." };
	public static Area auburyArea = new Area(new Position(3250, 3399, 0), new Position(3255, 3404, 0));
	public static Position auburyPosition = new Position(3253, 3401, 0);

	public static enum State {
		TALK_TO_AUBURY, TALK_TO_SEDRIDOR, TALK_TO_DUKE_HORACIO;
	}

	public static State getState(Script script) throws InterruptedException {
		if (Configs.haveConfig(script, 63, 1))
			return State.TALK_TO_SEDRIDOR;
		if (Configs.haveConfig(script, 63, 2))
			return State.TALK_TO_SEDRIDOR;
		if (Configs.haveConfig(script, 63, 3))
			return State.TALK_TO_AUBURY;
		if (Configs.haveConfig(script, 63, 4))
			return State.TALK_TO_AUBURY;
		if (Configs.haveConfig(script, 63, 5))
			return State.TALK_TO_SEDRIDOR;
		return State.TALK_TO_DUKE_HORACIO;
	}

	public static void doTask(Script script) throws InterruptedException {
		switch (getState(script)) {

		case TALK_TO_AUBURY:
			if (auburyArea.contains(script.myPlayer())) {
				if (script.getDialogues().inDialogue()) {
					if (script.getDialogues().isPendingContinuation()) {
						script.getDialogues().clickContinue();
						Script.sleep(Script.random(500, 1000));
					} else {
						script.getDialogues().completeDialogue(auburyOptions);
						Script.sleep(Script.random(500, 1000));
					}
				} else {
					NPCs.interact(script, "Talk-to", NPCs.find(script, "Aubury"));
				}
			} else {
				Walking.webWalkToPosition(script, auburyPosition, auburyArea);
			}
			break;

		case TALK_TO_SEDRIDOR:
			if (sedridorArea.contains(script.myPlayer())) {
				if (script.getDialogues().inDialogue()) {
					if (script.getDialogues().isPendingContinuation()) {
						script.getDialogues().clickContinue();
						Script.sleep(Script.random(500, 1000));
					} else {
						script.getDialogues().completeDialogue(sedridorOptions);
						Script.sleep(Script.random(500, 1000));
					}
				} else {
					NPCs.interact(script, "Talk-to", NPCs.find(script, "Sedridor"));
				}
			} else {
				Walking.webWalkToPosition(script, sedridorPosition, sedridorArea);
			}
			break;

		case TALK_TO_DUKE_HORACIO:
			if (dukeHoracioArea.contains(script.myPlayer())) {
				if (script.getDialogues().inDialogue()) {
					if (script.getDialogues().isPendingContinuation()) {
						script.getDialogues().clickContinue();
						Script.sleep(Script.random(500, 1000));
					} else {
						script.getDialogues().completeDialogue(dukeHoracioOptions);
						Script.sleep(Script.random(500, 1000));
					}
				} else {
					NPCs.interact(script, "Talk-to", NPCs.find(script, "Duke Horacio"));
				}
			} else {
				Walking.webWalkToPosition(script, dukeHoracioPosition, dukeHoracioArea);
			}
			break;

		default:
			break;
		}
	}
}