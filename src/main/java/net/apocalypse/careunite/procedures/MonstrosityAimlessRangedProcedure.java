package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
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

public class MonstrosityAimlessRangedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double rate) {
		if (entity == null)
			return;
		Entity enemy = null;
		double damage = 0;
		double r = 0;
		double tx = 0;
		double tz = 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		r = 4.5;
		damage = (entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * rate;
		tx = x + entity.getLookAngle().x * 4;
		tz = z + entity.getLookAngle().z * 4;
		{
			final Vec3 _center = new Vec3(tx, y, tz);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((2 * r) / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (IsValidEnemyForIreneProcedure.execute(entityiterator, entity)) {
					if (new Vec3(tx, y, tz).distanceTo(new Vec3((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()))) <= r) {
						entityiterator.hurt(
								new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:generic_warrior_attack"))), entity),
								(float) damage);
					}
				}
			}
		}
	}
}
