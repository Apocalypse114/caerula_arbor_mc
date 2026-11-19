package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.SkadiCorruptedEntity;

import java.util.ArrayList;

public class SkadiCorruptedSkillsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double dura = 0;
		double conv = 0;
		double deal = 0;
		double phase = 0;
		double converT = 0;
		double gap = 0;
		double nn = 0;
		converT = entity instanceof SkadiCorruptedEntity _datEntI ? _datEntI.getEntityData().get(SkadiCorruptedEntity.DATA_convertTick) : 0;
		if (converT < 999) {
			if (converT > 0) {
				if (entity instanceof SkadiCorruptedEntity _datEntSetL)
					_datEntSetL.getEntityData().set(SkadiCorruptedEntity.DATA_mayCorrupt, false);
				if (entity instanceof SkadiCorruptedEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_convertTick, (int) (converT - 1));
			} else {
				if (phase < 0.5) {
					for (Entity entityiterator : new ArrayList<>(world.players())) {
						if ((entityiterator != null ? entity.distanceTo(entityiterator) : -1) < 32) {
							if (entityiterator instanceof Player _player && !_player.level().isClientSide())
								_player.displayClientMessage(Component.literal((Component.translatable("entity.caerula_arbor_reunite.skadi_corrupted.convert").getString())), false);
						}
					}
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.TEAR_ISHARMLA.get()));
						entityToSpawn.setPickUpDelay(10);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.INCANDESCENT_ANIMA.get()));
						entityToSpawn.setPickUpDelay(10);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
					if (world instanceof ServerLevel _level) {
						ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, new ItemStack(CaerulaArborModItems.RECORD_UNDERTIDES.get()));
						entityToSpawn.setPickUpDelay(10);
						entityToSpawn.setUnlimitedLifetime();
						_level.addFreshEntity(entityToSpawn);
					}
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:corrupted_convert")), SoundSource.HOSTILE, 2, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:corrupted_convert")), SoundSource.HOSTILE, 2, 1, false);
						}
					}
					if (entity instanceof SkadiCorruptedEntity _datEntSetL)
						_datEntSetL.getEntityData().set(SkadiCorruptedEntity.DATA_mayCorrupt, false);
					if (!entity.level().isClientSide())
						entity.discard();
					SummonHurtSkadiProcedure.execute(world, x, y, z);
				}
			}
		}
		if (!entity.isAlive()) {
			return;
		}
		if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20, 0, false, false));
		conv = entity instanceof SkadiCorruptedEntity _datEntI ? _datEntI.getEntityData().get(SkadiCorruptedEntity.DATA_convertP) : 0;
		dura = entity instanceof SkadiCorruptedEntity _datEntI ? _datEntI.getEntityData().get(SkadiCorruptedEntity.DATA_duration) : 0;
		deal = entity instanceof SkadiCorruptedEntity _datEntI ? _datEntI.getEntityData().get(SkadiCorruptedEntity.DATA_deal) : 0;
		phase = entity instanceof SkadiCorruptedEntity _datEntI ? _datEntI.getEntityData().get(SkadiCorruptedEntity.DATA_phase) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (dura > 0) {
			if (entity instanceof SkadiCorruptedEntity _datEntSetI)
				_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_duration, (int) (dura - 1));
		}
		if (conv > 0) {
			if (phase < 1.9) {
				if (entity instanceof SkadiCorruptedEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_convertP, (int) (conv - 1));
			}
		} else if (dura <= 0) {
			if (phase < 0.5) {
				if (entity instanceof SkadiCorruptedEntity) {
					((SkadiCorruptedEntity) entity).setAnimation("animation.skadi_corrupted.to_phase_2");
				}
				if (entity instanceof SkadiCorruptedEntity animatable)
					animatable.setTexture("skadi_corrupted_1");
				if (entity instanceof SkadiCorruptedEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_convertP, 1120);
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
				if (entity instanceof SkadiCorruptedEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_phase, 1);
				if (entity instanceof SkadiCorruptedEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_deal, 0);
				if (entity instanceof SkadiCorruptedEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_duration, 60);
				if (entity instanceof LivingEntity _livingEntity31 && _livingEntity31.getAttributes().hasAttribute(Attributes.ARMOR))
					_livingEntity31.getAttribute(Attributes.ARMOR)
							.setBaseValue(((entity instanceof LivingEntity _livingEntity30 && _livingEntity30.getAttributes().hasAttribute(Attributes.ARMOR) ? _livingEntity30.getAttribute(Attributes.ARMOR).getBaseValue() : 0) * 1.5));
				if (entity instanceof SkadiCorruptedEntity _datEntSetL)
					_datEntSetL.getEntityData().set(SkadiCorruptedEntity.DATA_mayCorrupt, true);
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 60, 9, false, false));
				if (!world.isClientSide()) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:silence3")), SoundSource.HOSTILE, 2, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:silence3")), SoundSource.HOSTILE, 2, 1, false);
						}
					}
				}
			} else if (phase < 1.5) {
				if (entity instanceof SkadiCorruptedEntity) {
					((SkadiCorruptedEntity) entity).setAnimation("animation.skadi_corrupted.to_phase_3");
				}
				if (entity instanceof SkadiCorruptedEntity animatable)
					animatable.setTexture("skadi_corrupted");
				if (entity instanceof SkadiCorruptedEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_convertP, 99999);
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1);
				if (entity instanceof SkadiCorruptedEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_phase, 2);
				if (entity instanceof SkadiCorruptedEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_deal, 0);
				if (entity instanceof SkadiCorruptedEntity _datEntSetI)
					_datEntSetI.getEntityData().set(SkadiCorruptedEntity.DATA_duration, 80);
				if (entity instanceof LivingEntity _livingEntity45 && _livingEntity45.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
					_livingEntity45.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
							.setBaseValue(((entity instanceof LivingEntity _livingEntity44 && _livingEntity44.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
									? _livingEntity44.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getBaseValue()
									: 0) + 50));
				if (entity instanceof LivingEntity _livingEntity47 && _livingEntity47.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
					_livingEntity47.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(
							((entity instanceof LivingEntity _livingEntity46 && _livingEntity46.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity46.getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue() : 0) * 1.25));
				if (entity instanceof SkadiCorruptedEntity _datEntSetL)
					_datEntSetL.getEntityData().set(SkadiCorruptedEntity.DATA_mayCorrupt, true);
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 80, 9, false, false));
				if (!world.isClientSide()) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:silence4")), SoundSource.HOSTILE, 2, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:silence4")), SoundSource.HOSTILE, 2, 1, false);
						}
					}
				}
			}
		}
		if (phase < 0.5) {
			gap = 300;
			nn = 3;
		} else if (phase < 1.5) {
			gap = 360;
			nn = 4;
		} else {
			gap = 300;
			nn = 5;
			if (entity.tickCount % 20 == 5) {
				CorruptedRangedHurtProcedure.execute(world, x, y, z, entity);
			}
			SkadiCorruptedRimProcedure.execute(world, x, y, z, entity);
		}
		if (entity.tickCount % 20 == 10) {
			CorruptedPhaseBonusProcedure.execute(world, x, y, z, entity, phase);
		}
		if (entity.tickCount % gap == 99) {
			assert Boolean.TRUE; //#dbg:SkadiCorruptedSkills:corruptedSpawnCheck
			CorruptedSpawnMobsProcedure.execute(world, x, y, z, entity, nn);
		}
		SkadiCorruptedParticlesProcedure.execute(world, x, y, z, entity);
		if (phase > 1.5) {
			return;
		}
		if (deal >= (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.75) {
			if (entity instanceof SkadiCorruptedEntity _datEntSetL)
				_datEntSetL.getEntityData().set(SkadiCorruptedEntity.DATA_mayCorrupt, false);
		} else {
			if (entity instanceof SkadiCorruptedEntity _datEntSetL)
				_datEntSetL.getEntityData().set(SkadiCorruptedEntity.DATA_mayCorrupt, true);
		}
	}
}
