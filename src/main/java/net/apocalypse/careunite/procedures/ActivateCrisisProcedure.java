package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.tags.TagKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.BlockPos;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.Advancement;

import net.apocalypse.careunite.init.CaerulaArborModBlocks;
import net.apocalypse.careunite.CaerulaArborMod;

import java.util.List;
import java.util.Comparator;

public class ActivateCrisisProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, BlockState blockstate, Entity entity) {
		if (entity == null)
			return InteractionResult.PASS;
		if ((blockstate.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip1 ? blockstate.getValue(_getip1) : -1) == 0) {
			{
				int _value = 1;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
			{
				int _value = 1;
				BlockPos _pos = BlockPos.containing(x, y, z);
				BlockState _bs = world.getBlockState(_pos);
				if (_bs.getBlock().getStateDefinition().getProperty("animation") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
					world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
			}
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:start")), SoundSource.BLOCKS, 1, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:start")), SoundSource.BLOCKS, 1, 1, false);
				}
			}
			CaerulaArborMod.queueServerWork(25, () -> {
				if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == CaerulaArborModBlocks.CRISIS_TABLE.get()
						&& ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip8
								? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip8)
								: -1) == 1) {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("crisis_table.log_0").getString())), false);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:notice")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:notice")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
				}
			});
			CaerulaArborMod.queueServerWork(45, () -> {
				if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == CaerulaArborModBlocks.CRISIS_TABLE.get()
						&& ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip16
								? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip16)
								: -1) == 1) {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("crisis_table.log_1").getString())), false);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:notice")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:notice")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
				}
			});
			CaerulaArborMod.queueServerWork(55, () -> {
				if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == CaerulaArborModBlocks.CRISIS_TABLE.get()
						&& ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip24
								? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip24)
								: -1) == 1) {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("crisis_table.log_2").getString())), false);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:notice")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:notice")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
				}
			});
			CaerulaArborMod.queueServerWork(65, () -> {
				if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == CaerulaArborModBlocks.CRISIS_TABLE.get()
						&& ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip32
								? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip32)
								: -1) == 1) {
					CrisisDeployProcedure.execute(world, x, y, z, blockstate);
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("crisis_table.log_3").getString())), false);
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:alert")), SoundSource.BLOCKS, 3, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:alert")), SoundSource.BLOCKS, 3, 1, false);
						}
					}
				}
			});
			CaerulaArborMod.queueServerWork(95, () -> {
				if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == CaerulaArborModBlocks.CRISIS_TABLE.get()
						&& ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip40
								? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip40)
								: -1) == 1) {
					if (entity instanceof Player _player && !_player.level().isClientSide())
						_player.displayClientMessage(Component.literal((Component.translatable("crisis_table.log_4").getString())), false);
					if (entity instanceof ServerPlayer _player) {
						Advancement _adv = _player.server.getAdvancements().getAdvancement(new ResourceLocation("caerula_arbor_reunite:operation_deepness"));
						AdvancementProgress _ap = _player.getAdvancements().getOrStartProgress(_adv);
						if (!_ap.isDone()) {
							for (String criteria : _ap.getRemainingCriteria())
								_player.getAdvancements().award(_adv, criteria);
						}
					}
				}
				{
					final Vec3 _center = new Vec3(x, y, z);
					List<Entity> _entfound = world.getEntitiesOfClass(Entity.class, new AABB(_center, _center).inflate(12 / 2d), e -> true).stream().sorted(Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_center))).toList();
					for (Entity entityiterator : _entfound) {
						if (entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanoffspring")))
								&& !entityiterator.getType().is(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("caerula_arbor_reunite:oceanpet")))) {
							if (entityiterator instanceof Mob _entity && entity instanceof LivingEntity _ent)
								_entity.setTarget(_ent);
						}
					}
				}
			});
			CaerulaArborMod.queueServerWork(105, () -> {
				if ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock() == CaerulaArborModBlocks.CRISIS_TABLE.get()
						&& ((world.getBlockState(BlockPos.containing(x, y, z))).getBlock().getStateDefinition().getProperty("blockstate") instanceof IntegerProperty _getip52
								? (world.getBlockState(BlockPos.containing(x, y, z))).getValue(_getip52)
								: -1) == 1) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:quit")), SoundSource.BLOCKS, 1, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:quit")), SoundSource.BLOCKS, 1, 1, false);
						}
					}
				}
			});
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
