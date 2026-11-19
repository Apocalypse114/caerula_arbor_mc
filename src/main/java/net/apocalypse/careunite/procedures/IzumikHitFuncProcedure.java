package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.IzumikEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class IzumikHitFuncProcedure {
	@SubscribeEvent
	public static void entityTakesDamage(LivingDamageEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity(), event.getAmount());
		}
	}

	public static void execute(Entity entity, double amount) {
		execute(null, entity, amount);
	}

	private static void execute(@Nullable Event event, Entity entity, double amount) {
		if (entity == null)
			return;
		double deal = 0;
		deal = entity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_deal) : 0;
		if (entity instanceof IzumikEntity) {
			if (deal >= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.3) {
				if (entity instanceof IzumikEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IzumikEntity.DATA_skillp, 0);
				if (entity instanceof IzumikEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IzumikEntity.DATA_deal, 0);
			} else {
				if (entity instanceof IzumikEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IzumikEntity.DATA_deal, (int) (deal + amount));
			}
		}
	}
}
