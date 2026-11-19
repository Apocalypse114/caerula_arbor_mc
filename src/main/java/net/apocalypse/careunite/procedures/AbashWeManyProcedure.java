package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModParticleTypes;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

public class AbashWeManyProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return InteractionResult.PASS;
		String stra = "";
		String info = "";
		double target = 0;
		double p0 = 0;
		double p1 = 0;
		double p2 = 0;
		double p3 = 0;
		double l1 = 0;
		double l2 = 0;
		double l3 = 0;
		double l0 = 0;
		if (blockstate.getBlock() == CaerulaArborModBlocks.TIDE_OBSERVATION.get() && !world.isClientSide()) {
			p0 = CaerulaArborModVariables.MapVariables.get(world).evo_point_grow;
			p1 = CaerulaArborModVariables.MapVariables.get(world).evo_point_subsisting;
			p2 = CaerulaArborModVariables.MapVariables.get(world).evo_point_breed;
			p3 = CaerulaArborModVariables.MapVariables.get(world).evo_point_migration;
			l0 = CaerulaArborModVariables.MapVariables.get(world).strategy_grow;
			l1 = CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting;
			l2 = CaerulaArborModVariables.MapVariables.get(world).strategy_breed;
			l3 = CaerulaArborModVariables.MapVariables.get(world).strategy_migration;
			if (l0 + l1 + l2 + l3 >= 16) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.dictationless_chapter.late").getString())), true);
				return InteractionResult.PASS;
			}
			if (p0 + p1 + p2 + p3 == 0) {
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal((Component.translatable("item.caerula_arbor_reunite.dictationless_chapter.fail").getString())), true);
				return InteractionResult.PASS;
			}
			target = Mth.nextInt(RandomSource.create(), 0, 3);
			for (int index0 = 0; index0 < 32; index0++) {
				if (l0 < 4 && target == 0 && p0 > 0) {
					break;
				}
				if (l1 < 4 && target == 1 && p1 > 0) {
					break;
				}
				if (l2 < 4 && target == 2 && p2 > 0) {
					break;
				}
				if (l3 < 4 && target == 3 && p3 > 0) {
					break;
				}
				target = Mth.nextInt(RandomSource.create(), 0, 4);
			}
			if (target == 0) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:grow1")), SoundSource.PLAYERS, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:grow1")), SoundSource.PLAYERS, 1, 1, false);
					}
				}
				CaerulaArborModVariables.MapVariables.get(world).evo_point_grow = 0;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				stra = Component.translatable("gui.caerula_arbor_reunite.evo_tree.label_sreategy_grow").getString();
			} else if (target == 1) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:subsisting1")), SoundSource.PLAYERS, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:subsisting1")), SoundSource.PLAYERS, 1, 1, false);
					}
				}
				CaerulaArborModVariables.MapVariables.get(world).evo_point_subsisting = 0;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				stra = Component.translatable("gui.caerula_arbor_reunite.evo_tree.label_strategy_subsisting").getString();
			} else if (target == 2) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:breed1")), SoundSource.PLAYERS, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:breed1")), SoundSource.PLAYERS, 1, 1, false);
					}
				}
				CaerulaArborModVariables.MapVariables.get(world).evo_point_breed = 0;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				stra = Component.translatable("gui.caerula_arbor_reunite.evo_tree.label_strategy_breed").getString();
			} else if (target == 3) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:migration1")), SoundSource.PLAYERS, 1, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:migration1")), SoundSource.PLAYERS, 1, 1, false);
					}
				}
				CaerulaArborModVariables.MapVariables.get(world).evo_point_migration = 0;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				stra = Component.translatable("gui.caerula_arbor_reunite.evo_tree.label_strategy_migration").getString();
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles((SimpleParticleType) (CaerulaArborModParticleTypes.ENDSPEAKER_INV.get()), (x + 0.5), (y + 1), (z + 0.5), 32, 0.75, 1, 0.75, 0.15);
			info = (Component.translatable("item.caerula_arbor_reunite.dictationless_chapter.use").getString()).replace("{stra}", stra);
			if (!world.isClientSide() && world.getServer() != null)
				world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(((Component.translatable("item.caerula_arbor_reunite.dictationless_chapter.broadcast").getString()).replace("<player>", entity.getDisplayName().getString()))), false);
			if (entity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(info), false);
			itemstack.shrink(1);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
