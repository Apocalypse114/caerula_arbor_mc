package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.apocalypse.careunite.entity.OceanizedIllusionerEntity;

import java.util.Comparator;

public class IllusionRoundOwnerProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity illusioner = null;
		Entity enemy = null;
		if (!entity.isAlive()) {
			return;
		}
		if (entity.tickCount % 40 == 20) {
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (!(enemy == null) && enemy.isAlive()) {
				return;
			}
			illusioner = (Entity) world.getEntitiesOfClass(OceanizedIllusionerEntity.class, AABB.ofSize(new Vec3(x, y, z), 48, 48, 48), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null);
			if (illusioner == null) {
				return;
			}
			IllusionToOwnerProcedure.execute(entity, illusioner);
		}
		if (entity instanceof LivingEntity _livingEntity7 && _livingEntity7.getAttributes().hasAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()))
			_livingEntity7.getAttribute(CaerulaArborModAttributes.GENERAL_DEFENSE.get()).setBaseValue(0);
	}
}
