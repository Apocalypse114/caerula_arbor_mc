package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDamageEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.entity.CreeperPetEntity;
import net.apocalypse.careunite.entity.CreeperFishEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CreeprHitFuncProcedure {
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
		if (entity instanceof CreeperFishEntity || entity instanceof CreeperPetEntity) {
			if (amount > (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1)) {
				return;
			}
			if (!entity.isAlive()) {
				return;
			}
			if (entity instanceof CreeperFishEntity) {
				deal = (entity instanceof CreeperFishEntity _datEntI ? _datEntI.getEntityData().get(CreeperFishEntity.DATA_deal) : 0) + amount;
			} else {
				deal = (entity instanceof CreeperPetEntity _datEntI ? _datEntI.getEntityData().get(CreeperPetEntity.DATA_DEAL) : 0) + amount;
			}
			if (entity instanceof CreeperFishEntity _datEntSetI)
				_datEntSetI.getEntityData().set(CreeperFishEntity.DATA_deal, (int) deal);
			if (entity instanceof CreeperPetEntity _datEntSetI)
				_datEntSetI.getEntityData().set(CreeperPetEntity.DATA_DEAL, (int) deal);
			if (deal >= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.15) {
				if (entity instanceof CreeperFishEntity _datEntSetI)
					_datEntSetI.getEntityData().set(CreeperFishEntity.DATA_deal, 0);
				if (entity instanceof CreeperPetEntity _datEntSetI)
					_datEntSetI.getEntityData().set(CreeperPetEntity.DATA_DEAL, 0);
				if (entity instanceof CreeperFishEntity) {
					RangedSanityAttackProcedure.execute(world, x, y, z, entity);
					if (CaerulaArborModVariables.MapVariables.get(world).strategy_grow > 2) {
						CaerulaArborMod.queueServerWork(2, () -> {
							MutableSanityAttackProcedure.execute(world, x, y, z, entity, 8, 1.5);
						});
					}
				} else {
					RangedPetSanityAttackProcedure.execute(world, x, y, z, entity);
				}
			}
		}
	}
}
