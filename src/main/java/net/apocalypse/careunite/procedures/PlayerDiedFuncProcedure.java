package net.apocalypse.careunite.procedures;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.event.entity.living.LivingDeathEvent;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModBlocks;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class PlayerDiedFuncProcedure {
	@SubscribeEvent
	public static void onEntityDeath(LivingDeathEvent event) {
		if (event != null && event.getEntity() != null) {
			execute(event, event.getEntity().level(), event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), event.getSource(), event.getEntity());
		}
	}

	public static void execute(LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity) {
		execute(null, world, x, y, z, damagesource, entity);
	}

	private static void execute(@Nullable Event event, LevelAccessor world, double x, double y, double z, DamageSource damagesource, Entity entity) {
		if (damagesource == null || entity == null)
			return;
		boolean death_blocked = false;
		double light_cost = 0;
		double dx = 0;
		double dz = 0;
		double dy = 0;
		double num = 0;
		if (CaerulaArborModVariables.MapVariables.get(world).strategy_breed >= 3) {
			if (!damagesource.is(DamageTypes.GENERIC_KILL)) {
				dx = -1;
				num = Math.round(0.25 * Math.sqrt(entity instanceof LivingEntity _livEnt ? _livEnt.getMaxHealth() : -1));
				light_cost = 1;
				if (num > 4) {
					light_cost = 1;
				}
				if (num > 8) {
					num = 8;
					light_cost = 2;
				}
				if (num > 0) {
					dx = (-1) * num;
					for (int index0 = 0; index0 < (int) (2 * num); index0++) {
						dz = (-1) * num;
						for (int index1 = 0; index1 < (int) (2 * num); index1++) {
							dy = (-1) * num;
							for (int index2 = 0; index2 < (int) (2 * num); index2++) {
								if (light_cost <= 0) {
									return;
								}
								if ((world.getBlockState(BlockPos.containing(x + dx, y + dy, z + dz))).getBlock() == CaerulaArborModBlocks.OCEAN_OVARY.get()) {
									if (1 == ((world.getBlockState(BlockPos.containing(x + dx, y + dy, z + dz))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip5
											? (world.getBlockState(BlockPos.containing(x + dx, y + dy, z + dz))).getValue(_getip5)
											: -1)) {
										{
											int _value = 0;
											BlockPos _pos = BlockPos.containing(x + dx, y + dy, z + dz);
											BlockState _bs = world.getBlockState(_pos);
											if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
												world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
										}
										light_cost = light_cost - 1;
									}
								}
								dy = dy + 1;
							}
							dz = dz + 1;
						}
						dx = dx + 1;
					}
				}
			}
		}
	}
}
