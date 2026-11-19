package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.GameType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.client.Minecraft;

import net.apocalypse.careunite.entity.RunvolutionWaveEntity;
import net.apocalypse.careunite.entity.RunvolutionEntity;
import net.apocalypse.careunite.entity.GladiiaWhirlEntity;

import java.util.Comparator;

public class RunvolutionMatrixAttackProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, double D1, double D2, double rate) {
		if (entity == null)
			return;
		Entity owner = null;
		double d = 0;
		double n = 0;
		owner = (Entity) world.getEntitiesOfClass(RunvolutionEntity.class, AABB.ofSize(new Vec3(x, y, z), 24, 24, 24), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (owner == null) {
			owner = entity;
			d = 7;
		} else {
			d = owner instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity2.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0;
		}
		n = Math.min(GetLivingEntityNumProcedure.execute(world, x, y, z, owner), 20);
		for (Entity entityiterator : world.getEntities(owner, new AABB((x - D1), (y - 1), (z - D1), (x + D1), (y + 4), (z + D1)))) {
			if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))) {
				if (!((entityiterator instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == owner)) {
					continue;
				}
			}
			if (!(entityiterator instanceof LivingEntity)) {
				continue;
			}
			if (entityiterator instanceof RunvolutionWaveEntity) {
				continue;
			}
			if (entityiterator instanceof GladiiaWhirlEntity) {
				continue;
			}
			if (entityiterator.getY() > y && entityiterator.getDeltaMovement().y() > 0) {
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
			}.checkGamemode(entityiterator) || new Object() {
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
			if (Math.abs(x - entityiterator.getX()) >= D2 || Math.abs(z - entityiterator.getZ()) >= D2) {
				entityiterator.hurt(
						new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:runvolution_attack"))), entity, owner),
						(float) (d * rate * (1 + 0.1 * n)));
				if (n > 0) {
					LivingHeal1Procedure.execute(owner, d * rate * 0.05 * n);
				}
				if (D2 > 9) {
					RunvolutionWaveRelocateProcedure.execute(x, z, entityiterator, 0.625);
				} else if (D2 > 1) {
					RunvolutionWaveRelocateProcedure.execute(x, z, entityiterator, 0.4);
				}
			}
		}
	}
}
