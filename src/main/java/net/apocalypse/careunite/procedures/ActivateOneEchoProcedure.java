package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

public class ActivateOneEchoProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		double rejection = 0;
		if (itemstack.getOrCreateTag().getBoolean("used")) {
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getOffhandItem() : ItemStack.EMPTY).getItem() == Items.ECHO_SHARD) {
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
			rejection = ChoseEchoNoReptProcedure.execute(entity);
			if (rejection >= 0) {
				SetEchoProcedure.execute(entity, 1600, rejection);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_sensor.clicking_stop")), SoundSource.PLAYERS, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.sculk_sensor.clicking_stop")), SoundSource.PLAYERS, 2, 1, false);
					}
				}
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.SONIC_BOOM, x, (y + 0.5), z, 6, 0.5, 0.5, 0.5, 0.1);
				itemstack.getOrCreateTag().putBoolean("used", true);
			} else {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.echo_starter.full").getString())), true);
			}
		}
	}
}
