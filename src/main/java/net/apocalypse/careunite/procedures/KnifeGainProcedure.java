package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class KnifeGainProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			ItemStack _setval = (entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY);
			entity.getCapability(CaerulaArborModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
				capability.chitin_knife_selected = _setval.copy();
				capability.syncPlayerVariables(entity);
			});
		}
	}
}
