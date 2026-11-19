package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.CaerulaArborMod;

public class IreneAttackFlyProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity enemy, Entity entity, double rate, double time) {
		if (enemy == null || entity == null)
			return;
		enemy.push(0, 0.4, 0);
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.FIREWORK, (enemy.getX()), (enemy.getY() + 1), (enemy.getZ()), 48, 0.15, 1, 0.15, 0.15);
		if (enemy instanceof LivingEntity _entity && !_entity.level().isClientSide())
			_entity.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, (int) time, 0));
		enemy.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hunter_attack"))), entity),
				(float) ((entity instanceof LivingEntity _livingEntity6 && _livingEntity6.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity6.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * rate));
		CaerulaArborMod.queueServerWork(6, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_gun")), SoundSource.NEUTRAL, 3, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_gun")), SoundSource.NEUTRAL, 3, 1, false);
				}
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.END_ROD, (enemy.getX()), (enemy.getY() + 0.75), (enemy.getZ()), 32, 0.75, 0.75, 0.75, 0.15);
			enemy.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hunter_attack"))), entity),
					(float) ((entity instanceof LivingEntity _livingEntity14 && _livingEntity14.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity14.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0) * 2));
		});
	}
}
