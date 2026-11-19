package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModItems;

public class ActivateTwoEchoProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		double rejection = 0;
		if (itemstack.getOrCreateTag().getBoolean("used")) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.MOIST_ECHO_SHARD.get()) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).shrink(1);
				itemstack.getOrCreateTag().putBoolean("used", false);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_sensor.clicking")), SoundSource.PLAYERS, (float) 1.5, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_sensor.clicking")), SoundSource.PLAYERS, (float) 1.5, 1, false);
					}
				}
			}
		} else {
			for (int index0 = 0; index0 < 2; index0++) {
				rejection = ChoseEchoNoReptProcedure.execute(entity);
				if (rejection >= 0) {
					SetEchoProcedure.execute(entity, 2400, rejection);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_sensor.clicking_stop")), SoundSource.PLAYERS, 2, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_sensor.clicking_stop")), SoundSource.PLAYERS, 2, 1, false);
						}
					}
					if (world instanceof ServerLevel _level)
						_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.MOIST_BOOM.get()), x, (y + 0.5), z, 8, 0.5, 0.5, 0.5, 0.1);
					ResetRewardLimitProcedure.execute(entity, rejection, 4800);
					itemstack.getOrCreateTag().putBoolean("used", true);
				}
			}
		}
	}
}
