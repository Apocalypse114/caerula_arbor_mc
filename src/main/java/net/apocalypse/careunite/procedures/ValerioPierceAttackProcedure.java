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

import java.util.List;
import java.util.Comparator;

public class ValerioPierceAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double r) {
		if (entity == null)
			return;
		double atk = 0;
		atk = (entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 1.3;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((2 * r) / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= 3 || GetEntityCosineProcedure.execute(entity, entityiterator) >= 0.5) {
					if (IsValidEnemyForAlchemyProcedure.execute(entityiterator, entity)) {
						entityiterator.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:generic_warrior_attack"))), entity),
								(float) atk);
						if (world instanceof ServerLevel _level)
							_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.PRESAINT_HIT.get()), (entityiterator.getX()), (entityiterator.getY() + entityiterator.getBbHeight() * 0.5), (entityiterator.getZ()), 1, 0.2, 0.2, 0.2,
									0);
					}
				}
			}
		}
	}
}
