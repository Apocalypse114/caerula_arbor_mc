package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import java.util.List;
import java.util.Comparator;

public class RangedCommonHealProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double atk = 0;
		double count = 0;
		double curH = 0;
		double maxH = 0;
		atk = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(12 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (entityiterator instanceof Player && IsInquisitionSuitProcedure.execute(entityiterator) > 0.5 || IsBatalionSuitProcedure.execute(entityiterator)
						|| entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:homo_sapiens")))
						|| entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:hunters")))) {
					if ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 40, 1));
						SingleHealProcedure.execute(world, entityiterator, atk, 0);
						if (!(entityiterator == entity)) {
							count = count + 1;
							if (count >= 3) {
								break;
							}
						}
					}
				}
			}
		}
	}
}
