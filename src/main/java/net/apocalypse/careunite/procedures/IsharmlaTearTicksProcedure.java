package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.entity.IsharmlaTearEntity;

public class IsharmlaTearTicksProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double dura = 0;
		boolean isAttack = false;
		{
			Entity _ent = entity;
			_ent.setYRot(0);
			_ent.setXRot(0);
			_ent.setYBodyRot(_ent.getYRot());
			_ent.setYHeadRot(_ent.getYRot());
			_ent.yRotO = _ent.getYRot();
			_ent.xRotO = _ent.getXRot();
			if (_ent instanceof LivingEntity _entity) {
				_entity.yBodyRotO = _entity.getYRot();
				_entity.yHeadRotO = _entity.getYRot();
			}
		}
		entity.setDeltaMovement(new Vec3(0, 0, 0));
		if (!entity.isAlive()) {
			return;
		}
		dura = entity instanceof IsharmlaTearEntity _datEntI ? _datEntI.getEntityData().get(IsharmlaTearEntity.DATA_FUNC_COOLDOWN) : 0;
		if (dura > 0) {
			if (entity instanceof IsharmlaTearEntity _datEntSetI)
				_datEntSetI.getEntityData().set(IsharmlaTearEntity.DATA_FUNC_COOLDOWN, (int) (dura - 1));
		} else {
			isAttack = IsharmlaTearHurtProcedure.execute(world, x, y, z, entity);
			if (isAttack || IsharmlaTearSkillRProcedure.execute(world, x, y, z)) {
				if (entity instanceof IsharmlaTearEntity) {
					((IsharmlaTearEntity) entity).setAnimation("animation.isharmla_tear.attack");
				}
				if (entity instanceof IsharmlaTearEntity _datEntSetI)
					_datEntSetI.getEntityData().set(IsharmlaTearEntity.DATA_FUNC_COOLDOWN, 60);
				if (isAttack) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_tear_hurt_1")), SoundSource.HOSTILE, 2, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_tear_hurt_1")), SoundSource.HOSTILE, 2, 1, false);
						}
					}
				} else {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_tear_hurt_0")), SoundSource.HOSTILE, 2, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:isharmla_tear_hurt_0")), SoundSource.HOSTILE, 2, 1, false);
						}
					}
				}
			}
		}
	}
}
