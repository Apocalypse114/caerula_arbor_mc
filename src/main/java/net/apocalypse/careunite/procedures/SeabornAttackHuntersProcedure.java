package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.TheLastKnightEntity;
import net.apocalypse.careunite.entity.LastKnightAndHorseEntity;

import java.util.List;
import java.util.Comparator;
import net.minecraft.world.entity.EntityType;

public class SeabornAttackHuntersProcedure {
	public static final TagKey<EntityType<?>> HUNTERS = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:hunters"));
	public static final TagKey<EntityType<?>> INQUISITION = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:inquisition"));
	public static final TagKey<EntityType<?>> HUMAN_SIDE = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:is_humanside"));

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (!(enemy == null) && enemy.isAlive()) {
			return;
		}
		if (entity.getType().is(HUMAN_SIDE)) {
			return;
		}
		if (!(entity instanceof Monster)) {
			return;
		}
		if (entity.tickCount % 40 == 5 && entity instanceof Mob _entity) {
			Vec3 _center = new Vec3(x, y, z);
			List<Mob> _entfound = world.getEntitiesOfClass(Mob.class, new AABB(_center, _center).inflate(48 / 2d), e -> true)
				.stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Mob entityiterator : _entfound) {
				if (IsSameTeamProcedure.execute(entity, entityiterator)) {
					continue;
				}
				if (entityiterator.getType().is(HUNTERS)) {
					_entity.setTarget(entityiterator);
				} else if (entityiterator.getType().is(INQUISITION)) {
					_entity.setTarget(entityiterator);
				} else if (entityiterator instanceof TheLastKnightEntity) {
					_entity.setTarget(entityiterator);
				} else if (entityiterator instanceof LastKnightAndHorseEntity) {
					_entity.setTarget(entityiterator);
				}
			}
		}
	}
}
