package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.entity.MegaChestEntity;

import java.util.Map;

public class MegaChestInitProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		if (!(entity instanceof MegaChestEntity _datEntL0 && _datEntL0.getEntityData().get(MegaChestEntity.DATA_released))) {
			entity.setShiftKeyDown(true);
			if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
				_entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20, 8, false, false));
		}
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (!(enemy == null) && enemy.isAlive()) {
			return;
		}
		if (!world.isClientSide()) {
			if ((entity instanceof LivingEntity _entity ? _entity.getNoActionTime() : 0) >= 1200) {
				if (entity.isAlive()) {
					if ((world.getBlockState(BlockPos.containing(x, y, z))).canBeReplaced()) {
						if (!entity.level().isClientSide())
							entity.discard();
						{
							BlockPos _bp = BlockPos.containing(x, y, z);
							BlockState _bs = (new Object() {
								public BlockState with(BlockState _bs, Direction newValue) {
									Property<?> _prop = _bs.getBlock().getStateDefinition().getProperty("facing");
									if (_prop instanceof DirectionProperty _dp && _dp.getPossibleValues().contains(newValue))
										return _bs.setValue(_dp, newValue);
									_prop = _bs.getBlock().getStateDefinition().getProperty("axis");
									return _prop instanceof EnumProperty _ep && _ep.getPossibleValues().contains(newValue.getAxis()) ? _bs.setValue(_ep, newValue.getAxis()) : _bs;
								}
							}.with(CaerulaArborModBlocks.CHESTMEGA_SPAWNER.get().defaultBlockState(), (entity.getDirection())));
							BlockState _bso = world.getBlockState(_bp);
							for (Map.Entry<Property<?>, Comparable<?>> entry : _bso.getValues().entrySet()) {
								Property _property = _bs.getBlock().getStateDefinition().getProperty(entry.getKey().getName());
								if (_property != null && _bs.getValue(_property) != null)
									try {
										_bs = _bs.setValue(_property, (Comparable) entry.getValue());
									} catch (Exception e) {
									}
							}
							world.setBlock(_bp, _bs, 3);
						}
						if (world instanceof Level _level) {
							if (!_level.isClientSide()) {
								_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.ender_chest.close")), SoundSource.BLOCKS, 1, 1);
							} else {
								_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("block.ender_chest.close")), SoundSource.BLOCKS, 1, 1, false);
							}
						}
					}
				}
			}
		}
	}
}
