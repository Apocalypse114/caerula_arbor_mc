package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.context.CommandContext;

public class EvoLvlAllProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, CommandContext<CommandSourceStack> arguments) {
		EvoLvlSubsisProcedure.execute(world, x, y, z, arguments);
		EvoLvlGrowProcedure.execute(world, x, y, z, arguments);
		EvoLvlBreedProcedure.execute(world, x, y, z, arguments);
		EvoLvlMigraProcedure.execute(world, x, y, z, arguments);
	}
}
