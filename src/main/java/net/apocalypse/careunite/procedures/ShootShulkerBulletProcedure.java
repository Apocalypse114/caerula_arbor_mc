package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.entity.LivingEntity;
import net.apocalypse.careunite.entity.OceanizedShulkerEntity;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction;

public class ShootShulkerBulletProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity target) {
		if (entity == null || target == null)
			return;
		if (entity instanceof OceanizedShulkerEntity living && world instanceof ServerLevel _level) {
			Direction dire = living.getAttachDirection();
			Direction.Axis axis = dire != null ? dire.getAxis() : Direction.Axis.Y;
			ShulkerBullet sBullet = new ShulkerBullet(_level, living, target, axis);
			sBullet.getPersistentData().putBoolean("oceanized", true);
			_level.addFreshEntity(sBullet);
		}
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.shoot")), SoundSource.HOSTILE, 1, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.shulker.shoot")), SoundSource.HOSTILE, 1, 1, false);
			}
		}
	}
}
