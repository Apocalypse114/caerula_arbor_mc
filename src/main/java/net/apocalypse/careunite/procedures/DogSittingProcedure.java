package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.entity.OceanizedDogEntity;

public class DogSittingProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		Entity owner = null;
		Entity enemy = null;
		owner = entity instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (entity instanceof OceanizedDogEntity _datEntL2 && _datEntL2.getEntityData().get(OceanizedDogEntity.DATA_sitting)) {
			entity.setShiftKeyDown(true);
			if (!(entity instanceof LivingEntity _livEnt4 && _livEnt4.hasEffect(MobEffects.MOVEMENT_SLOWDOWN))) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 5, 9, false, false));
			}
			if (entity instanceof Mob _entity)
				_entity.setTarget(null);
		} else {
			entity.setShiftKeyDown(false);
		}
		if (enemy == owner || (enemy instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) == owner) {
			if (entity instanceof Mob _entity)
				_entity.setTarget(null);
		}
	}
}
