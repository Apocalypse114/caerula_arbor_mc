package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.IsharmlaEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class IsharmlaSkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp1 = 0;
		double sklp2 = 0;
		double dura = 0;
		double healP = 0;
		double absP = 0;
		boolean isMonster = false;
		boolean canAttack = false;
		if (!entity.isAlive()) {
			return;
		}
		sklp1 = entity instanceof IsharmlaEntity _datEntI ? _datEntI.getEntityData().get(IsharmlaEntity.DATA_SKILLP_1) : 0;
		sklp2 = entity instanceof IsharmlaEntity _datEntI ? _datEntI.getEntityData().get(IsharmlaEntity.DATA_SKILLP_2) : 0;
		dura = entity instanceof IsharmlaEntity _datEntI ? _datEntI.getEntityData().get(IsharmlaEntity.DATA_DURATION) : 0;
		absP = entity instanceof IsharmlaEntity _datEntI ? _datEntI.getEntityData().get(IsharmlaEntity.DATA_ABSORPTION) : 0;
		isMonster = entity instanceof IsharmlaEntity _datEntL5 && _datEntL5.getEntityData().get(IsharmlaEntity.DATA_IS_MONSTER);
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof IsharmlaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_DURATION, (int) (dura - 1));
		}
		if (absP > 0) {
			IsharmlaAbsorptionProcedure.execute(world, x, y, z, entity, absP);
			if (entity instanceof IsharmlaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_ABSORPTION, (int) (absP - 1));
		}
		canAttack = !(enemy == null) && enemy.isAlive();
		if (isMonster) {
			IsharmlaRimProcedure.execute(world, x, y, z, entity);
			if (entity instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
				_livingEntity11.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(0);
			if (sklp1 > 0) {
				if (entity instanceof IsharmlaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_SKILLP_1, (int) (sklp1 - 1));
			} else if (dura <= 0) {
				if (canAttack) {
					if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 32) {
						if (entity instanceof IsharmlaEntity) {
							((IsharmlaEntity) entity).setAnimation("animation.isharmla.tail_monster");
						}
						if (entity instanceof IsharmlaEntity _datEntSetI)
							_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_SKILLP_1, 300);
						if (entity instanceof IsharmlaEntity _datEntSetI)
							_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_DURATION, 26);
						dura = 26;
						CaerulaArborMod.queueServerWork(10, () -> {
							RangedIsharmlaAttackProcedure.execute(world, x, y, z, entity, 32, 2.5);
						});
					}
				}
			}
			if (sklp2 > 0) {
				if (entity instanceof IsharmlaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_SKILLP_2, (int) (sklp2 - 1));
			} else if (dura <= 0) {
				if (canAttack) {
					if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 32) {
						if (entity instanceof IsharmlaEntity) {
							((IsharmlaEntity) entity).setAnimation("animation.isharmla.bite_monster");
						}
						if (entity instanceof IsharmlaEntity _datEntSetI)
							_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_SKILLP_2, 200);
						if (entity instanceof IsharmlaEntity _datEntSetI)
							_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_DURATION, 40);
						dura = 40;
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 30, 9, false, false));
						CaerulaArborMod.queueServerWork(15, () -> {
							IsharmlaBiteProcedure.execute(world, x, y, z, entity);
						});
					}
				}
			}
		} else {
			if (entity instanceof LivingEntity _livingEntity25 && _livingEntity25.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
				_livingEntity25.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(33);
			healP = entity instanceof IsharmlaEntity _datEntI ? _datEntI.getEntityData().get(IsharmlaEntity.DATA_HEAL_P) : 0;
			if (healP > 0) {
				if (entity instanceof IsharmlaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_HEAL_P, (int) (healP - 1));
			} else if (dura <= 0) {
				if (entity instanceof IsharmlaEntity) {
					((IsharmlaEntity) entity).setAnimation("animation.isharmla.heal_human");
				}
				if (entity instanceof IsharmlaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_HEAL_P, 120);
				if (entity instanceof IsharmlaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_DURATION, 30);
				dura = 30;
				CaerulaArborMod.queueServerWork(15, () -> {
					RangedIsharmlaHealProcedure.execute(world, x, y, z, entity);
				});
			}
			if (sklp1 > 0) {
				if (entity instanceof IsharmlaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_SKILLP_1, (int) (sklp1 - 1));
			} else if (dura <= 0) {
				IsharmlaToMonsterProcedure.execute(world, x, y, z, entity);
				dura = 40;
			}
			if (sklp2 > 0) {
				if (entity instanceof IsharmlaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_SKILLP_2, (int) (sklp2 - 1));
			} else if (dura <= 0) {
				if (entity instanceof IsharmlaEntity) {
					((IsharmlaEntity) entity).setAnimation("animation.isharmla.heal_human");
				}
				if (entity instanceof IsharmlaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_SKILLP_2, 600);
				if (entity instanceof IsharmlaEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_DURATION, 30);
				dura = 30;
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 30, 9, false, false));
				DistributeIsharmlaTearProcedure.execute(world, x, y, z);
			}
			if (entity.tickCount % 400 == 80) {
				CorruptedSpawnMobsProcedure.execute(world, x, y, z, entity, 3);
			}
		}
	}
}
