package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.ThirsterEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class ThirsterAddIntegProcedure {
	@SubscribeEvent
	public static void entityTakesDamage(LivingDamageEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getAmount());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double amount) {
		execute(null, world, x, y, z, entity, amount);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, double amount) {
		if (entity == null)
			return;
		double maxH = 0;
		double interg = 0;
		double dura = 0;
		if (entity instanceof ThirsterEntity) {
			if (!entity.isAlive()) {
				return;
			}
			dura = entity instanceof ThirsterEntity _datEntI ? _datEntI.getEntityData().get(ThirsterEntity.DATA_DURATION) : 0;
			interg = entity instanceof ThirsterEntity _datEntI ? _datEntI.getEntityData().get(ThirsterEntity.DATA_INTEGRATION) : 0;
			maxH = entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1;
			if (entity instanceof ThirsterEntity _datEntSetI)
				_datEntSetI.getEntityData().set(ThirsterEntity.DATA_INTEGRATION, (int) (interg + Math.max(1, amount)));
			interg = interg + Math.max(1, amount);
			if (interg >= maxH * 0.15 && dura <= 0) {
				ThirsterRangedDizzyProcedure.execute(world, x, y, z, entity);
				if (entity instanceof ThirsterEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ThirsterEntity.DATA_INTEGRATION, 0);
				if (entity instanceof ThirsterEntity _datEntSetI)
					_datEntSetI.getEntityData().set(ThirsterEntity.DATA_DURATION, 400);
				if (entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get()))
					_livingEntity9.getAttribute(CaerulaArborModAttributes.LIVING_BARRIER.get()).setBaseValue((maxH - (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)));
			}
		}
	}
}
