package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.OceanizedEndermanEntity;
import net.apocalypse.careunite.entity.CreeperFishEntity;

import java.util.Comparator;
import net.minecraft.world.scores.Team;

public class EndermnaPickCreeperProcedure {

	public static boolean checkSameTeam(Entity a, Entity b){
		if (a == null || b == null)
			return false;
		Team at = a.getTeam();
		Team bt = b.getTeam();
		if (at == null) return bt == null;
        return at.isAlliedTo(bt);
	}

	private static boolean isUnamed(Entity entity){
		return entity.getDisplayName().getString().equals(entity.getType().getDescription().getString());
	}

	private static Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
		return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
	}

	
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		CreeperFishEntity ravager = null;
		if (!entity.isAlive()) {
			return;
		}
		if (entity instanceof OceanizedEndermanEntity _datEntL1 && _datEntL1.getEntityData().get(OceanizedEndermanEntity.DATA_HOLDING_CrEEPER)) {
			return;
		}
		if (entity.tickCount % 40 == 10 && Math.random() < 0.67) {
			ravager = world.getEntitiesOfClass(CreeperFishEntity.class, 
				AABB.ofSize(new Vec3(x, y, z), 48, 48, 48), e -> isUnamed(e) && checkSameTeam(entity, e))
			.stream().sorted(compareDistOf(x, y, z)).findFirst().orElse(null);
			if (!(ravager == null) && ravager.isAlive()) {
				if (entity instanceof Mob _entity)
					_entity.getNavigation().moveTo((ravager.getX()), (ravager.getY()), (ravager.getZ()), 1);
			}
		}
		if (entity.tickCount % 20 == 10) {
			ravager = world.getEntitiesOfClass(CreeperFishEntity.class, 
				AABB.ofSize(new Vec3(x, y, z), 5, 5, 5), e -> isUnamed(e) && checkSameTeam(entity, e))
			.stream().sorted(compareDistOf(x, y, z)).findFirst().orElse(null);
			if (!(ravager == null) && ravager.isAlive()) {
				if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
					_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10, 9, false, false));
				if (!ravager.level().isClientSide())
					ravager.discard();
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather")), SoundSource.HOSTILE, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather")), SoundSource.HOSTILE, 2, 1, false);
					}
				}
				if (entity instanceof OceanizedEndermanEntity _datEntSetL)
{
					_datEntSetL.getEntityData().set(OceanizedEndermanEntity.DATA_HOLDING_CrEEPER, true);
					if (ravager.charged()) _datEntSetL.setCreeperCharged();
				}
			}
		}
	}
}
