package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import java.util.List;
import java.util.Comparator;

public class RangedStrengthenedHEalProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double atk = 0;
		double count = 0;
		atk = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:medic_strong")), SoundSource.NEUTRAL, 2, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:medic_strong")), SoundSource.NEUTRAL, 2, 1, false);
			}
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(18 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (IsBatalionSuitProcedure.execute(entityiterator) || entityiterator instanceof Player && IsInquisitionSuitProcedure.execute(entityiterator) > 0.5
						|| entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:homo_sapiens")))
						|| entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:hunters")))) {
					if ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1)) {
						if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 60, 1));
						if ((entityiterator instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
							if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
								_entity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1));
							SingleHealProcedure.execute(world, entityiterator, atk * 2, 0.2);
						} else {
							SingleHealProcedure.execute(world, entityiterator, atk * 2, 0);
						}
						if (!(entity == entityiterator)) {
							count = count + 1;
							if (count >= 7) {
								break;
							}
						}
					}
				}
			}
		}
	}
}
