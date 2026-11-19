package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.BoolArgumentType;

public class SwitchEndspeakerSummonProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
		String info = "";
		boolean cancanneed = false;
		cancanneed = BoolArgumentType.getBool(arguments, "can");
		CaerulaArborModVariables.MapVariables.get(world).endspeakerSummon = cancanneed;
		CaerulaArborModVariables.MapVariables.get(world).syncData(world);
		if (cancanneed) {
			info = Component.translatable("command.endspeaker.summon.true").getString();
		} else {
			info = Component.translatable("command.endspeaker.summon.false").getString();
		}
		{
			final String _success = info;
			final boolean _informAdmins = true;
			arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
		}
	}
}
