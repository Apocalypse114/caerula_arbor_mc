package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.IshmaelEntity;

public class IshmaelAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity target) {
		if (entity == null || target == null)
			return;
		double ddd = 0;
		double t = 0;
		ddd = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		if (entity instanceof IshmaelEntity _datEntSetI)
			_datEntSetI.getEntityData().set(IshmaelEntity.DATA_BEAM_RENDER_T, 10);
		target.invulnerableTime = 0;
		target.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ishmael_magic"))), entity), (float) ddd);
		t = entity instanceof IshmaelEntity _datEntI ? _datEntI.getEntityData().get(IshmaelEntity.DATA_CHARGED_SPELL) : 0;
		if (target instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.COOLDOWN_SINAL.get(), 5, 0, false, false));
		LinkFlameToProcedure.execute(world, entity.getX() + Mth.nextDouble(RandomSource.create(), -1, 1), entity.getY() + Mth.nextDouble(RandomSource.create(), 1.33, 2.5), entity.getZ() + Mth.nextDouble(RandomSource.create(), -1, 1), target.getX(),
				target.getY() + target.getBbHeight() * 0.5, target.getZ());
		GiveTrappedBuffProcedure.execute(world, x, y, z, entity, target);
		IreneBurnBrandAroundProcedure.execute(world, target.getX(), target.getY(), target.getZ());
		for (int index0 = 0; index0 < (int) t; index0++) {
			target.invulnerableTime = 0;
			target.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ishmael_magic"))), entity), (float) (ddd * 1.25));
			target.invulnerableTime = 0;
			LinkFlameToProcedure.execute(world, entity.getX() + Mth.nextDouble(RandomSource.create(), -1, 1), entity.getY() + Mth.nextDouble(RandomSource.create(), 1.33, 2.5), entity.getZ() + Mth.nextDouble(RandomSource.create(), -1, 1),
					target.getX(), target.getY() + target.getBbHeight() * 0.5, target.getZ());
			GiveTrappedBuffProcedure.execute(world, x, y, z, entity, target);
		}
		if (!target.isAlive()) {
			NearestEnemyForIshmaelProcedure.execute(world, target, entity, ddd, t + 1);
		}
		if (entity instanceof IshmaelEntity _datEntSetI)
			_datEntSetI.getEntityData().set(IshmaelEntity.DATA_CHARGED_SPELL, 0);
		if ((entity instanceof IshmaelEntity _datEntI ? _datEntI.getEntityData().get(IshmaelEntity.DATA_SKILL_P) : 0) < 0) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:indigo_hit_critical")), SoundSource.NEUTRAL, (float) 2.5,
							(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:indigo_hit_critical")), SoundSource.NEUTRAL, (float) 2.5, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
		} else if (Math.random() < 0.5) {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:indigo_hit")), SoundSource.NEUTRAL, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:indigo_hit")), SoundSource.NEUTRAL, 2, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
				}
			}
		}
	}
}
