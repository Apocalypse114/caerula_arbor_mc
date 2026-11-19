package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class SetEchoTimeProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		double index = 0;
		double time = 0;
		String info = "";
		index = (int) (DoubleArgumentType.getDouble(arguments, "index"));
		time = (int) (DoubleArgumentType.getDouble(arguments, "duration"));
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "name")) {
				SetEchoProcedure.execute(entityiterator, time, index);
				info = Component.translatable("command.caerula_arbor_reunite.echo.set").getString();
				info = info.replace("<num>", EchoNameFromIndexProcedure.execute((int) index));
				info = info.replace("<t>", "" + time);
				info = info.replace("<name>", entityiterator.getDisplayName().getString());
				ResetRewardLimitProcedure.execute(entityiterator, index, time);
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
