package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class EndermiteTeleportSeabornProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double landingX = 0;
		double landingY = 0;
		double landingZ = 0;
		Entity target = null;
		landingX = (Math.random() - 0.5) * 1.5;
		landingY = (Math.random() - 0.5) * 0.5;
		landingZ = (Math.random() - 0.5) * 1.5;
		world.addParticle((SimpleParticleType) (CaerulaArborModParticleTypes.EDERMAN_PTC.get()), (x + landingX), (y + 0.175 + landingY), (z + landingZ), ((-0.1) * landingX), ((-0.1) * landingY), ((-0.1) * landingZ));
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.deathTime : 0) == 15) {
			target = GetLeastHealthSeabornProcedure.execute(world, x, y, z, entity);
			if (!(target == null)) {
				{
					Entity _ent = target;
					_ent.teleportTo(x, y, z);
					if (_ent instanceof ServerPlayer _serverPlayer)
						_serverPlayer.connection.teleport(x, y, z, _ent.getYRot(), _ent.getXRot());
				}
				target.setDeltaMovement(new Vec3(0, 0, 0));
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.chorus_fruit.teleport")), SoundSource.HOSTILE, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.chorus_fruit.teleport")), SoundSource.HOSTILE, 2, 1, false);
					}
				}
			}
		}
	}
}
