package core;

import java.awt.EventQueue;
import java.awt.Graphics2D;

import org.osbot.rs07.api.Quests.Quest;
import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

import customized.Paint;
import tasks.AirRunes;
import tasks.BodyRunes;
import tasks.EarthRunes;
import tasks.FireRunes;
import tasks.MindRunes;
import tasks.RuneMysteries;
import tasks.WaterRunes;

@ScriptManifest(author = "superuser", info = "Makes all free to play runes. Has option to also complete Rune Mysteries", name = "SuperAIOF2PRunecrafter", version = 1.01, logo = "http://i63.tinypic.com/2kizk1.png")
public class Main extends Script {
	// GUI
	public GUI GUIwindow;
	boolean GUI_INITIALIZED = false;
	// TASKS
	public static boolean runeMysteries = false;
	public static boolean airRunes = false;
	public static boolean mindRunes = false;
	public static boolean waterRunes = false;
	public static boolean earthRunes = false;
	public static boolean fireRunes = false;
	public static boolean bodyRunes = false;

	public static enum State {
		BODY_RUNES, FIRE_RUNES, EARTH_RUNES, WATER_RUNES, MIND_RUNES, AIR_RUNES, RUNE_MYSTERIES, WAIT;
	}

	public State getState() throws InterruptedException {
		if (runeMysteries && !getQuests().isComplete(Quest.RUNE_MYSTERIES))
			return State.RUNE_MYSTERIES;

		if (airRunes)
			return State.AIR_RUNES;

		if (mindRunes)
			return State.MIND_RUNES;

		if (waterRunes)
			return State.WATER_RUNES;

		if (earthRunes)
			return State.EARTH_RUNES;

		if (fireRunes)
			return State.FIRE_RUNES;

		if (bodyRunes)
			return State.BODY_RUNES;

		return State.WAIT;
	}

	public int onLoop() throws InterruptedException {
		switch (getState()) {

		case BODY_RUNES:
			BodyRunes.doTask(this);
			break;
		case FIRE_RUNES:
			FireRunes.doTask(this);
			break;
		case EARTH_RUNES:
			EarthRunes.doTask(this);
			break;
		case WATER_RUNES:
			WaterRunes.doTask(this);
			break;
		case MIND_RUNES:
			MindRunes.doTask(this);
			break;
		case AIR_RUNES:
			AirRunes.doTask(this);
			break;
		case RUNE_MYSTERIES:
			RuneMysteries.doTask(this);
			break;
		case WAIT:
			Script.sleep(1000);
			break;
		default:
			Script.sleep(1000);
			break;
		}
		return 73;
	}

	public void onPaint(Graphics2D g2d) {
		Paint.drawScriptName(this, g2d);
		Paint.drawMain(this, g2d);
		Paint.drawExperienceTrackers(this, g2d);
	}

	public void onStart() throws InterruptedException {
		log("Starting GUI...");
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				GUIwindow = new GUI();
				GUI_INITIALIZED = true;
				GUIwindow.setVisible(true);

				log("GUI started");
				log("Waiting for GUI to be compelted...");
			}
		});
		while (!GUI_INITIALIZED || GUIwindow.isVisible()) {
			sleep(200);
		}
		log("GUI finished");

		if (GUI.chckbxRuneMysteries.isSelected()) {
			log("Complete Rune Mysteries");
			runeMysteries = true;
		}

		String selectedItem = (String) GUI.cbxTasks.getSelectedItem();
		switch (selectedItem) {
		case "Air":
			log("Air Altar");
			airRunes = true;
			break;
		case "Mind":
			log("Mind Altar");
			mindRunes = true;
			break;
		case "Water":
			log("Water Altar");
			waterRunes = true;
			break;
		case "Earth":
			log("Earth Altar");
			earthRunes = true;
			break;
		case "Fire":
			log("Fire Altar");
			fireRunes = true;
			break;
		case "Body":
			log("Body Altar");
			bodyRunes = true;
			break;
		default:
			log("No Altar Selected");
			stop();
		}
	}

	public void onExit() {
		System.gc();
	}
}
