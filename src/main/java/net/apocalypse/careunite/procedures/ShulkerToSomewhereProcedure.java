package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.OceanizedShulkerEntity;

public class ShulkerToSomewhereProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double dx = 0;
		double dy = 0;
		double dz = 0;
		Direction tDIre = Direction.NORTH;
		for (int index0 = 0; index0 < 5; index0++) {
			dx = Mth.nextInt(RandomSource.create(), -9, 9);
			dy = Mth.nextInt(RandomSource.create(), -9, 9);
			dz = Mth.nextInt(RandomSource.create(), -9, 9);
			if (y + dy < -63) {
				continue;
			}
			tDIre = GetShulkerDirectionProcedure.execute(world, x + dx, y + dy, z + dz);
			if (!(tDIre == null)) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.teleport")), SoundSource.HOSTILE, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.teleport")), SoundSource.HOSTILE, 1, 1, false);
					}
				}
				{
					Entity _ent = entity;
					_ent.teleportTo((x + dx), (y + dy), (z + dz));
					if (_ent instanceof ServerPlayer _serverPlayer)
						_serverPlayer.connection.teleport((x + dx), (y + dy), (z + dz), _ent.getYRot(), _ent.getXRot());
				}
				if (entity instanceof OceanizedShulkerEntity _datEntSetS)
					_datEntSetS.getEntityData().set(OceanizedShulkerEntity.DATA_DIRECTION, tDIre.toString());
				break;
			}
		}
	}
}
