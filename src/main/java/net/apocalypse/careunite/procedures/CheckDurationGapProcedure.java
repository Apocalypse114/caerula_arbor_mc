package net.apocalypse.careunite.procedures;

public class CheckDurationGapProcedure {
	public static boolean execute(double amplifier, double duration) {
		return duration % Math.max(5 - amplifier, 2) == 1;
	}
}
