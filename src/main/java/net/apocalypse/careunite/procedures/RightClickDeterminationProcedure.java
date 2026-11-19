package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class RightClickDeterminationProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		if (blockstate.getBlock() == CaerulaArborModBlocks.MIZUKI_STATUE.get()) {
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.mizuki_determination.note").getString())), true);
		} else if (blockstate.getBlock() == CaerulaArborModBlocks.MIZUKI_DOLL.get()) {
			if (!CaerulaArborModVariables.MapVariables.get(world).if_sublimation) {
				CaerulaArborModVariables.MapVariables.get(world).if_sublimation = true;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				if (!world.isClientSide() && world.getServer() != null)
					world.getServer().getPlayerList().broadcastSystemMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.mizuki_determination.activate").getString())), false);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:sublimation_trigger")), SoundSource.BLOCKS, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:sublimation_trigger")), SoundSource.BLOCKS, 2, 1, false);
					}
				}
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.FIREWORK, (x + 0.5), (y + 0.5), (z + 0.5), 16, 0.75, 0.75, 0.75, 0.1);
				return InteractionResult.SUCCESS;
			}
		}
		return InteractionResult.PASS;
	}
}
