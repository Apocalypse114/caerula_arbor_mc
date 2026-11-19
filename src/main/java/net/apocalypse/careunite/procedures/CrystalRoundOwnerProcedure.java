package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.OceanizedEnderinaEntity;

public class CrystalRoundOwnerProcedure {
	public static void execute(Entity entity, Entity owner) {
		if (entity == null || owner == null)
			return;
		Entity illusioner = null;
		Entity enemy = null;
		illusioner = owner;
		if (illusioner == null) {
			return;
		}
		if ((illusioner instanceof OceanizedEnderinaEntity _datEntI ? _datEntI.getEntityData().get(OceanizedEnderinaEntity.DATA_REVIVE_TICK) : 0) <= 0) {
			CrytsalToEnderinaProcedure.execute(entity, illusioner);
			if (!(entity instanceof LivingEntity _livEnt2 && _livEnt2.hasEffect(CaerulaArborModMobEffects.MUTE.get()))) {
				LivingHeal1Procedure.execute(illusioner, (illusioner instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.0075);
			}
		} else {
			if (entity instanceof Mob _entity)
				_entity.getNavigation().moveTo((illusioner.getX()), (illusioner.getY()), (illusioner.getZ()), 0.5);
		}
	}
}
