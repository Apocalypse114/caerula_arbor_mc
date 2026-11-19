package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.ChieslerPetEntity;

public class ChieslerSittingProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		Entity owner = null;
		Entity enemy = null;
		owner = entity instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null;
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (entity instanceof ChieslerPetEntity _datEntL2 && _datEntL2.getEntityData().get(ChieslerPetEntity.DATA_SITTING)) {
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
		if (!(enemy == null) && enemy.isAlive()) {
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.ADD_DEF_TINY.get(), 10, 9, false, false));
		}
	}
}
