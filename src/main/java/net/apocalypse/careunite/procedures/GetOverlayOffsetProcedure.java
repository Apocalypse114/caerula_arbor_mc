package net.apocalypse.careunite.procedures;

import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

public class GetOverlayOffsetProcedure {
	public static int x() {
		return Math.toIntExact(Math.round(CaerulaConfigsConfiguration.X_OFFSET.get()));
	}
	public static int y(){
		return Math.toIntExact(Math.round(CaerulaConfigsConfiguration.Y_OFFSET.get()));
	}
}
