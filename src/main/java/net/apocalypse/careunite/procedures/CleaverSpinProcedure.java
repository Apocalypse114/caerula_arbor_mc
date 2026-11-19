package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import java.util.List;
import java.util.Comparator;

public class CleaverSpinProcedure {
	public static void execute(LevelAccessor world, Entity entity, double xx, double yy, double zz) {
		if (entity == null)
			return;
		{
			final Vec3 _center = new Vec3(xx, yy, zz);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(5 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator == entity) {
					continue;
				}
				if ((entityiterator instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) == entity) {
					continue;
				}
				if (!(entityiterator instanceof Mob)) {
					continue;
				}
				if (entityiterator instanceof Player) {
					if (!IsRecentlyHurtProcedure.execute(entity, entityiterator)) {
						continue;
					}
				}
				if (new Vec3((entityiterator.getX()), (entityiterator.getY()), (entityiterator.getZ())).distanceTo(new Vec3(xx, yy, zz)) <= 2.5) {
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:cleaver_mix"))), entity),
							(float) ((entity instanceof LivingEntity _livingEntity9 && _livingEntity9.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity9.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 0.8));
				}
			}
		}
	}
}
