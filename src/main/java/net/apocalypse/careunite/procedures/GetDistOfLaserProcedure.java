package net.apocalypse.careunite.procedures;

public class GetDistOfLaserProcedure {
	
	public static double execute(double tickInSkill) {
		tickInSkill = 45 - tickInSkill;
		double t = tickInSkill - 18;
		return 1 + t * 1.5;
	}
}
