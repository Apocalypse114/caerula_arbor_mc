package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.CaerulaArborMod;

public class GladiiaDuelPullProcedure {
	public static void execute(LevelAccessor world, Entity ene, Entity entity) {
		if (ene == null || entity == null)
			return;
		Entity side = null;
		double damage = 0;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(ene.getX(), ene.getY(), ene.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_pull_pull")), SoundSource.NEUTRAL, 3, 1);
			} else {
				_level.playLocalSound((ene.getX()), (ene.getY()), (ene.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_pull_pull")), SoundSource.NEUTRAL, 3, 1, false);
			}
		}
		PullToGladiiaProcedure.execute(ene, entity);
		GladiiaLinkPtcToEntityProcedure.execute(world, entity, ene);
		damage = entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity4.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		ene.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hunter_attack"))), entity), (float) (damage * 3));
		side = CatchNearestEnemyProcedure.execute(world, ene.getX(), ene.getY(), ene.getZ(), ene);
		if (!(side == null)) {
			PullToGladiiaProcedure.execute(side, entity);
			GladiiaLinkPtcToEntityProcedure.execute(world, entity, side);
			side.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:hunter_attack"))), entity), (float) (damage * 3));
		}
		CaerulaArborMod.queueServerWork(10, () -> {
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(ene.getX(), ene.getY(), ene.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_attack_pre")), SoundSource.NEUTRAL, 3, 1);
				} else {
					_level.playLocalSound((ene.getX()), (ene.getY()), (ene.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:gladiia_attack_pre")), SoundSource.NEUTRAL, 3, 1, false);
				}
			}
			if (ene instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DIZZY.get(), 40, 0, false, false));
			if (CatchNearestEnemyProcedure.execute(world, ene.getX(), ene.getY(), ene.getZ(), ene) instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.DIZZY.get(), 40, 0, false, false));
		});
	}
}
