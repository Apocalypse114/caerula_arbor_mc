package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.OceanIllusionEntity;

import java.util.Comparator;

public class SwapWithIllusionProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return false;
		Entity illusion = null;
		if (entity.isPassenger()) {
			return false;
		}
		if (Math.random() < 0.75) {
			illusion = (Entity) world.getEntitiesOfClass(OceanIllusionEntity.class, AABB.ofSize(new Vec3(x, y, z), 48, 48, 48), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null);
			if (illusion == null) {
				return false;
			}
			if (!illusion.isAlive()) {
				return false;
			}
			{
				Entity _ent = entity;
				_ent.teleportTo((illusion.getX()), (illusion.getY()), (illusion.getZ()));
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport((illusion.getX()), (illusion.getY()), (illusion.getZ()), _ent.getYRot(), _ent.getXRot());
			}
			{
				Entity _ent = illusion;
				_ent.teleportTo(x, y, z);
				if (_ent instanceof ServerPlayer _serverPlayer)
					_serverPlayer.connection.teleport(x, y, z, _ent.getYRot(), _ent.getXRot());
			}
			if (!world.isClientSide()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.mirror_move")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.illusioner.mirror_move")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
			}
			return true;
		}
		return false;
	}
}
