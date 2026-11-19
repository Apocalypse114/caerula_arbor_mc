package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.monster.MagmaCube;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class FrozenEfffectProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double dh = 0;
		double dw = 0;
		if (entity instanceof Creeper) {
			if (entity instanceof Creeper _creeper)
				_creeper.setSwellDir(0);
		}
		if (entity instanceof Blaze) {
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FREEZE)), 1);
		}
		if (entity instanceof MagmaCube) {
			entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.FREEZE)), 1);
		}
		dh = entity.getBbHeight() * 0.5;
		dw = entity.getBbWidth() * 0.5;
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.ICE_FLAKE.get()), x, (y + dh), z, 8, dw, dh, dw, 0.05);
		if (world instanceof ServerLevel _level)
			_level.sendParticles(ParticleTypes.ITEM_SNOWBALL, x, (y + dh), z, 2, dw, dh, dw, 0.05);
	}
}
