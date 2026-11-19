package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.ThirsterEntity;

import java.util.Comparator;

public class ReduceThirsterAttrProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		Entity thirster = null;
		thirster = (Entity) world.getEntitiesOfClass(ThirsterEntity.class, AABB.ofSize(new Vec3(x, y, z), 48, 48, 48), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (!(thirster == null)) {
			if (thirster instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
				_livingEntity3.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
						.setBaseValue(Math.max((thirster instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get())
								? _livingEntity2.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).getBaseValue()
								: 0) - 1, 0));
			if (thirster instanceof LivingEntity _livingEntity5 && _livingEntity5.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()))
				_livingEntity5.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
						.setBaseValue(Math.max((thirster instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get())
								? _livingEntity4.getAttribute(CaerulaArborModAttributes.MAGIC_RESISTANCE.get()).getBaseValue()
								: 0) - 5, 0));
		}
	}
}
