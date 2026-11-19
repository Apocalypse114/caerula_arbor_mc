package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class TeleportToProcedure {
	public static void execute(LevelAccessor world, Entity obj, double fromX, double fromY, double fromZ, double toX, double toY, double toZ) {
		if (obj == null)
			return;
		double vx = 0;
		double vy = 0;
		double vz = 0;
		double size = 0;
		if (obj.isAlive()) {
			vx = toX - fromX;
			vy = toY - fromY;
			vz = toZ - fromZ;
			size = Math.max(Math.min(Math.round(Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2) + Math.pow(vz, 2))), 32), 1);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(fromX, fromY, fromZ), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")), SoundSource.HOSTILE, 1, 1);
				} else {
					_level.playLocalSound(fromX, fromY, fromZ, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.enderman.teleport")), SoundSource.HOSTILE, 1, 1, false);
				}
			}
			for (int index0 = 0; index0 < (int) size; index0++) {
				if (world instanceof ServerLevel _level)
					_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.EDERMAN_PTC.get()), (fromX + (vx / size) * index0), (fromY + (vy / size) * index0 + 0.65), (fromZ + (vz / size) * index0), 32, 0.65, 0.65, 0.65, 0.05);
			}
			{
				Entity _ent = obj;
				_ent.teleportTo(toX, toY, toZ);
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport(toX, toY, toZ, _ent.getYRot(), _ent.getXRot());
			}
			obj.clearFire();
		}
	}
}
