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

public class SuperCatRangedProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity obj) {
		if (obj == null)
			return;
		Entity enemy = null;
		double damage = 0;
		enemy = obj instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(10 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
					if (!(entityiterator == enemy)) {
						continue;
					}
				}
				if (entityiterator == obj) {
					continue;
				}
				if (new Vec3(x, y, z).distanceTo(new Vec3((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ()))) <= 5) {
					damage = obj instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity9.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:super_cat_attack"))), obj),
							(float) damage);
				}
			}
		}
	}
}
