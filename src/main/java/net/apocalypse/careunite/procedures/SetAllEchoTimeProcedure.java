package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class SetAllEchoTimeProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		double time = 0;
		String info = "";
		time = (int) (DoubleArgumentType.getDouble(arguments, "duration"));
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "name")) {
				for (int index0 = 0; index0 < 8; index0++) {
					SetEchoProcedure.execute(entityiterator, time, index0);
					ResetRewardLimitProcedure.execute(entityiterator, index0, time);
				}
				info = Component.translatable("command.caerula_arbor_reunite.echo.setall").getString();
				info = info.replace("<t>", "" + (int) time);
				info = info.replace("<name>", entityiterator.getDisplayName().getString());
				{
					final String _success = info;
					final boolean _informAdmins = true;
					arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
				}
			}
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}
}
