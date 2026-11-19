package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import java.util.List;
import java.util.Comparator;

public class SkadiSpinProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double rrr) {
		if (entity == null)
			return;
		double ddd = 0;
		Entity eee = null;
		eee = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		ddd = (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * rrr;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(6 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof Monster)) {
					if (!(entityiterator == eee || (entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == entity)) {
						continue;
					}
				}
				if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) < 3.5 && IsValidEnemyForIreneProcedure.execute(entityiterator, entity)) {
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hunter_attack"))), entity),
							(float) ddd);
				}
			}
		}
	}
}
