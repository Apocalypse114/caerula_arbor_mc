package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.PromotedAxolotlEntity;

public class AxolotlTicksProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double fkdt = 0;
		double cooldown = 0;
		double percPerTick = 0;
		Entity lastEnemy = null;
		Entity lastEnemyEnemy = null;
		Entity recordedLastHelper = null;
		String helperUuid = "";
		if (entity.isAlive()) {
			fkdt = entity instanceof PromotedAxolotlEntity _datEntI ? _datEntI.getEntityData().get(PromotedAxolotlEntity.DATA_FAKE_DEATH_T) : 0;
			cooldown = entity instanceof PromotedAxolotlEntity _datEntI ? _datEntI.getEntityData().get(PromotedAxolotlEntity.DATA_FAKE_D_COOLDOWN) : 0;
			percPerTick = (entity instanceof PromotedAxolotlEntity _datEntI ? _datEntI.getEntityData().get(PromotedAxolotlEntity.DATA_HEAL_PER_S) : 0) * 0.05 * 0.01;
			if (fkdt > 0) {
				if (entity instanceof PromotedAxolotlEntity _datEntSetI)
					_datEntSetI.getEntityData().set(PromotedAxolotlEntity.DATA_FAKE_DEATH_T, (int) (fkdt - 1));
				percPerTick = percPerTick * 2;
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 9, false, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_KNOCKACK_RESISTANVCE.get(), 10, 9, false, false));
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SANITY_COUNTER_BACK.get(), 10, 0, false, false));
				if (fkdt < 170 && fkdt > 20 && (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) >= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
					if (entity instanceof PromotedAxolotlEntity _datEntSetI)
						_datEntSetI.getEntityData().set(PromotedAxolotlEntity.DATA_FAKE_DEATH_T, 20);
					fkdt = 20;
				}
				if (fkdt == 20) {
					if (entity instanceof PromotedAxolotlEntity) {
						((PromotedAxolotlEntity) entity).setAnimation("exit_fake_death");
					}
				}
			} else {
				if (cooldown > 0) {
					if (entity instanceof PromotedAxolotlEntity _datEntSetI)
						_datEntSetI.getEntityData().set(PromotedAxolotlEntity.DATA_FAKE_D_COOLDOWN, (int) (cooldown - 1));
				} else {
					if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
						if (entity instanceof PromotedAxolotlEntity _datEntSetI)
							_datEntSetI.getEntityData().set(PromotedAxolotlEntity.DATA_FAKE_DEATH_T, 300);
						if (entity instanceof PromotedAxolotlEntity _datEntSetI)
							_datEntSetI.getEntityData().set(PromotedAxolotlEntity.DATA_FAKE_D_COOLDOWN, 300);
						if (entity instanceof PromotedAxolotlEntity) {
							((PromotedAxolotlEntity) entity).setAnimation("to_fake_death");
						}
					}
				}
			}
			LivingHealProcedure.execute(entity, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * percPerTick);
			lastEnemy = (entity instanceof LivingEntity _entity) ? _entity.getLastHurtByMob() : null;
			if (!(lastEnemy == null)) {
				lastEnemyEnemy = (lastEnemy instanceof LivingEntity _entity) ? _entity.getLastHurtByMob() : null;
				if (!(lastEnemyEnemy == null)) {
					if (!(lastEnemyEnemy == entity)) {
						LivingHealProcedure.execute(lastEnemyEnemy, (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * percPerTick * 0.5);
					}
				}
			}
		}
	}
}
