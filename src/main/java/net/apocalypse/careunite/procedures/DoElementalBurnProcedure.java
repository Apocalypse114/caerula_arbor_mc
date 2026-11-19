package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class DoElementalBurnProcedure {
	public static void execute(LevelAccessor world, Entity entity, double amount) {
		if (entity == null)
			return;
		if (world.isClientSide()) {
			return;
		}
		if (entity.fireImmune() || entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(MobEffects.FIRE_RESISTANCE)) {
			return;
		}
		int burningT = entity.getRemainingFireTicks();
		if (burningT < 500) {
			entity.setRemainingFireTicks(burningT + (int)amount);
		} else {
			entity.clearFire();
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.NEUTRAL, 1,
							(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2));
				} else {
					_level.playLocalSound((entity.getX()), (entity.getY()), (entity.getZ()), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.NEUTRAL, 1,
							(float) Mth.nextDouble(RandomSource.create(), 0.8, 1.2), false);
				}
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.EW_FLAME.get()), (entity.getX()), (entity.getY() + 0.75), (entity.getZ()), 32, 0.75, 0.75, 0.75, 0.1);
			entity.invulnerableTime = 0;
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:ocean_real")))), 24);
		}
	}
}
