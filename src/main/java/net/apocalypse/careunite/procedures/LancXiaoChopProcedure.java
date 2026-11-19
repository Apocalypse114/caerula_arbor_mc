package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class LancXiaoChopProcedure {
	public static void execute(LevelAccessor world, Entity ene, Entity entity) {
		if (ene == null || entity == null)
			return;
		double atk = 0;
		double tz = 0;
		double ty = 0;
		double tx = 0;
		atk = entity instanceof LivingEntity _livingEntity0 && _livingEntity0.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity0.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		tx = ene.getX() + Mth.nextDouble(RandomSource.create(), -0.25, 0.25);
		ty = ene.getY();
		tz = ene.getZ() + Mth.nextDouble(RandomSource.create(), -0.25, 0.25);
		EndspeakerLinkPtcToProcedure.execute(world, entity.getX(), entity.getY(), entity.getZ(), tx, ty, tz);
		{
			Entity _ent = entity;
			_ent.teleportTo(tx, ty, tz);
			if (_ent instanceof ServerPlayer _serverPlayer)
				_serverPlayer.connection.teleport(tx, ty, tz, _ent.getYRot(), _ent.getXRot());
		}
		if (world instanceof ServerLevel _level)
			_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.ENDSPEAKER_PARTICLE.get()), tx, (ty + 0.75), tz, 18, 0.75, 0.75, 0.75, 0.15);
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(tx, ty, tz), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:endspeaker_attack_hit")), SoundSource.PLAYERS, (float) 1.5, 1);
			} else {
				_level.playLocalSound(tx, ty, tz, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:endspeaker_attack_hit")), SoundSource.PLAYERS, (float) 1.5, 1, false);
			}
		}
		ene.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:endspeaker_attack"))), entity), (float) (atk * 2));
	}
}
