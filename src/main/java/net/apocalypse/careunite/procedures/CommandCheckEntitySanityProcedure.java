package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.network.chat.Component;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.CommandSourceStack;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.context.CommandContext;

public class CommandCheckEntitySanityProcedure {
	public static void execute(CommandContext<CommandSourceStack> arguments) {
		Entity ent = null;
		String info = "";
		ent = new Object() {
			public Entity getEntity() {
				try {
					return EntityArgument.getEntity(arguments, "name");
				} catch (CommandSyntaxException e) {
					e.printStackTrace();
					return null;
				}
			}
		}.getEntity();
		if (ent == null) {
			arguments.getSource().sendFailure(Component.literal((Component.translatable("command.sanity.check.fail").getString())));
		}
		info = Component.translatable("command.sanity.check.success").getString();
		info = info.replace("{name}", ent.getDisplayName().getString());
		info = info.replace("{num}",
				"" + Math.round(Math.pow(10, 1)
						* (ent instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY.get()) ? _livingEntity6.getAttribute(CaerulaArborModAttributes.SANITY.get()).getBaseValue() : 0))
						/ Math.pow(10, 1));
		{
			final String _success = info;
			final boolean _informAdmins = true;
			arguments.getSource().sendSuccess(() -> Component.literal(_success), _informAdmins);
		}
	}
}
