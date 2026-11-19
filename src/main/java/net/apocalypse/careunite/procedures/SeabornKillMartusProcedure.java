package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.MartusEntity;

import javax.annotation.Nullable;

import java.util.Comparator;

@Mod.EventBusSubscriber
public class SeabornKillMartusProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getEntity(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		execute(null, world, x, y, z, entity, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null)
			return;
		Entity martus = null;
		if (event.isCanceled()) {
			return;
		}
		if (entity instanceof MartusEntity) {
			return;
		}
		martus = (Entity) world.getEntitiesOfClass(MartusEntity.class, AABB.ofSize(new Vec3(x, y, z), 96, 96, 96), e -> true).stream().sorted(new Object() {
			Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
				return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
			}
		}.compareDistOf(x, y, z)).findFirst().orElse(null);
		if (martus == null) {
			return;
		}
		if (entity.getPersistentData().getBoolean("blessed")) {
			HurtMartusProcedure.execute(world, martus, sourceentity, Math.max(Math.min((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.25, (martus instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.4),
					(martus instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.05), 0);
		} else if (entity.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring"))) && (martus instanceof MartusEntity _datEntI ? _datEntI.getEntityData().get(MartusEntity.DATA_phase) : 0) >= 1) {
			HurtMartusProcedure.execute(world, martus, sourceentity, Math.max(Math.min((entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.03, (martus instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.025),
					(martus instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1) * 0.018), 0);
		}
	}
}
