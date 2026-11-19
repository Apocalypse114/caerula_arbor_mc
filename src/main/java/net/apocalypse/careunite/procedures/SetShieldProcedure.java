package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.configuration.CaerulaConfigsConfiguration;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class SetShieldProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		String info = "";
		double fff = 0;
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "name")) {
				fff = Math.min(DoubleArgumentType.getDouble(arguments, "shield"), (double) CaerulaConfigsConfiguration.SHIELD_LIMIT.get());
				{
					double _setval = fff;
					entityiterator.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.player_shield = _setval;
						capability.syncPlayerVariables(entityiterator);
					});
				}
				info = Component.translatable("command.set_shield").getString();
				info = info.replace("<player>", entityiterator.getDisplayName().getString());
				info = info.replace("<num>", "" + Math.round(fff));
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
