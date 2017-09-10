package customized;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.util.ArrayList;

import org.osbot.rs07.api.ui.Skill;
import org.osbot.rs07.script.Script;

public class Paint {
	private final static int ALPHA = 160, X = 7, Y = 344, WIDTH = 489, HEIGHT = 15;
	private static final String[] suffix = new String[] { "", "k", "m", "b", "t" };
	private static boolean scriptStarted, startedThread;
	public static Font customFont12 = new Font("Arial", 1, 12);
	public static Font customFont20 = new Font("Lucida Calligraphy", 1, 20);
	public static Color fadedBlack = new Color(0.0f, 0.0f, 0.0f, 0.5f);
	public static Color tan = new Color(208, 189, 155);
	public static String state = " ";
	public static String action = " ";
	public static long startTime = System.currentTimeMillis();

	public static void drawScriptName(Script script, Graphics2D g) {
		g.setColor(Color.white);
		g.setFont(customFont20);
		g.drawString("SuperAIOF2PRunecrafter v1.01", 230, 336);
	}

	public static void drawMain(Script script, Graphics2D g) {
		g.setColor(fadedBlack);
		g.fillRect(4, 281, 225, 57);

		g.setColor(Color.white);
		g.setFont(customFont12);
		g.drawString("Runtime: " + Time.msToString(System.currentTimeMillis() - startTime), 9, 297);
		g.drawString("State: " + state, 9, 317);
		g.drawString("Action: " + action, 9, 332);

		g.setColor(tan);
		g.fillRect(7, 459, 115, 15); // 344
		g.setColor(Color.black);
		g.drawString("Made by superuser", 10, 471);
	}

	public static void drawMouse(Script script, Graphics2D g) {
		g.setColor(Color.white);
		int mouseX = (int) script.mouse.getPosition().getX();
		int mouseY = (int) script.mouse.getPosition().getY();
		g.drawLine(mouseX - 5, mouseY + 5, mouseX + 5, mouseY - 5);
		g.drawLine(mouseX - 5, mouseY - 5, mouseX + 5, mouseY + 5);
	}

	public static void drawExperienceTrackers(Script script, Graphics2D g) {
		if (scriptStarted) {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			java.util.List<PaintSkill> paintSkillList = new ArrayList<>();

			for (int i = 0; i < PaintSkill.values().length; i++) {
				if (script.getExperienceTracker().getGainedXP(PaintSkill.values()[i].getSkill()) > 0)
					paintSkillList.add(PaintSkill.values()[i]);
			}

			if (!paintSkillList.isEmpty()) {
				int size = paintSkillList.size() > 9 ? 9 : paintSkillList.size();
				for (int i = 0; i < size; i++) {
					PaintSkill skill = paintSkillList.get(i);

					g.setFont(new Font("Arial", Font.PLAIN, 12));

					g.setColor(skill.getBackground());
					g.fillRoundRect(X, (Y + (i * HEIGHT) + 1), WIDTH, HEIGHT, 5, 5);

					g.setColor(skill.getForeground());
					g.fillRoundRect(X, (Y + (i * HEIGHT) + 1), getBarFillPercent(WIDTH, script, skill.getSkill()),
							HEIGHT, 5, 5);

					String textSkillName = "|  " + skill.getSkill().name();

					String textSkillLevels = "|  Lvl." + script.getSkills().getStatic(skill.getSkill());

					String textXPGained = "|  XP Gained: " + script.getExperienceTracker().getGainedXP(skill.getSkill())
							+ "(" + format(script.getExperienceTracker().getGainedXPPerHour(skill.getSkill())) + ")";

					String textTTL = "|  TTL: "
							+ Time.msToString(script.getExperienceTracker().getTimeToLevel(skill.getSkill()));

					g.setColor((skill.equals(PaintSkill.PRAYER) || skill.equals(PaintSkill.RUNECRAFTING)) ? Color.black
							: Color.white);
					g.drawString(textSkillName, 12, (13 + Y + (i * HEIGHT)));
					g.drawString(textSkillLevels, 120, (13 + Y + (i * HEIGHT)));
					g.drawString(textXPGained, 175, (13 + Y + (i * HEIGHT)));
					g.drawString(textTTL, 335, (13 + Y + (i * HEIGHT)));
				}
			}

			g.dispose();
		} else if (!startedThread) {
			new Thread(() -> startSkillTrackers(script)).start();
			startedThread = true;
		}
	}

	private static int getBarFillPercent(int barWidth, Script script, Skill skill) {
		double xpTL = script.getSkills().getExperienceForLevel(script.getSkills().getStatic(skill));
		double xpTL1 = script.getSkills().getExperienceForLevel(script.getSkills().getStatic(skill) + 1);
		double percentTNL = ((script.getSkills().getExperience(skill) - xpTL) / (xpTL1 - xpTL) * 100);
		return (int) ((barWidth / 100D) * percentTNL);
	}

	private static String format(double number) {
		String r = new DecimalFormat("##0E0").format(number);
		r = r.replaceAll("E[0-9]", suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]);
		while (r.length() > 4 || r.matches("[0-9]+\\.[a-z]")) {
			r = r.substring(0, r.length() - 2) + r.substring(r.length() - 1);
		}
		return r;
	}

	private static void startSkillTrackers(Script script) {
		while (!script.getClient().isLoggedIn() && script.getBot().getScriptExecutor().isRunning())
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		script.getExperienceTracker().startAll();
		scriptStarted = true;
		script.log("Started skill trackers!"); // Notify
	}

	private enum PaintSkill {
		ATTACK(new Color(80, 17, 8, ALPHA)), /**/
		DEFENCE(new Color(111, 131, 196, ALPHA)), /**/
		STRENGTH(new Color(13, 112, 65, ALPHA)), /**/
		HITPOINTS(new Color(206, 58, 71, ALPHA)), /**/
		RANGED(new Color(85, 111, 13, ALPHA)), /**/
		PRAYER(new Color(224, 220, 220, ALPHA)), /**/
		MAGIC(new Color(162, 150, 129, ALPHA)), /**/
		COOKING(new Color(61, 12, 77, ALPHA)), /**/
		WOODCUTTING(new Color(145, 115, 53, ALPHA)), /**/
		FLETCHING(new Color(0, 36, 37, ALPHA)), /**/
		FISHING(new Color(96, 128, 161, ALPHA)), /**/
		FIREMAKING(new Color(202, 86, 12, ALPHA)), /**/
		CRAFTING(new Color(125, 90, 56, ALPHA)), /**/
		SMITHING(new Color(52, 52, 31, ALPHA)), /**/
		MINING(new Color(56, 97, 100, ALPHA)), /**/
		HERBLORE(new Color(3, 69, 5, ALPHA)), /**/
		AGILITY(new Color(23, 25, 96, ALPHA)), /**/
		THIEVING(new Color(82, 29, 62, ALPHA)), /**/
		SLAYER(new Color(22, 20, 20, ALPHA)), /**/
		FARMING(new Color(3, 56, 3, ALPHA)), /**/
		RUNECRAFTING(new Color(181, 181, 169, ALPHA)), /**/
		HUNTER(new Color(99, 91, 56, ALPHA)), /**/
		CONSTRUCTION(new Color(155, 143, 123, ALPHA)); /**/

		private final Color foreground;
		private final Color background;

		PaintSkill(Color background) {
			this.background = background;
			this.foreground = new Color(background.getRed(), background.getGreen(), background.getBlue()).brighter();
		}

		public Color getForeground() {
			return foreground;
		}

		public Color getBackground() {
			return background;
		}

		public Skill getSkill() {
			return Skill.values()[ordinal()];
		}
	}
}