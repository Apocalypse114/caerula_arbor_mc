package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;

import net.apocalypse.careunite.entity.IzumilDollEntity;
import net.apocalypse.careunite.entity.IzumikOffspringEntity;

import java.util.Comparator;

public class IzumikDollTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double ttt = 0;
		Entity followee = null;
		ttt = entity instanceof IzumilDollEntity _datEntI ? _datEntI.getEntityData().get(IzumilDollEntity.DATA_TransformTick) : 0;
		entity.stopRiding();
		if (ttt >= 0 && entity instanceof IzumilDollEntity _datEntL2 && _datEntL2.getEntityData().get(IzumilDollEntity.DATA_CanLoot)) {
			entity.setDeltaMovement(new Vec3(0, 0, 0));
			if (ttt >= 40) {
				if (!world.isClientSide() && world.getServer() != null) {
					for (ItemStack itemstackiterator : world.getServer().getLootData().getLootTable(new ResourceLocation("caerula_arbor_reunite:izumik_loot")).getRandomItems(new LootParams.Builder((ServerLevel) world).create(LootContextParamSets.EMPTY))) {
						if (world instanceof ServerLevel _level) {
							ItemEntity entityToSpawn = new ItemEntity(_level, x, y, z, itemstackiterator);
							entityToSpawn.setPickUpDelay(10);
							entityToSpawn.setUnlimitedLifetime();
							_level.addFreshEntity(entityToSpawn);
						}
					}
				}
				if (!entity.level().isClientSide())
					entity.discard();
			}
			if (entity instanceof IzumilDollEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IzumilDollEntity.DATA_TransformTick, (int) (ttt + 1));
		} else {
			if (entity.tickCount % 5 == 0) {
				followee = (Entity) world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 96, 96, 96), e -> true).stream().sorted(new Object() {
					Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
						return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
					}
				}.compareDistOf(x, y, z)).findFirst().orElse(null);
				if (followee == null) {
					followee = (Entity) world.getEntitiesOfClass(IzumikOffspringEntity.class, AABB.ofSize(new Vec3(x, y, z), 96, 96, 96), e -> true).stream().sorted(new Object() {
						Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
							return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
						}
					}.compareDistOf(x, y, z)).findFirst().orElse(null);
				}
				if (!(followee == null)) {
					if ((followee != null ? entity.distanceTo(followee) : -1) > 2.5) {
						if (entity instanceof Mob _entity)
							_entity.getNavigation().moveTo((followee.getX()), (followee.getY() + 1), (followee.getZ()), 1);
					}
				}
			}
		}
	}
}
