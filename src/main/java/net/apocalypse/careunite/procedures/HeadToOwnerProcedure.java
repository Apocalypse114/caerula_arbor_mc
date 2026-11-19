package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.SonsEntity;
import net.apocalypse.careunite.entity.BishopFishEntity;

import java.util.Comparator;

public class HeadToOwnerProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity owner = null;
		Entity tgt = null;
		owner = (Entity) world.getEntitiesOfClass(BishopFishEntity.class, AABB.ofSize(new Vec3(x, y, z), 96, 96, 96), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (!(owner == null)) {
			if (entity instanceof Mob _entity)
				_entity.getNavigation().moveTo((owner.getX()), (owner.getY()), (owner.getZ()), 0.33);
		} else {
			if (entity instanceof Mob _entity)
				_entity.getNavigation().stop();
		}
		if (!(entity instanceof LivingEntity _livEnt7 && _livEnt7.hasEffect(CaerulaArborModMobEffects.COOLDOWN_SINAL.get()))) {
			tgt = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (!(null == tgt)) {
				if ((tgt != null ? entity.distanceTo(tgt) : -1) <= 3.5) {
					if (tgt instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 30, 3, false, false));
					{
						Entity _ent = tgt;
						_ent.teleportTo((entity.getX() + entity.getLookAngle().x * 0.33), (entity.getY()), (entity.getZ() + entity.getLookAngle().z * 0.33));
						if (_ent instanceof ServerPlayer _serverPlayer)
							_serverPlayer.connection.teleport((entity.getX() + entity.getLookAngle().x * 0.33), (entity.getY()), (entity.getZ() + entity.getLookAngle().z * 0.33), _ent.getYRot(), _ent.getXRot());
					}
					tgt.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3(x, y, z));
					if (entity instanceof SonsEntity) {
						((SonsEntity) entity).setAnimation("animation.bishopson.loop");
					}
				}
			}
		}
	}
}
