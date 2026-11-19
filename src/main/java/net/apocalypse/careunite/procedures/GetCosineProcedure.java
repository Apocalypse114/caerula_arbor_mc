package net.apocalypse.careunite.procedures;

public class GetCosineProcedure {
	public static double execute(double a1, double a2, double b1, double b2) {
		double dotProd = 0;
		double norm = 0;
		double norm1 = 0;
		dotProd = a1 * a2 + b1 * b2;
		norm = Math.pow(a1 * a1 + b1 * b1, 0.5);
		norm1 = Math.pow(a2 * a2 + b2 * b2, 0.5);
		if (norm * norm1 == 0) {
			return 1;
		}
		return dotProd / (norm * norm1);
	}
}
