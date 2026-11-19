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

import net.apocalypse.careunite.entity.OceanizeRabbitEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class XLPJRabbitSkillsProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp1 = 0;
		double curVar = 0;
		curVar = entity instanceof OceanizeRabbitEntity _datEntI ? _datEntI.getEntityData().get(OceanizeRabbitEntity.DATA_variant) : 0;
		if (curVar < 4.5) {
			if (entity instanceof OceanizeRabbitEntity animatable)
				animatable.setTexture(("oceanized_rabbit_" + (int) curVar));
			return;
		} else {
			if (entity instanceof OceanizeRabbitEntity animatable)
				animatable.setTexture("oceanized_rabbit_bloody");
		}
		if (!entity.isAlive()) {
			return;
		}
		sklp1 = entity instanceof OceanizeRabbitEntity _datEntI ? _datEntI.getEntityData().get(OceanizeRabbitEntity.DATA_swallowP) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (sklp1 > 0) {
			if (entity instanceof OceanizeRabbitEntity _datEntSetI)
				_datEntSetI.getEntityData().set(OceanizeRabbitEntity.DATA_swallowP, (int) (sklp1 - 1));
		} else {
			if (!(enemy == null) && enemy.isAlive()) {
				if ((enemy != null ? entity.distanceTo(enemy) : -1) <= 5) {
					if (entity instanceof OceanizeRabbitEntity) {
						((OceanizeRabbitEntity) entity).setAnimation("animation.oceanized_rabbit.swallow");
					}
					if (entity instanceof OceanizeRabbitEntity _datEntSetI)
						_datEntSetI.getEntityData().set(OceanizeRabbitEntity.DATA_swallowP, 200);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 15, 9, false, false));
					CaerulaArborMod.queueServerWork(8, () -> {
						if (entity.isAlive() && !((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null) && (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).isAlive()) {
							(entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null).hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.OUTSIDE_BORDER), entity),
									(float) ((entity instanceof LivingEntity _livingEntity19 && _livingEntity19.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity19.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 7.99));
						}
					});
				}
			}
		}
	}
}
