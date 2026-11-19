package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class SetSurvivorCOntractLayerProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		double lvl = 0;
		Entity player = null;
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "name")) {
				player = entityiterator;
				if (player == null) {
					return;
				}
				if ((entityiterator.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new CaerulaArborModVariables.PlayerVariables())).relic_SURVIVOR >= 0) {
					lvl = (int) (DoubleArgumentType.getDouble(arguments, "lvl"));
					{
						double _setval = lvl;
						entityiterator.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
							capability.relic_SURVIVOR = _setval;
							capability.syncPlayerVariables(entityiterator);
						});
					}
					{
						final String _success = (Component.translatable("command.caerula_arbor_reunite.relic.survivor_contract").getString() + "" + (int) lvl);
						final boolean _informAdmins = true;
						arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
					}
				} else {
					arguments.getSource().sendFailure(Component.literal((Component.translatable("command.caerula_arbor_reunite.relic.unlock").getString())));
				}
			}
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}
}
