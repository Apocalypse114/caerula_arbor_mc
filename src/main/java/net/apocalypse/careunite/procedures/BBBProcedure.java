package net.apocalypse.careunite.procedures;

import net.minecraft.client.gui.screens.Screen;

public class BBBProcedure {
	public static boolean execute() {
		return Screen.hasShiftDown();
	}
}
