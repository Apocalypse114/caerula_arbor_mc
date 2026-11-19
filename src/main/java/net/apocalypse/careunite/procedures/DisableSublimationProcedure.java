package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

import com.mojang.brigadier.context.CommandContext;

public class DisableSublimationProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
		CaerulaArborModVariables.MapVariables.get(world).if_sublimation = false;
		CaerulaArborModVariables.MapVariables.get(world).syncData(world);
		CaerulaArborModVariables.MapVariables.get(world).strategy_sublimation = 0;
		CaerulaArborModVariables.MapVariables.get(world).syncData(world);
		{
			final String _success = (Component.translatable("command.evolution.sublimation.ban").getString());
			final boolean _informAdmins = true;
			arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
		}
	}
}
