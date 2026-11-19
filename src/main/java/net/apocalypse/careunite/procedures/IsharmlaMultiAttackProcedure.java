package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class IsharmlaMultiAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity sourceentity) {
		if (sourceentity == null)
			return;
		Entity enemy = null;
		double damage = 0;
		double r = 0;
		double h = 0;
		double count = 0;
		double sX = 0;
		double sY = 0;
		double sZ = 0;
		enemy = sourceentity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		r = 24;
		damage = sourceentity instanceof LivingEntity _livingEntity1 && _livingEntity1.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity1.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		sX = sourceentity.getX();
		sY = sourceentity.getY();
		sZ = sourceentity.getZ();
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_attack_launch")), SoundSource.HOSTILE, (float) 2.5, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_attack_launch")), SoundSource.HOSTILE, (float) 2.5, 1, false);
			}
		}
		if (!(enemy == null)) {
			IsharmlaDroppedAttackProcedure.execute(world, enemy.getX(), enemy.getY(), enemy.getZ(), sourceentity, Mth.nextDouble(RandomSource.create(), 1.5, 3), 1);
		}
		for (int index0 = 0; index0 < 5; index0++) {
			if (count > 5) {
				break;
			}
			{
				final Vec3 _center = new Vec3((sourceentity.getX()), (sourceentity.getY()), (sourceentity.getZ()));
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(48 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if (count > 5) {
						break;
					}
					if (!(entityiterator instanceof LivingEntity)) {
						continue;
					}
					if (sourceentity == entityiterator) {
						continue;
					}
					if (entityiterator instanceof Player) {
						if (new Object() {
							public boolean checkGamemode(Entity _ent) {
								if (_ent instanceof ServerPlayer _serverPlayer) {
									return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.CREATIVE;
								} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
									return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
											&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.CREATIVE;
								}
								return false;
							}
						}.checkGamemode(entityiterator)) {
							continue;
						}
						if (new Object() {
							public boolean checkGamemode(Entity _ent) {
								if (_ent instanceof ServerPlayer _serverPlayer) {
									return _serverPlayer.gameMode.getGameModeForPlayer() == GameType.SPECTATOR;
								} else if (_ent.level().isClientSide() && _ent instanceof Player _player) {
									return Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()) != null
											&& Minecraft.getInstance().getConnection().getPlayerInfo(_player.getGameProfile().getId()).getGameMode() == GameType.SPECTATOR;
								}
								return false;
							}
						}.checkGamemode(entityiterator)) {
							continue;
						}
					}
					if (IsValidEnemyForSeabornProcedure.execute(entityiterator, sourceentity) && (entityiterator != null ? sourceentity.distanceTo(entityiterator) : -1) <= r) {
						count = count + 1;
						CaerulaArborMod.queueServerWork((int) (2 * count), () -> {
							IsharmlaDroppedAttackProcedure.execute(world, entityiterator.getX(), entityiterator.getY(), entityiterator.getZ(), sourceentity, Mth.nextDouble(RandomSource.create(), 1.5, 3), 1);
						});
					}
				}
			}
		}
	}
}
