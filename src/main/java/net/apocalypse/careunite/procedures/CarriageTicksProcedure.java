package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.commands.arguments.EntityAnchorArgument;

import net.apocalypse.careunite.entity.PumpkinCarriageEntity;

public class CarriageTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double sprT = 0;
		Entity enemy = null;
		if (entity.isAlive()) {
			sprT = entity instanceof PumpkinCarriageEntity _datEntI ? _datEntI.getEntityData().get(PumpkinCarriageEntity.DATA_SPRINT_T) : 0;
		}
		if (sprT > 0) {
			if (entity instanceof PumpkinCarriageEntity _datEntSetI)
				_datEntSetI.getEntityData().set(PumpkinCarriageEntity.DATA_SPRINT_T, (int) (sprT - 1));
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (sprT == 19) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:carriage_back")), SoundSource.NEUTRAL, (float) 0.75,
								(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:carriage_back")), SoundSource.NEUTRAL, (float) 0.75, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
					}
				}
			}
			if (sprT > 13) {
				if (!(enemy == null) && enemy.isAlive()) {
					entity.lookAt(EntityAnchorArgument.Anchor.EYES, new Vec3((enemy.getX()), (enemy.getY() + enemy.getBbHeight() * 0.5), (enemy.getZ())));
				}
			} else if (sprT == 13) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:carriage_sprint")), SoundSource.NEUTRAL, (float) 0.75,
								(float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1));
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:carriage_sprint")), SoundSource.NEUTRAL, (float) 0.75, (float) Mth.nextDouble(RandomSource.create(), 0.9, 1.1), false);
					}
				}
				PushCarriageProcedure.execute(entity);
				CarriageRangedProcedure.execute(world, x, y, z, entity,
						entity instanceof LivingEntity _livingEntity15 && _livingEntity15.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity15.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0, 2.5);
			} else if (sprT >= 5) {
				CarriageRangedProcedure.execute(world, x, y, z, entity,
						entity instanceof LivingEntity _livingEntity16 && _livingEntity16.getAttributes().hasAttribute(Attributes.ATTACK_DAMAGE) ? _livingEntity16.getAttribute(Attributes.ATTACK_DAMAGE).getValue() : 0, 2.5);
			}
		}
	}
}
