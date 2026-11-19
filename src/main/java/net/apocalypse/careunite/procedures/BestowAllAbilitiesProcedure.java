package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.context.CommandContext;

public class BestowAllAbilitiesProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
		double ind = 0;
		String info = "";
		for (int index0 = 0; index0 < 6; index0++) {
			BestowAbilityProcedure.execute(world, (double) index0);
		}
		info = Component.translatable("command.endspeaker.bestow.all").getString();
		{
			final String _success = info;
			final boolean _informAdmins = true;
			arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
		}
	}
}
