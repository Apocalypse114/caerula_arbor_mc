package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;

import java.util.List;
import java.util.Comparator;

public class ValerioElementalBurnProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double a) {
		if (entity == null)
			return;
		double atk = 0;
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.SOUL_FIRE_FLAME, x, (y + 0.1), z, 2, a, 0.1, a, 0.1);
		if (entity.tickCount % 20 == 10) {
			atk = (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity2.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.3;
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((2 * a) / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (IsValidEnemyForAlchemyProcedure.execute(entityiterator, entity)) {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MAGIC)), (float) atk);
						if (!DealZhixuBurnProcedure.execute(entityiterator, 50)) {
							DoElementalBurnProcedure.execute(world, entityiterator, 35);
						}
					}
				}
			}
		}
	}
}
