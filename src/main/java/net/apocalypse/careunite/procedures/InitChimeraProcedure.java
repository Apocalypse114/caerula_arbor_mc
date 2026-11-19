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
import net.apocalypse.careunite.entity.TideChimeraEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class InitChimeraProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 100, 9, false, false));
		if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get()))
			_livingEntity2.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
					.setBaseValue(((entity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(CaerulaArborModAttributes.SANITY_RATE.get())
							? _livingEntity1.getAttribute(CaerulaArborModAttributes.SANITY_RATE.get()).getBaseValue()
							: 0) + 4));
		if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(CaerulaArborModAttributes.MISSRATE.get()))
			_livingEntity3.getAttribute(CaerulaArborModAttributes.MISSRATE.get()).setBaseValue(50);
		if (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity4.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).setBaseValue(45);
		if (entity instanceof TideChimeraEntity) {
			((TideChimeraEntity) entity).setAnimation("animation.super_apocata.start");
		}
		CaerulaArborMod.queueServerWork(36, () -> {
			if (entity.isAlive()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.land")), SoundSource.HOSTILE, (float) 2.5, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.anvil.land")), SoundSource.HOSTILE, (float) 2.5, 1, false);
					}
				}
			}
		});
		CaerulaArborMod.queueServerWork(60, () -> {
			if (entity.isAlive()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.slime.jump")), SoundSource.HOSTILE, (float) 2.5, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.slime.jump")), SoundSource.HOSTILE, (float) 2.5, 1, false);
					}
				}
			}
		});
		CaerulaArborMod.queueServerWork(66, () -> {
			if (entity.isAlive()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.slime.jump")), SoundSource.HOSTILE, (float) 2.5, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.slime.jump")), SoundSource.HOSTILE, (float) 2.5, 1, false);
					}
				}
			}
		});
		CaerulaArborMod.queueServerWork(83, () -> {
			if (entity.isAlive()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.place")), SoundSource.HOSTILE, (float) 2.5, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.slime_block.place")), SoundSource.HOSTILE, (float) 2.5, 1, false);
					}
				}
			}
		});
	}
}
