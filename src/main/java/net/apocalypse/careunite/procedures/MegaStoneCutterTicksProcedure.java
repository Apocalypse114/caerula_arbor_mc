package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.MegaStonecutterEntity;

public class MegaStoneCutterTicksProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		double sklp = 0;
		double cooldown = 0;
		Entity enemy = null;
		sklp = entity instanceof MegaStonecutterEntity _datEntI ? _datEntI.getEntityData().get(MegaStonecutterEntity.DATA_SKILL_TICk) : 0;
		cooldown = entity instanceof MegaStonecutterEntity _datEntI ? _datEntI.getEntityData().get(MegaStonecutterEntity.DATA_SKILL_COOLDOWN) : 0;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (sklp >= 1) {
			if (entity instanceof MegaStonecutterEntity _datEntSetI)
				_datEntSetI.getEntityData().set(MegaStonecutterEntity.DATA_SKILL_TICk, (int) (sklp - 1));
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (!(enemy == null) && enemy.isAlive()) {
				if (entity instanceof MegaStonecutterEntity _datEntSetL)
					_datEntSetL.getEntityData().set(MegaStonecutterEntity.DATA_SHOOTINGGG, ((enemy != null ? entity.distanceTo(enemy) : -1) <= 24));
				if (enemy instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_KNOCKACK_RESISTANVCE.get(), 20, 9, false, false));
				entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY()), (enemy.getZ())));
			} else {
				if (entity instanceof MegaStonecutterEntity _datEntSetL)
					_datEntSetL.getEntityData().set(MegaStonecutterEntity.DATA_SHOOTINGGG, false);
			}
		} else {
			if (cooldown >= 1) {
				if (entity instanceof MegaStonecutterEntity _datEntSetI)
					_datEntSetI.getEntityData().set(MegaStonecutterEntity.DATA_SKILL_COOLDOWN, (int) (cooldown - 1));
				if (entity instanceof MegaStonecutterEntity _datEntSetL)
					_datEntSetL.getEntityData().set(MegaStonecutterEntity.DATA_SHOOTINGGG, false);
			} else {
				if (!(enemy == null) && enemy.isAlive() && (enemy != null ? entity.distanceTo(enemy) : -1) <= 20) {
					if (entity instanceof LivingEntity _entity)
						_entity.removeAllEffects();
					if (entity instanceof MegaStonecutterEntity _datEntSetI)
						_datEntSetI.getEntityData().set(MegaStonecutterEntity.DATA_SKILL_COOLDOWN, 300);
					if (entity instanceof MegaStonecutterEntity _datEntSetI)
						_datEntSetI.getEntityData().set(MegaStonecutterEntity.DATA_SKILL_TICk, 820);
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.SUPER_DESTRUCTIVE_MODE.get(), 820, 0, false, false));
				}
			}
		}
	}
}
