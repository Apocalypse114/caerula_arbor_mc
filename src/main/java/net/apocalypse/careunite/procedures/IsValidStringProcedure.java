package net.apocalypse.careunite.procedures;

public class IsValidStringProcedure {
	public static boolean execute(String item, String name) {
		if (item == null || name == null)
			return false;
		double index = 0;
		String clip = "";
		if ((item).equals("*")) {
			return true;
		}
		if ((name).equals(item)) {
			return true;
		}
		if (item.contains("*")) {
			index = item.indexOf("*", 0);
			if (index < 1) {
				return false;
			}
			clip = item.substring(0, (int) index);
			if ((clip).isEmpty()) {
				return false;
			}
			return name.startsWith(clip);
		}
		return false;
	}
}
