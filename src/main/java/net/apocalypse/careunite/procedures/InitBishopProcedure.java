package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.BishopFishEntity;

public class InitBishopProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof BishopFishEntity _datEntSetI)
			_datEntSetI.getEntityData().set(BishopFishEntity.DATA_locx, (int) Math.round(x));
		if (entity instanceof BishopFishEntity _datEntSetI)
			_datEntSetI.getEntityData().set(BishopFishEntity.DATA_locy, (int) Math.round(y));
		if (entity instanceof BishopFishEntity _datEntSetI)
			_datEntSetI.getEntityData().set(BishopFishEntity.DATA_locz, (int) Math.round(z));
		if (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get()))
			_livingEntity4.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
							? _livingEntity3.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get()).getBaseValue()
							: 0) + 10));
		if (entity instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity5.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).setBaseValue(24);
		entity.setNoGravity(true);
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 80, 1, false, false));
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.emerge")), SoundSource.HOSTILE, 3, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.warden.emerge")), SoundSource.HOSTILE, 3, 1, false);
			}
		}
		if (entity instanceof BishopFishEntity) {
			((BishopFishEntity) entity).setAnimation("animation.bishop.start1");
		}
	}
}
