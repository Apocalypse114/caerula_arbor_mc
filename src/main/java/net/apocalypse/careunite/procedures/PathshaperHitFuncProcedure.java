package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingAttackEvent;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;

import net.apocalypse.careunite.entity.RouteShaperEntity;
import net.apocalypse.careunite.entity.RouteFractalEntity;
import net.apocalypse.careunite.entity.LingeringFractalEntity;
import net.apocalypse.careunite.entity.LineringPathshaperEntity;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class PathshaperHitFuncProcedure {
	@SubscribeEvent
	public static void onEntityAttacked(LivingAttackEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getSource().getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity sourceentity) {
		execute(null, world, x, y, z, damagesource, sourceentity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity sourceentity) {
		if (damagesource == null || sourceentity == null)
			return;
		double sklp = 0;
		if (event.isCanceled()) {
			return;
		}
		if (damagesource.is(DamageTypes.MOB_ATTACK) || damagesource.is(ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation("caerula_arbor_reunite:general_seaborn_attack")))) {
			if (sourceentity instanceof RouteShaperEntity) {
				sklp = sourceentity instanceof RouteShaperEntity _datEntI ? _datEntI.getEntityData().get(RouteShaperEntity.DATA_skillp) : 0;
				if (sourceentity instanceof RouteShaperEntity _datEntSetI)
					_datEntSetI.getEntityData().set(RouteShaperEntity.DATA_skillp, (int) (sklp + 1));
				if (sklp + 1 >= 8) {
					SummonFractalProcedure.execute(world, x, y, z, sourceentity);
					if (sourceentity instanceof RouteShaperEntity _datEntSetI)
						_datEntSetI.getEntityData().set(RouteShaperEntity.DATA_skillp, 0);
				}
			}
			if (sourceentity instanceof RouteFractalEntity) {
				sklp = sourceentity instanceof RouteFractalEntity _datEntI ? _datEntI.getEntityData().get(RouteFractalEntity.DATA_skillp) : 0;
				if (sourceentity instanceof RouteFractalEntity _datEntSetI)
					_datEntSetI.getEntityData().set(RouteFractalEntity.DATA_skillp, (int) (sklp + 1));
				if (sklp + 1 >= 6) {
					SummonFractalProcedure.execute(world, x, y, z, sourceentity);
					if (sourceentity instanceof RouteFractalEntity _datEntSetI)
						_datEntSetI.getEntityData().set(RouteFractalEntity.DATA_skillp, 0);
				}
			}
			if (sourceentity instanceof LineringPathshaperEntity) {
				sklp = sourceentity instanceof LineringPathshaperEntity _datEntI ? _datEntI.getEntityData().get(LineringPathshaperEntity.DATA_skillp) : 0;
				if (sourceentity instanceof LineringPathshaperEntity _datEntSetI)
					_datEntSetI.getEntityData().set(LineringPathshaperEntity.DATA_skillp, (int) (sklp + 1));
				if (sklp + 1 >= 8) {
					SummonFractalProcedure.execute(world, x, y, z, sourceentity);
					if (sourceentity instanceof LineringPathshaperEntity _datEntSetI)
						_datEntSetI.getEntityData().set(LineringPathshaperEntity.DATA_skillp, 0);
				}
			}
			if (sourceentity instanceof LingeringFractalEntity) {
				sklp = sourceentity instanceof LingeringFractalEntity _datEntI ? _datEntI.getEntityData().get(LingeringFractalEntity.DATA_skillp) : 0;
				if (sourceentity instanceof LingeringFractalEntity _datEntSetI)
					_datEntSetI.getEntityData().set(LingeringFractalEntity.DATA_skillp, (int) (sklp + 1));
				if (sklp + 1 >= 6) {
					SummonFractalProcedure.execute(world, x, y, z, sourceentity);
					if (sourceentity instanceof LingeringFractalEntity _datEntSetI)
						_datEntSetI.getEntityData().set(LingeringFractalEntity.DATA_skillp, 0);
				}
			}
		}
	}
}
