package net.apocalypse.careunite.procedures;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.apocalypse.careunite.CaerulaArborMod;

public class CrisisDeployProcedure {
	public static void execute(LevelAccessor world, double x, double y, double z, BlockState blockstate) {
		double creeper = 0;
		double gap = 0;
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				CrisisSummonSliderProcedure.execute(world, x, y, z, blockstate);
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, 8, 3);
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				CrisisSummonShooterProcedure.execute(world, x, y, z, blockstate);
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, 5, 6);
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				CrisisSummonReaperProcedure.execute(world, x, y, z, blockstate);
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, 3, 10);
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				CrisiSummonFirstProcedure.execute(world, x, y, z, blockstate);
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, 2, 15);
		creeper = Mth.nextInt(RandomSource.create(), 2, 6);
		gap = Math.round(30 / creeper);
		new Object() {
			void timedLoop(int timedloopiterator, int timedlooptotal, int ticks) {
				CrisisSummonCreeperProcedure.execute(world, x, y, z, blockstate);
				final int tick2 = ticks;
				CaerulaArborMod.queueServerWork(tick2, () -> {
					if (timedlooptotal > timedloopiterator + 1) {
						timedLoop(timedloopiterator + 1, timedlooptotal, tick2);
					}
				});
			}
		}.timedLoop(0, (int) creeper, (int) gap);
	}
}
