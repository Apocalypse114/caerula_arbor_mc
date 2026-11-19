package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.entity.IshmaelEntity;

import java.util.Comparator;

public class OnTrappedTickProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double amplifier) {
		if (entity == null)
			return;
		Entity caster = null;
		entity.setDeltaMovement(new Vec3(0, 0, 0));
		if (entity.tickCount % 10 == 0) {
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.TRAPPED_PARTICLE.get()), x, (y + entity.getBbHeight() * 0.5), z, 2, (entity.getBbWidth() * 0.5), (entity.getBbHeight() * 0.5), (entity.getBbWidth() * 0.5), 0);
		}
		if (entity.tickCount % 20 == 0) {
			if (amplifier > 0) {
				caster = (Entity) world.getEntitiesOfClass(IshmaelEntity.class, AABB.ofSize(new Vec3(x, y, z), 48, 48, 48), e -> true).stream().sorted(new Object() {
					Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
						return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
					}
				}.compareDistOf(x, y, z)).findFirst().orElse(null);
				if (!(caster == null)) {
					entity.invulnerableTime = 0;
					entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ishmael_magic")))),
							(float) ((caster instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity11.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.4));
					entity.invulnerableTime = 0;
				}
			}
		}
	}
}
