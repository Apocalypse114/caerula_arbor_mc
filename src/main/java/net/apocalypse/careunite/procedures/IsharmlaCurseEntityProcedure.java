package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class IsharmlaCurseEntityProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double amplifier) {
		if (entity == null)
			return;
		double d = 0;
		double t = 0;
		d = (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.25 * (amplifier + 1);
		entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:isharmla_cursed")))), (float) d);
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.ISHARMLA_CURSE_PARTICLE.get()), x, (y + 0.8), z, 32, 0.8, 0.8, 0.8, 0.1);
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_tear_hurt_0")), SoundSource.HOSTILE, 2, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_tear_hurt_0")), SoundSource.HOSTILE, 2, 1, false);
			}
		}
		t = Mth.nextDouble(RandomSource.create(), 0, 6.283);
		if (entity instanceof Mob _entity)
			_entity.getNavigation().moveTo((x + 2 * Math.sin(t)), y, (z + 2 * Math.cos(t)), 1.5);
	}
}
