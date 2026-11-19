package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.FakeOffspringEntity;

public class StickToEnemyProcedure {
	public static void execute(LevelAccessor world, Entity entity) {
		if (entity == null)
			return;
		Entity obj = null;
		double ToHurt = 0;
		double amplifi = 0;
		if (entity instanceof Mob _mobEnt0 && _mobEnt0.isAggressive()) {
			obj = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (!(obj == null)) {
				if ((obj != null ? entity.distanceTo(obj) : -1) <= 4) {
					{
						Entity _ent = entity;
						_ent.teleportTo((obj.getX() + obj.getBbWidth() * (entity instanceof FakeOffspringEntity _datEntI ? _datEntI.getEntityData().get(FakeOffspringEntity.DATA_dx) : 0) * 0.01), (obj.getY()),
								(obj.getZ() + obj.getBbWidth() * (entity instanceof FakeOffspringEntity _datEntI ? _datEntI.getEntityData().get(FakeOffspringEntity.DATA_dz) : 0) * 0.01));
						if (_ent instanceof ServerPlayer _serverPlayer)
							_serverPlayer.connection.teleport((obj.getX() + obj.getBbWidth() * (entity instanceof FakeOffspringEntity _datEntI ? _datEntI.getEntityData().get(FakeOffspringEntity.DATA_dx) : 0) * 0.01), (obj.getY()),
									(obj.getZ() + obj.getBbWidth() * (entity instanceof FakeOffspringEntity _datEntI ? _datEntI.getEntityData().get(FakeOffspringEntity.DATA_dz) : 0) * 0.01), _ent.getYRot(), _ent.getXRot());
					}
				}
			}
		}
		if (entity.tickCount % 20 == 0) {
			amplifi = 1;
			if (entity.tickCount >= 320) {
				amplifi = 10;
			}
			ToHurt = (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.125 * amplifi;
			if ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) > ToHurt) {
				if (entity instanceof LivingEntity _entity)
					_entity.setHealth((float) ((entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) - ToHurt));
			} else {
				entity.hurt(new DamageSource(world.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:oceankiller_damage")))), 99999);
			}
		}
	}
}
