package net.apocalypse.careunite.procedures;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;

import java.util.List;
import java.util.Comparator;

public class CommandInquisitionsProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return;
		String log = "";
		double rand = 0;
		double tX = 0;
		double tZ = 0;
		double num = 0;
		if (!entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:inquisition")))) {
			{
				final Vec3 _center = new Vec3(x, y, z);
				List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(32 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
				for (Entity entityiterator : _entfound) {
					if ((entityiterator instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) == sourceentity || (entityiterator instanceof TamableAnimal _tamEnt ? (Entity) _tamEnt.getOwner() : null) == null) {
						if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:inquisition")))) {
							if (entityiterator instanceof Mob _entity && entity instanceof LivingEntity _ent)
								_entity.setTarget(_ent);
							num = num + 1;
						}
					}
				}
			}
			log = Component.translatable("interphone.dispatch.attack").getString();
			log = log.replace("{num}", "" + Math.round(num));
			log = log.replace("{enemy}", entity.getDisplayName().getString());
			if (sourceentity instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal(log), true);
		}
	}
}
