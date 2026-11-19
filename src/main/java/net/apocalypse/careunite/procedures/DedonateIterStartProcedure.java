package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;

import java.util.Comparator;

public class DedonateIterStartProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z) {
		boolean consume = false;
		Entity player = null;
		for (Direction directioniterator : Direction.values()) {
			if (DedonateIterProcedure.execute(world, x + directioniterator.getStepX(), y + directioniterator.getStepY(), z + directioniterator.getStepZ(), false, 256)) {
				consume = true;
			}
		}
		if (consume) {
			world.setBlock(BlockPos.containing(x, y, z), Blocks.AIR.defaultBlockState(), 3);
			if (world instanceof Level _level) {
				if (!_level.isClientSide()) {
					_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dragon_fireball.explode")), SoundSource.BLOCKS, 3, 1);
				} else {
					_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.dragon_fireball.explode")), SoundSource.BLOCKS, 3, 1, false);
				}
			}
		} else {
			player = (Entity) world.getEntitiesOfClass(Player.class, AABB.ofSize(new Vec3(x, y, z), 32, 32, 32), e -> true).stream().sorted(new Object() {
				Comparator<Entity> compareDistOf(double _x, double _y, double _z) {
					return Comparator.comparingDouble(_entcnd -> _entcnd.distanceToSqr(_x, _y, _z));
				}
			}.compareDistOf(x, y, z)).findFirst().orElse(null);
			if (player instanceof Player _player && !_player.level().isClientSide())
				_player.displayClientMessage(Component.literal((Component.translatable("block.caerula_arbor_reunite.sea_prairie_bomb.no_func").getString())), true);
		}
	}
}
