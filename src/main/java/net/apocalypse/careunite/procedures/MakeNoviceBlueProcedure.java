package net.apocalypse.careunite.procedures;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.NoviceAlchemistEntity;

public class MakeNoviceBlueProcedure {
	public static InteractionResult execute(Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.BANNED_ITEM.get()) {
			if (entity instanceof NoviceAlchemistEntity _datEntSetL)
				_datEntSetL.getEntityData().set(NoviceAlchemistEntity.DATA_IS_BLUE, (!(entity instanceof NoviceAlchemistEntity _datEntL2 && _datEntL2.getEntityData().get(NoviceAlchemistEntity.DATA_IS_BLUE))));
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
