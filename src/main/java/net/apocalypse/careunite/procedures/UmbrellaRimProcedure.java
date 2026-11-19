package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class UmbrellaRimProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double angle = 0;
		double d = 0;
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
		for (int index0 = 0; index0 < 7; index0++) {
			angle = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			d = Mth.nextDouble(RandomSource.create(), 3.75, 5.25);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.BUBBLE_COLUMN_UP, (x + d * Math.sin(angle)), (y + 0.4), (z + d * Math.cos(angle)), 2, 0.1, 0.1, 0.1, 0.1);
			angle = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			d = Mth.nextDouble(RandomSource.create(), 3.75, 5.25);
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.NERVOUS_IMPAIRMENT.get()), (x + d * Math.sin(angle)), (y + 0.4), (z + d * Math.cos(angle)), 2, 0.1, 0.1, 0.1, 0.1);
		}
		if (CaerulaArborModVariables.MapVariables.get(world).strategy_grow >= 3) {
			for (int index1 = 0; index1 < 9; index1++) {
				angle = Mth.nextDouble(RandomSource.create(), 0, 6.283);
				d = Mth.nextDouble(RandomSource.create(), 6.5, 7.5);
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.BUBBLE_COLUMN_UP, (x + d * Math.sin(angle)), (y + 0.4), (z + d * Math.cos(angle)), 2, 0.15, 0.15, 0.15, 0.1);
				angle = Mth.nextDouble(RandomSource.create(), 0, 6.283);
				d = Mth.nextDouble(RandomSource.create(), 6.5, 7.5);
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.NERVOUS_IMPAIRMENT.get()), (x + d * Math.sin(angle)), (y + 0.4), (z + d * Math.cos(angle)), 2, 0.15, 0.15, 0.15, 0.1);
			}
		}
		if (entity.tickCount % 20 == 0) {
			for (Entity entityiterator : world.getEntities(entity, new AABB((x - 4), (y - 1.5), (z - 4), (x + 4), (y + 2), (z + 4)))) {
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 4) {
					if (IsValidEnemyForSeabornProcedure.execute(entityiterator, entity)) {
						DeductPlayerSanityProcedure.execute(entityiterator,
								(entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity15.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 4);
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic")))),
								(float) (entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity16.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
					}
				}
			}
			if (CaerulaArborModVariables.MapVariables.get(world).strategy_grow >= 3) {
				for (Entity entityiterator : world.getEntities(entity, new AABB((x - 7), (y - 1.75), (z - 7), (x + 7), (y + 3), (z + 7)))) {
					if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 7) {
						if (IsValidEnemyForSeabornProcedure.execute(entityiterator, entity)) {
							DeductPlayerSanityProcedure.execute(entityiterator,
									(entity instanceof LivingEntity _livingEntity21 && _livingEntity21.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity21.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 4);
							entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic")))),
									(float) (entity instanceof LivingEntity _livingEntity22 && _livingEntity22.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity22.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
						}
					}
				}
			}
		}
	}
}
