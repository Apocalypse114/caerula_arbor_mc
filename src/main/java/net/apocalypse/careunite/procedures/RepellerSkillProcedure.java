package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.TideDeathrepellerEntity;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class RepellerSkillProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double num = 0;
		double sklp = 0;
		double dura = 0;
		if (!(entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(CaerulaArborModMobEffects.FAKE_DEATH.get()))) {
			sklp = entity instanceof TideDeathrepellerEntity _datEntI ? _datEntI.getEntityData().get(TideDeathrepellerEntity.DATA_skillp) : 0;
			dura = entity instanceof TideDeathrepellerEntity _datEntI ? _datEntI.getEntityData().get(TideDeathrepellerEntity.DATA_duration) : 0;
			if (dura > 0) {
				if (entity instanceof TideDeathrepellerEntity _datEntSetI)
					_datEntSetI.getEntityData().set(TideDeathrepellerEntity.DATA_duration, (int) (dura - 1));
			}
			if (sklp <= 0) {
				num = 0;
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(8 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (!(entityiterator == entity) && (entityiterator instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) >= 10) {
							num = num + 1;
						}
					}
				}
				if (num >= 2 || (entity instanceof LivingEntity _livEnt ? _livEnt.getHealth() : -1) < (entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.5) {
					enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
					if (!(enemy == null) && (enemy != null ? entity.distanceTo(enemy) : -1) <= 4) {
						entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY()), (enemy.getZ())));
						if (entity instanceof TideDeathrepellerEntity) {
							((TideDeathrepellerEntity) entity).setAnimation("empty");
						}
						if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
							_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 50, 0, false, false));
						if (entity instanceof TideDeathrepellerEntity) {
							((TideDeathrepellerEntity) entity).setAnimation("animation.deathrepeller.combo");
						}
						CaerulaArborMod.queueServerWork(17, () -> {
							if (entity.isAlive()) {
								RepellerChopProcedure.execute(world, x, y, z, entity, 2);
							}
						});
						CaerulaArborMod.queueServerWork(23, () -> {
							if (entity.isAlive()) {
								RepellerChopProcedure.execute(world, x, y, z, entity, 2);
							}
						});
						CaerulaArborMod.queueServerWork(35, () -> {
							if (entity.isAlive()) {
								RepellerChopProcedure.execute(world, x, y, z, entity, 2);
							}
						});
						CaerulaArborMod.queueServerWork(42, () -> {
							if (entity.isAlive()) {
								RepellerChopProcedure.execute(world, x, y, z, entity, 3.5);
							}
						});
						if (entity instanceof TideDeathrepellerEntity _datEntSetI)
							_datEntSetI.getEntityData().set(TideDeathrepellerEntity.DATA_duration, 53);
						if (entity instanceof TideDeathrepellerEntity _datEntSetI)
							_datEntSetI.getEntityData().set(TideDeathrepellerEntity.DATA_skillp, 300);
					}
				}
			} else {
				if (entity instanceof TideDeathrepellerEntity _datEntSetI)
					_datEntSetI.getEntityData().set(TideDeathrepellerEntity.DATA_skillp, (int) (sklp - 1));
				if (CaerulaArborModVariables.MapVariables.get(world).strategy_grow >= 3) {
					if (entity instanceof TideDeathrepellerEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TideDeathrepellerEntity.DATA_skillp, (int) (sklp - 2));
				}
			}
		}
	}
}
