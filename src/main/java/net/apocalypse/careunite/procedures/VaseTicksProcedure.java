package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.UsherVaseEntity;

public class VaseTicksProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp = 0;
		double less = 0;
		if (entity.isAlive()) {
			sklp = entity instanceof UsherVaseEntity _datEntI ? _datEntI.getEntityData().get(UsherVaseEntity.DATA_SKILLP) : 0;
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (sklp > -420) {
				if (sklp > 0) {
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 9, false, false));
					if (!world.isClientSide()) {
						if (!(enemy == null) && enemy.isAlive() && (enemy != null ? entity.distanceTo(enemy) : -1) <= 16) {
							if (entity instanceof UsherVaseEntity _datEntSetI)
								_datEntSetI.getEntityData().set(UsherVaseEntity.DATA_TARGET_ID, (int) enemy.getId());
							if (sklp % 20 == 10) {
								if (enemy instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DIZZY.get(), 30, 0, false, false));
								if (enemy instanceof LivingEntity _entity && !_entity.level().isClientSide())
									_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_KNOCKACK_RESISTANVCE.get(), 5, 4, false, false));
								enemy.invulnerableTime = 0;
								enemy.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC), entity),
										(float) ((entity instanceof LivingEntity _livingEntity12 && _livingEntity12.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity12.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0)
												* 0.5));
							}
						} else {
							sklp = 1;
						}
					}
				}
				if (entity instanceof UsherVaseEntity _datEntSetI)
					_datEntSetI.getEntityData().set(UsherVaseEntity.DATA_SKILLP, (int) (sklp - 1));
			} else {
				if (!(enemy == null) && enemy.isAlive() && (enemy != null ? entity.distanceTo(enemy) : -1) <= 12) {
					if (entity instanceof UsherVaseEntity _datEntSetI)
						_datEntSetI.getEntityData().set(UsherVaseEntity.DATA_SKILLP, 200);
				}
			}
		}
	}
}
