package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.Comparator;

public class DispatchAlchemyProcedure {
	public static void execute(LevelAccessor world, Entity chief, ItemStack itemstack, double tx, double ty, double tz) {
		if (chief == null)
			return;
		double tX = 0;
		double tY = 0;
		double tZ = 0;
		double num = 0;
		String log = "";
		String name = "";
		boolean creative = false;
		if (!(chief instanceof Player _plrCldCheck1 && _plrCldCheck1.getCooldowns().isOnCooldown(itemstack.getItem()))) {
			tX = tx;
			tY = ty;
			tZ = tz;
			creative = new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
					}
					return false;
				}
			}.checkGamemode(chief);
			{
				final Vec3 _center = new Vec3(tx, ty, tz);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(48 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (IsAlchemyProcedure.execute(entityiterator) && (creative || (entityiterator instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) == chief)) {
						num = num + 1;
						RemoveTargetProcedure.execute(entityiterator);
						if (entityiterator instanceof Mob _entity)
							_entity.getNavigation().moveTo((tx + Mth.nextDouble(RandomSource.create(), -2, 2)), tY, (tz + Mth.nextDouble(RandomSource.create(), -2, 2)), 1);
					}
				}
			}
			if (num > 0) {
				if (!creative) {
					if (chief instanceof Player _player)
						_player.getCooldowns().addCooldown(itemstack.getItem(), 40);
				}
				log = Component.translatable("item.caerula_arbor_reunite.alchemist_wand.call").getString();
				log = log.replace("{num}", "" + Math.round(num));
				log = log.replace("{x}", "" + Math.round(Math.pow(10, 2) * tX) / Math.pow(10, 2));
				log = log.replace("{z}", "" + Math.round(Math.pow(10, 2) * tZ) / Math.pow(10, 2));
				log = log.replace("{y}", "" + Math.round(Math.pow(10, 2) * tY) / Math.pow(10, 2));
				if (chief instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(log), true);
			}
		}
	}
}
