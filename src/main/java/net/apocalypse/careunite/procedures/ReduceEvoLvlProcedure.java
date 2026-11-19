package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.network.CaerulaArborModVariables;

public class ReduceEvoLvlProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, ItemStack itemstack) {
		if (entity == null)
			return;
		boolean shouldBroadCast = false;
		double maxium_lvl = 0;
		double lvl1 = 0;
		double lvl2 = 0;
		double lvl3 = 0;
		double lvl4 = 0;
		double useTick = 0;
		double gameTick = 0;
		String stra = "";
		String info = "";
		String info_raw = "";
		String broadCast = "";
		useTick = CaerulaArborModVariables.MapVariables.get(world).incandescentAnimaUseTick;
		gameTick = world.getLevelData().getGameTime();
		if (useTick > 0 && gameTick - useTick < 24000) {
			if (!(new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
					}
					return false;
				}
			}.checkGamemode(entity))) {
				info = Component.translatable("item.caerula_arbor_reunite.incandescent_anima.cooldown").getString();
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(info), true);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.PLAYERS, 3, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.PLAYERS, 3, 1, false);
					}
				}
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.ASH, x, (y + 0.75), z, 32, 0.75, 0.75, 0.75, 0.1);
				return;
			}
		}
		broadCast = Component.translatable("item.caerula_arbor_reunite.incandescent_anima.broadcast").getString();
		broadCast = broadCast.replace("<player>", entity.getDisplayName().getString());
		if (IfCanSilenceProcedure.execute(world)) {
			lvl1 = CaerulaArborModVariables.MapVariables.get(world).strategy_silence;
			if (lvl1 > 0) {
				maxium_lvl = lvl1 - 1;
				CaerulaArborModVariables.MapVariables.get(world).strategy_silence = maxium_lvl;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				CaerulaArborModVariables.MapVariables.get(world).evo_point_silence = 0;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				info = Component.translatable("item.caerula_arbor_reunite.incandescent_anima.use").getString();
				stra = Component.translatable("caerula_arbor_reunite.strategy.silence").getString();
				info = info.replace("{stra}", stra);
				info = info.replace("{p}", "" + (int) maxium_lvl);
				if (!world.isClientSide() && world.getServer() != null)
					world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(broadCast), false);
				if (!world.isClientSide() && world.getServer() != null)
					world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(info), false);
				shouldBroadCast = true;
			} else {
				CaerulaArborModVariables.MapVariables.get(world).silence_enabled = false;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				info = Component.translatable("item.caerula_arbor_reunite.incandescent_anima.disable").getString();
				if (!world.isClientSide() && world.getServer() != null)
					world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(broadCast), false);
				if (!world.isClientSide() && world.getServer() != null)
					world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(info), false);
				shouldBroadCast = true;
			}
		} else {
			lvl1 = CaerulaArborModVariables.MapVariables.get(world).strategy_grow;
			lvl2 = CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting;
			lvl3 = CaerulaArborModVariables.MapVariables.get(world).strategy_breed;
			lvl4 = CaerulaArborModVariables.MapVariables.get(world).strategy_migration;
			maxium_lvl = Math.max(Math.max(lvl1, lvl2), Math.max(lvl3, lvl4));
			if (maxium_lvl == 0) {
				info = Component.translatable("item.caerula_arbor_reunite.incandescent_anima.fail").getString();
				if (entity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(info), true);
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.PLAYERS, 3, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.fire.extinguish")), SoundSource.PLAYERS, 3, 1, false);
					}
				}
				if (world instanceof ServerLevel _level)
					_level.sendParticles(ParticleTypes.ASH, x, (y + 0.75), z, 32, 0.75, 0.75, 0.75, 0.1);
				return;
			}
			info_raw = Component.translatable("item.caerula_arbor_reunite.incandescent_anima.use").getString();
			info_raw = info_raw.replace("{p}", "" + (int) (maxium_lvl - 1));
			if (!world.isClientSide() && world.getServer() != null)
				world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(broadCast), false);
			if (lvl1 == maxium_lvl) {
				CaerulaArborModVariables.MapVariables.get(world).strategy_grow = maxium_lvl - 1;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				CaerulaArborModVariables.MapVariables.get(world).evo_point_grow = 0;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				stra = Component.translatable("gui.caerula_arbor_reunite.evo_tree.label_sreategy_grow").getString();
				info = info_raw.replace("{stra}", stra);
				shouldBroadCast = true;
				if (!world.isClientSide() && world.getServer() != null)
					world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(info), false);
			}
			if (lvl2 == maxium_lvl) {
				CaerulaArborModVariables.MapVariables.get(world).strategy_subsisting = maxium_lvl - 1;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				CaerulaArborModVariables.MapVariables.get(world).evo_point_subsisting = 0;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				stra = Component.translatable("gui.caerula_arbor_reunite.evo_tree.label_strategy_subsisting").getString();
				info = info_raw.replace("{stra}", stra);
				shouldBroadCast = true;
				if (!world.isClientSide() && world.getServer() != null)
					world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(info), false);
			}
			if (lvl3 == maxium_lvl) {
				CaerulaArborModVariables.MapVariables.get(world).strategy_breed = maxium_lvl - 1;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				CaerulaArborModVariables.MapVariables.get(world).evo_point_breed = 0;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				stra = Component.translatable("gui.caerula_arbor_reunite.evo_tree.label_strategy_breed").getString();
				info = info_raw.replace("{stra}", stra);
				shouldBroadCast = true;
				if (!world.isClientSide() && world.getServer() != null)
					world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(info), false);
			}
			if (lvl4 == maxium_lvl) {
				CaerulaArborModVariables.MapVariables.get(world).strategy_migration = maxium_lvl - 1;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				CaerulaArborModVariables.MapVariables.get(world).strategy_migration = 0;
				CaerulaArborModVariables.MapVariables.get(world).syncData(world);
				stra = Component.translatable("gui.caerula_arbor_reunite.evo_tree.label_strategy_migration").getString();
				info = info_raw.replace("{stra}", stra);
				shouldBroadCast = true;
				if (!world.isClientSide() && world.getServer() != null)
					world.getServer().getPlayerList().broadcastSystemMessage(Component.literal(info), false);
			}
		}
		if (shouldBroadCast) {
			CaerulaArborModVariables.MapVariables.get(world).incandescentAnimaUseTick = gameTick;
			CaerulaArborModVariables.MapVariables.get(world).syncData(world);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.end_portal.spawn")), SoundSource.PLAYERS, 3, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.end_portal.spawn")), SoundSource.PLAYERS, 3, 1, false);
				}
			}
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.END_ROD, x, (y + 0.75), z, 32, 0.75, 0.75, 0.75, 0.1);
			itemstack.shrink(1);
		}
	}
}
