package net.apocalypse.careunite.procedures;

import net.minecraftforge.common.ForgeMod;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.GladiiaEntity;

import java.util.Comparator;

public class InitGladiiaWhirlProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity gladiia = null;
		gladiia = (Entity) world.getEntitiesOfClass(GladiiaEntity.class, AABB.ofSize(new Vec3(x, y, z), 48, 48, 48), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (!(gladiia == null)) {
			if (entity instanceof LivingEntity _livingEntity3 && _livingEntity3.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE))
				_livingEntity3.getAttribute(Attributes.ATTACK_DAMAGE)
						.setBaseValue((gladiia instanceof LivingEntity _livingEntity2 && _livingEntity2.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity2.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0));
		}
		if (entity instanceof LivingEntity _livingEntity4 && _livingEntity4.getAttributes().hasAttribute(ForgeMod.ENTITY_GRAVITY.get()))
			_livingEntity4.getAttribute(ForgeMod.ENTITY_GRAVITY.get()).setBaseValue(0);
	}
}
