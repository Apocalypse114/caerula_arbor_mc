package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingHurtEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModEntities;
import net.apocalypse.careunite.entity.NetherseaSlimeEntity;

import javax.annotation.Nullable;
import net.minecraft.world.damagesource.DamageType;

@Mod.EventBusSubscriber
public class SlimeFUncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingHurtEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity) {
		execute(null, world, x, y, z, damagesource, entity);
	}

	public static final ResourceKey<DamageType> nethersea = ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:trail_damage"));

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity) {
		if (damagesource == null || entity == null)
			return;
		if (damagesource.is(nethersea)) {
			if (entity instanceof Slime slime) {
				if (slime.getHealth() <= slime.getMaxHealth() * 0.5
					|| slime.getHealth()<= 1) {
					int size = slime.getSize();
					if (world instanceof ServerLevel _level) {
						NetherseaSlimeEntity entityToSpawn = CaerulaArborModEntities.NETHERSEA_SLIME.get().create(_level);
						if (entityToSpawn != null){
							entityToSpawn.setPos(x,y,z);
							entityToSpawn.getEntityData().set(NetherseaSlimeEntity.DATA_SIZE, size);
							entityToSpawn.setYRot(entity.getYRot());
							entity.discard();
							_level.addFreshEntity(entityToSpawn);
						}
					}
				}
			}
		}
		if ((damagesource.getEntity()) instanceof NetherseaSlimeEntity) {
			entity.invulnerableTime = 0;
		}
	}
}
