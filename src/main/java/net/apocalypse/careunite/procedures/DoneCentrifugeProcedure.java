package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;

public class DoneCentrifugeProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		String selection = "";
		ItemStack res = ItemStack.EMPTY;
		if ((entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).getItem() == CaerulaArborModItems.HUNTER_GENE.get()) {
			selection = entity.getPersistentData().getString("centrifugerSelection");
			if ((selection).equals("skadi")) {
				res = new ItemStack(CaerulaArborModItems.HUNTER_GENE_SKADI.get()).copy();
			} else if ((selection).equals("ulpians")) {
				res = new ItemStack(CaerulaArborModItems.HUNTER_GENE_ULPIANS.get()).copy();
			} else if ((selection).equals("gladiia")) {
				res = new ItemStack(CaerulaArborModItems.HUNTER_GENE_GLADIIA.get()).copy();
			} else if ((selection).equals("specter")) {
				res = new ItemStack(CaerulaArborModItems.HUNTER_GENE_SPECTER.get()).copy();
			}
			if (!(res.getItem() == ItemStack.EMPTY.getItem())) {
				(entity instanceof LivingEntity _livEnt ? _livEnt.getMainHandItem() : ItemStack.EMPTY).shrink(1);
				if (entity instanceof Player _player)
					_player.closeContainer();
				{
					int _value = 1;
					BlockPos _pos = BlockPos.containing(x, y, z);
					BlockState _bs = world.getBlockState(_pos);
					if (_bs.getBlock().getStateDefinition().getProperty("animation") instanceof IntegerProperty _integerProp && _integerProp.getPossibleValues().contains(_value))
						world.setBlock(_pos, _bs.setValue(_integerProp, _value), 3);
				}
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:notice")), SoundSource.BLOCKS, 2, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:notice")), SoundSource.BLOCKS, 2, 1, false);
					}
				}
				if (world instanceof ServerLevel _level) {
					ItemEntity entityToSpawn = new ItemEntity(_level, (x + 0.5), (y + 1), (z + 0.5), res);
					entityToSpawn.setPickUpDelay(10);
					entityToSpawn.setUnlimitedLifetime();
					_level.addFreshEntity(entityToSpawn);
				}
			}
		}
	}
}
