package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.init.CaerulaArborModMobEffects;
import net.apocalypse.careunite.entity.FirstTellerEntity;

public class TellerSkillreleaseProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		Entity enemy = null;
		double sklp = 0;
		if (!entity.isAlive()) {
			return;
		}
		sklp = entity instanceof FirstTellerEntity _datEntI ? _datEntI.getEntityData().get(FirstTellerEntity.DATA_sklp) : 0;
		if (entity instanceof FirstTellerEntity _datEntSetI)
			_datEntSetI.getEntityData().set(FirstTellerEntity.DATA_sklp, (int) (sklp + 1));
		if (CaerulaArborModVariables.MapVariables.get(world).strategy_grow >= 4 && sklp < 0) {
			if (entity instanceof FirstTellerEntity _datEntSetI)
				_datEntSetI.getEntityData().set(FirstTellerEntity.DATA_sklp, (int) (sklp + 1));
		}
		enemy = entity instanceof Mob _mobEnt ? (Entity) _mobEnt.getTarget() : null;
		if (!(enemy == null) && enemy.isAlive()) {
			if (sklp >= 400) {
				if (!world.isClientSide() && (enemy != null ? entity.distanceTo(enemy) : -1) <= 8) {
					if (world instanceof Level _level) {
						if (!_level.isClientSide()) {
							_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:firstteller_skill")), SoundSource.HOSTILE, (float) 2.4, 1);
						} else {
							_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:firstteller_skill")), SoundSource.HOSTILE, (float) 2.4, 1, false);
						}
					}
					if (entity instanceof LivingEntity _entity && !_entity.level().isClientSide())
						_entity.addEffect(new MobEffectInstance(CaerulaArborModMobEffects.INVULNERABLE.get(), 65, 0, false, false));
					if (entity instanceof FirstTellerEntity) {
						((FirstTellerEntity) entity).setAnimation("animation.firstspeak.skill");
					}
					if (entity instanceof FirstTellerEntity _datEntSetI)
						_datEntSetI.getEntityData().set(FirstTellerEntity.DATA_sklp, -60);
				}
			} else if (sklp < 0 && sklp >= -50) {
				if (sklp % 10 == 0) {
					FirstTellerAttackProcedure.execute(world, enemy.getX(), enemy.getY(), enemy.getZ(), enemy, entity);
				}
			}
		}
	}
}
