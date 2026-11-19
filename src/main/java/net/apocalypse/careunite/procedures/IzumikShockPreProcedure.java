package net.apocalypse.careunite.procedures;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.Entity;
import net.minecraft.sounds.SoundSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;

import net.apocalypse.careunite.network.CaerulaArborModVariables;
import net.apocalypse.careunite.entity.IzumikEntity;
import net.apocalypse.careunite.CaerulaArborMod;

public class IzumikShockPreProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, Entity entity) {
		if (entity == null)
			return;
		double range = 0;
		if (world instanceof Level _level) {
			if (!_level.isClientSide()) {
				_level.playSound(null, BlockPos.containing(x, y, z), ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:izumik_shock")), SoundSource.HOSTILE, (float) 3.5, 1);
			} else {
				_level.playLocalSound(x, y, z, ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("caerula_arbor_reunite:izumik_shock")), SoundSource.HOSTILE, (float) 3.5, 1, false);
			}
		}
		range = 11;
		if (CaerulaArborModVariables.MapVariables.get(world).strategy_grow >= 4) {
			range = 14;
		}
		if ((entity instanceof IzumikEntity _datEntI ? _datEntI.getEntityData().get(IzumikEntity.DATA_phase) : 0) >= 2) {
			range = range + 3;
		}
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				IzumikShokedProcedure.execute(world, x, y, z, entity, (timedloopiterator + 1) * 2);
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, (int) range, 1);
	}
}
