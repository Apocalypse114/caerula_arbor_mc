package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;

public class UmbrellaBigRimProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double angle = 0;
		double d = 0;
		double daam = 0;
		if (entity instanceof LivingEntity _entity)
			_entity.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
		for (int index0 = 0; index0 < 8; index0++) {
			angle = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			d = Mth.nextDouble(RandomSource.create(), 6.5, 6.75);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.BUBBLE_COLUMN_UP, (x + d * Math.sin(angle)), (y + 0.4), (z + d * Math.cos(angle)), 2, 0.1, 0.1, 0.1, 0.1);
			angle = Mth.nextDouble(RandomSource.create(), 0, 6.283);
			d = Mth.nextDouble(RandomSource.create(), 6.5, 6.75);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.ELECTRIC_SPARK, (x + d * Math.sin(angle)), (y + 0.4), (z + d * Math.cos(angle)), 2, 0.1, 0.1, 0.1, 0.1);
		}
		if (entity.tickCount % 20 == 0) {
			angle = entity instanceof LivingEntity _livingEntity8 && _livingEntity8.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity8.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
			for (Entity entityiterator : world.getEntities(entity, new AABB((x - 6.5), (y - 2), (z - 6.5), (x + 6.5), (y + 5), (z + 6.5)))) {
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 6.5) {
					if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
						if (!(entityiterator == (entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null))) {
							continue;
						}
					}
					if (!(entityiterator instanceof LivingEntity)) {
						continue;
					}
					if (IsSameTeamProcedure.execute(entityiterator, entity)) {
						continue;
					}
					DeductPlayerSanityProcedure.execute(entityiterator, daam * 4);
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_magic")))),
							(float) (daam * 0.5));
				}
			}
		}
	}
}
