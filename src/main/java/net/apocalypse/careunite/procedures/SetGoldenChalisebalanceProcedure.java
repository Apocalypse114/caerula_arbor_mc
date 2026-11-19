package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class SetGoldenChalisebalanceProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		double lvl = 0;
		//Entity player = null;
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "name")) {
				final Entity player = entityiterator;
				if (player == null) {
					return;
				}
				lvl = (int) (DoubleArgumentType.getDouble(arguments, "balance"));
				{
					double _setval = lvl;
					player.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
						capability.plauyer_balance = _setval;
						capability.syncPlayerVariables(player);
					});
				}
				{
					final String _success = (Component.translatable("command.caerula_arbor_reunite.relic.golden_chalise").getString() + "" + (int) lvl);
					final boolean _informAdmins = true;
					arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
				}
			}
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
	}
}
