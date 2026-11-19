package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.context.CommandContext;

public class EvoAddAllProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, CommandContext<CommandSourceStack> arguments) {
		EvoAddGrowProcedure.execute(world, x, y, z, arguments);
		EvoAddBreedProcedure.execute(world, x, y, z, arguments);
		EvoAddMigraProcedure.execute(world, x, y, z, arguments);
		EvoAddSubsProcedure.execute(world, x, y, z, arguments);
	}
}
