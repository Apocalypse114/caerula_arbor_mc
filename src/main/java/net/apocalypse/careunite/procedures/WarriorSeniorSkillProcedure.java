package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.WarriorPriestEntity;

import java.util.List;
import java.util.Comparator;

public class WarriorSeniorSkillProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double sklp1 = 0;
		double skillp = 0;
		if (entity.isAlive()) {
			skillp = entity instanceof WarriorPriestEntity _datEntI ? _datEntI.getEntityData().get(WarriorPriestEntity.DATA_skill_p) : 0;
			sklp1 = entity instanceof WarriorPriestEntity _datEntI ? _datEntI.getEntityData().get(WarriorPriestEntity.DATA_skill_p1) : 0;
			if (skillp > 0) {
				if (entity instanceof WarriorPriestEntity _datEntSetI)
					_datEntSetI.getEntityData().set(WarriorPriestEntity.DATA_skill_p, (int) (skillp - 1));
			}
			if (sklp1 > 0) {
				if (entity instanceof WarriorPriestEntity _datEntSetI)
					_datEntSetI.getEntityData().set(WarriorPriestEntity.DATA_skill_p1, (int) (sklp1 - 1));
			} else {
				if (entity instanceof WarriorPriestEntity) {
					((WarriorPriestEntity) entity).setAnimation("animation.warriorpriest.givebuff");
				}
				if (entity instanceof WarriorPriestEntity _datEntSetI)
					_datEntSetI.getEntityData().set(WarriorPriestEntity.DATA_skill_p1, 400);
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(16 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:homo_sapiens")))) {
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 0));
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 0));
						}
					}
				}
			}
		}
	}
}
