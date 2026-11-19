package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.TideChimeraEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CreeprChimeraHitFuncProcedure {
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
		double deal = 0;
		if (entity instanceof TideChimeraEntity) {
			if (amount > (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)) {
				return;
			}
			if (!entity.isAlive()) {
				return;
			}
			deal = (entity instanceof TideChimeraEntity _datEntI ? _datEntI.getEntityData().get(TideChimeraEntity.DATA_deal) : 0) + amount;
			if (entity instanceof TideChimeraEntity _datEntSetI)
				_datEntSetI.getEntityData().set(TideChimeraEntity.DATA_deal, (int) deal);
			if (deal >= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.25) {
				if (entity instanceof TideChimeraEntity _datEntSetI)
					_datEntSetI.getEntityData().set(TideChimeraEntity.DATA_deal, 0);
				MutableSanityAttackProcedure.execute(world, x, y, z, entity, 8, 1);
			}
		}
	}
}
