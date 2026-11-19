package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;

import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.TideBishopEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class BishopSkillProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double sklp = 0;
		if (!(entity instanceof LivingEntity _livEnt0 && _livEnt0.hasEffect(CaerulaArborModMobEffects.FAKE_DEATH.get()))) {
			sklp = entity instanceof TideBishopEntity _datEntI ? _datEntI.getEntityData().get(TideBishopEntity.DATA_skillp) : 0;
			if (sklp <= 0) {
				if (!((entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null) == null)) {
					if (entity instanceof TideBishopEntity) {
						((TideBishopEntity) entity).setAnimation("animation.tidebishop.cast");
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 50, 0, false, false));
					CaerulaArborMod.queueServerWork(33, () -> {
						BishopHealProcedure.execute(world, x, y, z, entity);
					});
					if (entity instanceof TideBishopEntity _datEntSetI)
						_datEntSetI.getEntityData().set(TideBishopEntity.DATA_skillp, 200);
				}
			} else {
				if (entity instanceof TideBishopEntity _datEntSetI)
					_datEntSetI.getEntityData().set(TideBishopEntity.DATA_skillp, (int) (sklp - 1));
			}
		}
	}
}
