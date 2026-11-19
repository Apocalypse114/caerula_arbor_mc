package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import java.util.List;
import java.util.Comparator;

public class PolarRangedProcedure {
	public static void execute(LevelAccessor world, Entity sourceentity) {
		if (sourceentity == null)
			return;
		Entity enemy = null;
		double damage = 0;
		double r = 0;
		enemy = sourceentity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		r = 3;
		damage = (sourceentity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.6;
		{
			final Vec3 _center = new Vec3((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()));
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((2 * r) / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
					if (!(entityiterator == enemy)) {
						continue;
					}
				}
				if (entityiterator == sourceentity) {
					continue;
				}
				if ((sourceentity != null ? entityiterator.distanceTo(sourceentity) : -1) <= r) {
					entityiterator.hurt(
							new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), sourceentity),
							(float) damage);
					entityiterator.push(0, 0.25, 0);
				}
			}
		}
	}
}
