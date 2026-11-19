package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

import com.mojang.brigadier.context.CommandContext;

public class DisableSilenceProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
		CaerulaArborModVariables.MapVariables.get(world).silence_enabled = false;
		CaerulaArborModVariables.MapVariables.get(world).syncData(world);
		CaerulaArborModVariables.MapVariables.get(world).strategy_silence = 0;
		CaerulaArborModVariables.MapVariables.get(world).syncData(world);
		{
			final String _success = (Component.translatable("command.evolution.silence.ban").getString());
			final boolean _informAdmins = true;
			arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
		}
	}
}
