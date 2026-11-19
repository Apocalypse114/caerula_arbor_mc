package net.apocalypse.careunite.procedures;

public class ElectFuncGapProcedure {
	public static boolean execute(double amplifier, double duration) {
		return duration % (30 - Math.min(amplifier, 3) * 5) == 0;
	}
}
