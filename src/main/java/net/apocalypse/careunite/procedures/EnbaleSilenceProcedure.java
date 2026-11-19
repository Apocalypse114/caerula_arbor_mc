package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;

public class EnbaleSilenceProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		if (!CaerulaArborModVariables.MapVariables.get(world).silence_enabled) {
			CaerulaArborModVariables.MapVariables.get(world).silence_enabled = true;
			CaerulaArborModVariables.MapVariables.get(world).syncData(world);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.end_portal.spawn")), SoundSource.PLAYERS, 4, (float) 0.85);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.end_portal.spawn")), SoundSource.PLAYERS, 4, (float) 0.85, false);
				}
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.MOIST_BOOM.get()), x, (y + 2), z, 32, 2, 2, 2, 0.33);
			if (!world.isClientSide() && world.getServer() != null)
				world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(((Component.translatable("item.caerula_arbor_reunite.leviathan_animus.broadcast").getString()).replace("<player>", entity.getDisplayName().getString()))), false);
			if (!world.isClientSide() && world.getServer() != null)
				world.getServer().getPlayerList().broadcastSystemMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.language_key.description_14").getString())), false);
			itemstack.shrink(1);
		} else {
			if (CaerulaArborModVariables.MapVariables.get(world).strategy_silence < 4 && IfCanSilenceProcedure.execute(world)) {
				if (!world.isClientSide() && world.getServer() != null)
					world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(((Component.translatable("item.caerula_arbor_reunite.leviathan_animus.broadcast").getString()).replace("<player>", entity.getDisplayName().getString()))), false);
				UpgradeSilenceProcedure.execute(world, 99999999);
				itemstack.shrink(1);
			} else {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.candle.extinguish")), SoundSource.PLAYERS, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.candle.extinguish")), SoundSource.PLAYERS, 2, 1, false);
					}
				}
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.ASH, x, (y + 2), z, 64, 2, 2, 2, 0.33);
			}
		}
	}
}
