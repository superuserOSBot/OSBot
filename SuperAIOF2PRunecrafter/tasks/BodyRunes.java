package tasks;

import org.osbot.rs07.api.map.Area;
import org.osbot.rs07.api.map.Position;
import org.osbot.rs07.api.ui.EquipmentSlot;
import org.osbot.rs07.script.Script;

import customized.Bank;
import customized.MyPlayer;
import customized.Objects;
import customized.Paint;
import customized.Walking;

public class BodyRunes {

	public static Area ruinsArea = new Area(new Position(3048, 3440, 0), new Position(3059, 3451, 0));
	public static Position ruinsPosition = new Position(3055, 3443, 0);

	public static Area altarArea = new Area(new Position(2505, 4827, 0), new Position(2540, 4855, 0));
	public static Position altarPosition = new Position(2521, 4838, 0);

	public static Area bankArea = new Area(new Position(3088, 3488, 0), new Position(3098, 3499, 0));
	public static Position bankPosition = new Position(3093, 3491, 0);

	public static enum State {
		WITHDRAW_TIARA, OPEN_BANK, NAV_TO_BANK, EQUIP_TIARA, DEPOSIT_ALL, WITHDRAW_RUNE_ESSENCE, WITHDRAW_PURE_ESSENCE, STOP_SCRIPT, CRAFT_RUNES, NAV_TO_ALTAR;
	}

	public static State getState(Script script) throws InterruptedException {
		if (!script.getInventory().isFull()) {
			if (!script.getEquipment().isWearingItem(EquipmentSlot.HAT, "Body tiara")) {
				if (!script.getInventory().contains("Body tiara")) {
					if (bankArea.contains(script.myPlayer())) {
						if (script.getBank().isOpen()) {
							return State.WITHDRAW_TIARA;
						}
						return State.OPEN_BANK;
					}
					return State.NAV_TO_BANK;
				}
				return State.EQUIP_TIARA;
			}
			if (bankArea.contains(script.myPlayer())) {
				if (script.getBank().isOpen()) {
					if (script.getInventory().contains("Body rune")) {
						return State.DEPOSIT_ALL;
					}
					if (script.getBank().contains("Rune essence")) {
						return State.WITHDRAW_RUNE_ESSENCE;
					}
					if (script.getBank().contains("Pure essence")) {
						return State.WITHDRAW_PURE_ESSENCE;
					}
					return State.STOP_SCRIPT;
				}
				return State.OPEN_BANK;
			}
			return State.NAV_TO_BANK;
		}
		if (altarArea.contains(script.myPlayer())) {
			return State.CRAFT_RUNES;
		}
		return State.NAV_TO_ALTAR;
	}

	public static void doTask(Script script) throws InterruptedException {
		switch (getState(script)) {

		case WITHDRAW_TIARA:
			Paint.state = "WITHDRAW_TIARA";
			Bank.depositAllExcept(script, "Body tiara");
			Bank.withdraw(script, 1, "Body tiara");
			break;

		case OPEN_BANK:
			Paint.state = "OPEN_BANK";
			Bank.openBank(script);
			break;

		case NAV_TO_BANK:
			Paint.state = "NAV_TO_BANK";
			if (!script.myPlayer().isAnimating()) {
				if (altarArea.contains(script.myPlayer())) {
					Objects.interact(script, "Use", Objects.find(script, "Portal"), ruinsArea);
				}
				Walking.webWalkToPosition(script, bankPosition, bankArea);
			} else
				Script.sleep(Script.random(250, 750));
			break;

		case EQUIP_TIARA:
			Paint.state = "EQUIP_TIARA";
			MyPlayer.equip(script, EquipmentSlot.HAT, "Body tiara");
			break;

		case DEPOSIT_ALL:
			Paint.state = "DEPOSIT_ALL";
			Bank.depositAll(script);
			break;

		case WITHDRAW_RUNE_ESSENCE:
			Paint.state = "WITHDRAW_RUNE_ESSENCE";
			if (script.getInventory().isEmptyExcept("Pure essence"))
				Bank.withdrawAll(script, "Rune essence");
			else
				Bank.depositAll(script);
			break;

		case WITHDRAW_PURE_ESSENCE:
			Paint.state = "WITHDRAW_PURE_ESSENCE";
			if (script.getInventory().isEmptyExcept("Rune essence"))
				Bank.withdrawAll(script, "Pure essence");
			else
				Bank.depositAll(script);
			break;

		case STOP_SCRIPT:
			Paint.state = "STOP_SCRIPT";
			script.stop();
			break;

		case CRAFT_RUNES:
			Paint.state = "CRAFT_RUNES";
			Objects.interact(script, "Craft-rune", Objects.find(script, "Altar"), "Body rune");
			break;

		case NAV_TO_ALTAR:
			Paint.state = "NAV_TO_ALTAR";
			if (ruinsArea.contains(script.myPlayer())) {
				Objects.interact(script, "Enter", Objects.find(script, "Mysterious ruins"), altarArea);
			}
			Walking.webWalkToPosition(script, ruinsPosition, ruinsArea);
			break;

		default:
			break;
		}
	}
}
