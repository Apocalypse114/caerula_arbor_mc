package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.arguments.DoubleArgumentType;

public class CommandHurtSanityProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		Entity ent = null;
		String info = "";
		double num = 0;
		try {
			for (Entity entityiterator : EntityArgument.getEntities(arguments, "name")) {
				ent = entityiterator;
				if (!(ent == null) && ent instanceof LivingEntity) {
					num = num + 1;
					DeductPlayerSanityProcedure.execute(ent, DoubleArgumentType.getDouble(arguments, "amount"));
					if (num == 1) {
						info = Component.translatable("command.sanity.hurt.single").getString();
						info = info.replace("{name}", ent.getDisplayName().getString());
					} else {
						info = Component.translatable("command.sanity.hurt.mult").getString();
					}
				}
			}
		} catch (CommandSyntaxException e) {
			e.printStackTrace();
		}
		info = info.replace("{num}", "" + Math.round(num));
		info = info.replace("{amount}", "" + Math.round(Math.pow(10, 1) * (DoubleArgumentType.getDouble(arguments, "amount"))) / Math.pow(10, 1));
		{
			final String _success = info;
			final boolean _informAdmins = true;
			arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
		}
	}
}
