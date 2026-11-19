package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class SwitchOceanizeProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		String info = "";
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "name")) {
				{
					double _setval = DoubleArgumentType.getDouble(arguments, "state");
					entityiterator.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.player_oceanization = _setval;
						capability.syncPlayerVariables(entityiterator);
					});
				}
				info = Component.translatable("command.oceanize").getString();
				info = info.replace("<player>", entityiterator.getDisplayName().getString());
				info = info.replace("<num>", "" + Math.round(DoubleArgumentType.getDouble(arguments, "state")));
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
