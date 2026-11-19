package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class BestowOneAbilityProcedure {
	public static void execute(LevelAccessor world, CommandContext<CommandSourceStack> arguments) {
		String info = "";
		double ind = 0;
		ind = Math.round(DoubleArgumentType.getDouble(arguments, "index"));
		BestowAbilityProcedure.execute(world, ind - 1);
		info = Component.translatable("command.endspeaker.bestow.one").getString();
		info = info.replace("{index}", "" + Math.round(ind));
		{
			final String _success = info;
			final boolean _informAdmins = true;
			arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
		}
	}
}
