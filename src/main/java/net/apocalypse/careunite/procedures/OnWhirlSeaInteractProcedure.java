package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class OnWhirlSeaInteractProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		BlockState stateTemp = Blocks.AIR.defaultBlockState();
		double tx = 0;
		double ty = 0;
		double tz = 0;
		double count = 0;
		if (Math.min(CaerulaArborModVariables.MapVariables.get(world).strategy_grow, CaerulaArborModVariables.MapVariables.get(world).strategy_migration) >= 1 || new Object() {
			public boolean checkGamemode(Entity _ent) {
				if (_ent instanceof ServerPlayer _serverPlayer) {
					return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
				} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
					return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
				}
				return false;
			}
		}.checkGamemode(sourceentity)) {
			for (int index0 = 0; index0 < 9; index0++) {
				for (int index1 = 0; index1 < 9; index1++) {
					for (int index2 = 0; index2 < 9; index2++) {
						tx = x + index0 - 4;
						ty = y + index1 - 4;
						tz = z + index2 - 4;
						stateTemp = (world.getBlockState(BlockPos.containing(tx, ty, tz)));
						if (stateTemp.getBlock() == Blocks.PRISMARINE || stateTemp.getBlock() == Blocks.PRISMARINE_BRICKS || stateTemp.getBlock() == Blocks.DARK_PRISMARINE || stateTemp.getBlock() == Blocks.SEA_LANTERN) {
							count = count + 1;
						}
					}
				}
			}
			if (count >= 64) {
				count = 64;
				if (!entity.level().isClientSide())
					entity.discard();
				if (sourceentity instanceof Player _player) {
					ItemStack _stktoremove = new ItemStack(CaerulaArborModItems.WHIRL_EYE.get());
					_player.getInventory().clearOrCountMatchingItems(p -> _stktoremove.getItem() == p.getItem(), 1, _player.inventoryMenu.getCraftSlots());
				}
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.PROMOTED_ELDER_GUARDIAN.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie.converted_to_drowned")), SoundSource.AMBIENT, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.zombie.converted_to_drowned")), SoundSource.AMBIENT, 2, 1, false);
					}
				}
				for (int index3 = 0; index3 < 9; index3++) {
					for (int index4 = 0; index4 < 9; index4++) {
						for (int index5 = 0; index5 < 9; index5++) {
							tx = x + index3 - 4;
							ty = y + index4 - 4;
							tz = z + index5 - 4;
							stateTemp = (world.getBlockState(BlockPos.containing(tx, ty, tz)));
							if (stateTemp.getBlock() == Blocks.PRISMARINE || stateTemp.getBlock() == Blocks.PRISMARINE_BRICKS || stateTemp.getBlock() == Blocks.DARK_PRISMARINE || stateTemp.getBlock() == Blocks.SEA_LANTERN) {
								world.destroyBlock(BlockPos.containing(tx, ty, tz), false);
								count = count - 1;
								if (count <= 0) {
									break;
								}
							}
						}
						if (count <= 0) {
							break;
						}
					}
					if (count <= 0) {
						break;
					}
				}
			}
		}
	}
}
