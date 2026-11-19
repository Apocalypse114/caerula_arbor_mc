package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.InteractionResult;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.init.CaerulaArborModItems;
import net.apocalypse.careunite.entity.IreneEntity;

public class TapIreneProcedure {
	public static InteractionResult execute(LevelAccessor world, double x, double y, double z, Entity entity, Entity sourceentity) {
		if (entity == null || sourceentity == null)
			return InteractionResult.PASS;
		double tap = 0;
		Entity enemy = null;
		if (!entity.isAlive()) {
			return InteractionResult.PASS;
		}
		if ((sourceentity instanceof LivingEntity _entity) ? _entity.isHolding(CaerulaArborModItems.PERSONNEL_TRANSPORTER.get()) : false) {
			return InteractionResult.PASS;
		}
		if (EmployWildInquisitionProcedure.execute(world, x, y, z, entity, sourceentity, new ItemStack(CaerulaArborModItems.TREATY_DIAMOND.get()))) {
			return InteractionResult.SUCCESS;
		}
		tap = entity instanceof IreneEntity _datEntI ? _datEntI.getEntityData().get(IreneEntity.DATA_tapTick) : 0;
		if (tap <= 0) {
			enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
			if (!(enemy == null) && enemy.isAlive()) {
				return InteractionResult.PASS;
			}
			if (!world.isClientSide()) {
				if (world instanceof Level _level) {
					if (!_level.isClientSide()) {
						_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_interact")), SoundSource.NEUTRAL, 3, 1);
					} else {
						_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:irene_interact")), SoundSource.NEUTRAL, 3, 1, false);
					}
				}
			}
			if (entity instanceof IreneEntity) {
				((IreneEntity) entity).setAnimation("animation.irene.interact");
			}
			if (entity instanceof IreneEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IreneEntity.DATA_tapTick, 35);
			if (entity instanceof IreneEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IreneEntity.DATA_duration, 35);
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}
}
