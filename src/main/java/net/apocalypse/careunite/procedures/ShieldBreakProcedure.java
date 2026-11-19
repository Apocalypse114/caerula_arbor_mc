package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

public class ShieldBreakProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double time) {
		if (entity == null)
			return;
		if ((entity instanceof LivingEntity _entUseItem0 ? _entUseItem0.getUseItem() : ItemStack.EMPTY).getItem() instanceof ShieldItem) {
			if(entity instanceof Player player) {
	            player.getCooldowns().addCooldown(player.getUseItem().getItem(), (int) time);
	            player.stopUsingItem();
	            player.level().broadcastEntityEvent(player, (byte) 30);
	        }
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.shield.break")), SoundSource.PLAYERS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.shield.break")), SoundSource.PLAYERS, 1, 1, false);
				}
			}
		}
	}
}
