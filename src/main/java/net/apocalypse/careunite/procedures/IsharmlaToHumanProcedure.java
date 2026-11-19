package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.IsharmlaEntity;

public class IsharmlaToHumanProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return false;
		double p = 0;
		double mH = 0;
		if (entity instanceof IsharmlaEntity _datEntL0 && _datEntL0.getEntityData().get(IsharmlaEntity.DATA_IS_MONSTER)) {
			IsharmlaToHstyleProcedure.execute(world, x, y, z);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_to_human")), SoundSource.HOSTILE, 3, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_to_human")), SoundSource.HOSTILE, 3, 1, false);
				}
			}
			p = entity instanceof IsharmlaEntity _datEntI ? _datEntI.getEntityData().get(IsharmlaEntity.DATA_RECORDED_HEALTH) : 0;
			mH = entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1;
			if (entity instanceof IsharmlaEntity) {
				((IsharmlaEntity) entity).setAnimation("animation.isharmla.to_human");
			}
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(MobEffects.MOVEMENT_SPEED);
			if (entity instanceof IsharmlaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_DURATION, 40);
			if (entity instanceof IsharmlaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_SKILLP_1, 2400);
			if (entity instanceof IsharmlaEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IsharmlaEntity.DATA_SKILLP_2, 280);
			if (entity instanceof IsharmlaEntity _datEntSetL)
				_datEntSetL.getEntityData().set(IsharmlaEntity.DATA_IS_MONSTER, false);
			if (entity instanceof LivingEntity _entity)
				_entity.removeEffect(CaerulaArborModMobEffects.INVULNERABLE.get());
			if (entity instanceof LivingEntity _entity)
				_entity.setHealth((float) Math.max(mH * Math.min(p * 0.001 + 0.03, 1), 1));
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 40, 9, false, false));
			return true;
		}
		return false;
	}
}
