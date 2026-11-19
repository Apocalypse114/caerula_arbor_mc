package net.apocalypse.careunite.procedures;

public class LeastInFourProcedure {
	public static double execute(double a1, double a2, double a3, double a4) {
		return Math.min(Math.min(a1, a2), Math.min(a3, a4));
	}
}
