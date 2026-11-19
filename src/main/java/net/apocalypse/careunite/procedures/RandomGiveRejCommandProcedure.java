package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

public class RandomGiveRejCommandProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		String info = "";
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "name")) {
				if ((entityiterator.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).disoclusion < 15) {
					GrantRandomExistingRejectionProcedure.execute(entityiterator);
					info = Component.translatable("command.rejection.clear.random.give").getString();
					info = info.replace("<player>", entityiterator.getDisplayName().getString());
					{
						final String _success = info;
						final boolean _informAdmins = true;
						arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
					}
				} else {
					info = Component.translatable("command.rejection.full").getString();
					info = info.replace("<player>", entityiterator.getDisplayName().getString());
					arguments.getSource().sendFailure(Component.literal(info));
				}
			}
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}
}
