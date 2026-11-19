package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.BoolArgumentType;

public class SwitchPlayerEvolutionProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		String info = "";
		boolean unlcok = false;
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "name")) {
				unlcok = BoolArgumentType.getBool(arguments, "unlock");
				{
					boolean _setval = unlcok;
					entityiterator.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.can_player_evo = _setval;
						capability.syncPlayerVariables(entityiterator);
					});
				}
				if (unlcok) {
					info = Component.translatable("command.caerula_arbor_reunite.p_evo.on").getString();
				} else {
					info = Component.translatable("command.caerula_arbor_reunite.p_evo.off").getString();
				}
				info = info.replace("<player>", entityiterator.getDisplayName().getString());
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
