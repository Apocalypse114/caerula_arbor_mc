package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.Comparator;

public class CallAlchemyAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		String log = "";
		double num = 0;
		boolean cre = false;
		if (!IsAlchemyProcedure.execute(entity)) {
			cre = new Object() {
				public boolean checkGamemode(Entity _ent) {
					if (_ent instanceof ServerPlayer _serverPlayer) {
						return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
					} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
						return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
								&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
					}
					return false;
				}
			}.checkGamemode(sourceentity);
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (IsAlchemyProcedure.execute(entityiterator) && (cre || (entityiterator instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) == sourceentity)) {
						if (entityiterator instanceof Mob _entity && entity instanceof LivingEntity _ent)
							_entity.setTarget(_ent);
						num = num + 1;
					}
				}
			}
			if (num > 0) {
				log = Component.translatable("item.caerula_arbor_reunite.alchemist_wand.atk").getString();
				log = log.replace("{num}", "" + Math.round(num));
				log = log.replace("{enemy}", entity.getDisplayName().getString());
				if (sourceentity instanceof Player _player && !_player.level().isClientSide())
					_player.displayClientMessage(Component.literal(log), true);
			}
		}
	}
}
