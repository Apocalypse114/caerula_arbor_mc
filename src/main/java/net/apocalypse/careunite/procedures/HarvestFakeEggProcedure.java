package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.init.CaerulaArborModEntities;

public class HarvestFakeEggProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return;
		double age = 0;
		if (new Object() {
			public boolean checkGamemode(Entity _ent) {
				if (_ent instanceof ServerPlayer _serverPlayer) {
					return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
				} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
					return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null && Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
				}
				return false;
			}
		}.checkGamemode(entity)) {
			return;
		}
		age = blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip2 ? blockstate.getValue(_getip2) : -1;
		if (age >= 2) {
			if (Math.random() < 0.75) {
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 0.5), (z + 0.5), new ItemStack(CaerulaArborModItems.FAKE_EGG.get()));
					entityToSpawn.setPickUpDelay(10);
					_level.addFreshEntity(entityToSpawn);
				}
			} else if (!(((entity instanceof LivingEntity _entity) ? _entity.getMainHandItem() : ItemStack.EMPTY).getItem() instanceof AxeItem
					|| (entity instanceof LivingEntity _entity ? _entity.getOffhandItem() : ItemStack.EMPTY).getItem() instanceof AxeItem)) {
				if (world instanceof ServerLevel _level) {
					Entity entityToSpawn = CaerulaArborModEntities.FAKE_OFFSPRING.get().spawn(_level, BlockPos.containing(x, y, z), MobSpawnType.MOB_SUMMONED);
					if (entityToSpawn != null) {
						entityToSpawn.setYRot(world.getRandom().nextFloat() * 360F);
					}
				}
			}
		}
	}
}
