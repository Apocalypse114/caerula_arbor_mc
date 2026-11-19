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

public class WardenRangedProcedure {
	public static void execute(LevelAccessor world, Entity obj, boolean isSonic, double rate, double xx, double yy, double zz) {
		if (obj == null)
			return;
		Entity enemy = null;
		double damage = 0;
		double r = 0;
		enemy = obj instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		r = 3;
		if (isSonic) {
			r = 4.5;
		}
		{
			final Vec3 _center = new Vec3(xx, yy, zz);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((2 * r) / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator == obj) {
					continue;
				}
				if (IsValidEnemyForSeabornProcedure.execute(entityiterator, obj) && new Vec3(xx, yy, zz).distanceTo(new Vec3((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()))) <= r) {
					damage = (obj instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity6.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * rate;
					if (isSonic) {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:warden_sonic"))), obj),
								(float) damage);
						DeductPlayerSanityProcedure.execute(entityiterator, damage * 1.5);
					} else {
						entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:warden_attack"))), obj),
								(float) damage);
					}
				}
			}
		}
	}
}
