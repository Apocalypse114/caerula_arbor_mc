package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.entity.CreeperPetEntity;

public class CreeperSittingProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		Entity owner = null;
		Entity enemy = null;
		double at = 0;
		owner = entity instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (entity instanceof CreeperPetEntity _datEntL2 && _datEntL2.getEntityData().get(CreeperPetEntity.DATA_SIT)) {
			if (!(entity instanceof LivingEntity _livEnt3 && _livEnt3.hasEffect(MobEffects.MOVEMENT_SLOWDOWN))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 9, false, false));
			}
			entity.setShiftKeyDown(true);
			RemoveTargetProcedure.execute(entity);
		} else {
			entity.setShiftKeyDown(false);
		}
		if (enemy == owner || (enemy instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) == owner) {
			RemoveTargetProcedure.execute(entity);
		}
		at = entity instanceof CreeperPetEntity _datEntI ? _datEntI.getEntityData().get(CreeperPetEntity.DATA_ANIM_TICK) : 0;
		if (at > 0) {
			if (entity instanceof CreeperPetEntity _datEntSetI)
				_datEntSetI.getEntityData().set(CreeperPetEntity.DATA_ANIM_TICK, (int) (at - 1));
		}
	}
}
