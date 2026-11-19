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

public class DireswineDashProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity obj, double R, double rate) {
		if (obj == null)
			return;
		Entity enemy = null;
		double d = 0;
		String uuid = "";
		enemy = obj instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		d = (obj instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * rate;
		uuid = obj.getStringUUID();
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((R * 2) / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator == obj) {
					continue;
				}
				if (entityiterator == enemy) {
					continue;
				}
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
					if (!(entityiterator == enemy || (entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == obj)) {
						continue;
					}
				}
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if ((uuid).equals(entityiterator.getPersistentData().getString("caerula_lastHurtByDireswine"))) {
					continue;
				}
				if ((entityiterator != null ? obj.distanceTo(entityiterator) : -1) <= R) {
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack"))), obj),
							(float) d);
					entityiterator.push(0, 0.64, 0);
					entityiterator.getPersistentData().putString("caerula_lastHurtByDireswine", uuid);
				}
			}
		}
	}
}
