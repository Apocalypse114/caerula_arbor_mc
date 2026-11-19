package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.particles.ParticleTypes;

public class EmployWildInquisitionProcedure {
	public static boolean execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity, ItemStack required) {
		if (entity == null || sourceentity == null)
			return false;
		if (entity instanceof TamableAnimal _tamEnt ? _tamEnt.isTame() : false) {
			return false;
		}
		if ((sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == required.getItem()) {
			if (entity instanceof TamableAnimal _toTame && sourceentity instanceof Player _owner)
				_toTame.tame(_owner);
			if (world instanceof ServerLevel _level)
				_level.sendParticles(ParticleTypes.HAPPY_VILLAGER, x, (y + 0.75), z, 16, 0.75, 0.75, 0.75, 0.1);
			(sourceentity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
			return true;
		}
		return false;
	}
}
