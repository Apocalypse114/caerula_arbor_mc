package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;
import net.minecraft.util.RandomSource;
import net.minecraft.util.Mth;

import net.apocalypse.careunite.entity.FakeOffspringEntity;

public class FakeeggInitProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof FakeOffspringEntity _datEntSetI)
			_datEntSetI.getEntityData().set(FakeOffspringEntity.DATA_dx, Mth.nextInt(RandomSource.create(), -50, 50));
		if (entity instanceof FakeOffspringEntity _datEntSetI)
			_datEntSetI.getEntityData().set(FakeOffspringEntity.DATA_dz, Mth.nextInt(RandomSource.create(), -50, 50));
	}
}
