package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;

import java.util.List;
import java.util.Comparator;

public class BellRangedRIngProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double range) {
		if (entity == null)
			return;
		double damage = 0;
		double r = 0;
		if (IsAlchemyStunnedProcedure.execute(entity)) {
			return;
		}
		r = range;
		damage = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.bell.use")), SoundSource.NEUTRAL, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.bell.use")), SoundSource.NEUTRAL, 1, (float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
			}
		}
		{
			final Vec3 _center = new Vec3(x, y, z);
			List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate((2 * r) / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
			for (Entity entityiterator : _entfound) {
				if (!(entityiterator instanceof LivingEntity)) {
					continue;
				}
				if (entityiterator == entity) {
					continue;
				}
				if (IsValidEnemyForAlchemyProcedure.execute(entityiterator, entity) && (entityiterator != null ? entity.distanceTo(entityiterator) : -1) <= r) {
					entityiterator.invulnerableTime = 0;
					entityiterator.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.MOB_ATTACK), entity), (float) damage);
					if (entityiterator instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.TRAPPED.get(), 10, 0, false, false));
					if (Math.random() < 0.25 && (entityiterator instanceof LivingEntity _livingEntity10 && _livingEntity10.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get())
							? _livingEntity10.getAttribute(CaerulaArborModAttributes.NUMB.get()).getBaseValue()
							: 0) == 0) {
						if (entityiterator instanceof LivingEntity _livingEntity11 && _livingEntity11.getAttributes().hasAttribute(CaerulaArborModAttributes.NUMB.get()))
							_livingEntity11.getAttribute(CaerulaArborModAttributes.NUMB.get()).setBaseValue(1);
					}
				}
			}
		}
	}
}
