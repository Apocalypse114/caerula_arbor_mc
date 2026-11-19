package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;

public class DirectionInitProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		{
			Entity _ent = entity;
			_ent.setYRot((float) (90 * Mth.nextInt(RandomSource.create(), 0, 3)));
			_ent.setXRot(0);
			_ent.setYBodyRot(_ent.getYRot());
			_ent.setYHeadRot(_ent.getYRot());
			_ent.yRotO = _ent.getYRot();
			_ent.xRotO = _ent.getXRot();
			if (_ent instanceof LivingEntity _entity) {
				_entity.yBodyRotO = _entity.getYRot();
				_entity.yHeadRotO = _entity.getYRot();
			}
		}
		if (entity instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
			_livingEntity2.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).setBaseValue(18);
	}
}
