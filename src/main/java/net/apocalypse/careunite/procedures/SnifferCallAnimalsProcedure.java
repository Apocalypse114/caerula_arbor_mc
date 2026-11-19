package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import java.util.List;
import java.util.Comparator;

public class SnifferCallAnimalsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double res = 0;
		double detail = 0;
		double atk = 0;
		ItemStack result = ItemStack.EMPTY;
		Entity eee = null;
		eee = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (eee == null) {
			return;
		}
		if (eee.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:animal_seaborn")))) {
			return;
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(48 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator == entity) {
					continue;
				}
				if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:animal_seaborn")))) {
					if (entityiterator instanceof Mob _entity && eee instanceof LivingEntity _ent)
						_entity.setTarget(_ent);
				}
			}
		}
	}
}
