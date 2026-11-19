package net.apocalypse.careunite.procedures;

import net.minecraft.world.entity.Entity;

import net.apocalypse.careunite.entity.Endspeaker2Entity;
import net.apocalypse.careunite.entity.Endspeaker1Entity;
import net.apocalypse.careunite.entity.Endspeaker0Entity;

public class EndspeakerReviveProcedure {
	public static void execute(Entity entity) {
		if (entity == null)
			return;
		if (entity instanceof Endspeaker0Entity) {
			if (entity instanceof Endspeaker0Entity) {
				((Endspeaker0Entity) entity).setAnimation("animation.endspeaker_0.die");
			}
			if (entity instanceof Endspeaker0Entity _datEntSetI)
				_datEntSetI.getEntityData().set(Endspeaker0Entity.DATA_EvolveTime, 300);
		} else if (entity instanceof Endspeaker1Entity) {
			if (entity instanceof Endspeaker1Entity) {
				((Endspeaker1Entity) entity).setAnimation("animation.endspeaker_1.die");
			}
			if (entity instanceof Endspeaker1Entity _datEntSetI)
				_datEntSetI.getEntityData().set(Endspeaker1Entity.DATA_EvolveTime, 300);
		} else if (entity instanceof Endspeaker2Entity) {
			if (entity instanceof Endspeaker2Entity) {
				((Endspeaker2Entity) entity).setAnimation("animation.endspeaker_2.die");
			}
			if (entity instanceof Endspeaker2Entity _datEntSetI)
				_datEntSetI.getEntityData().set(Endspeaker2Entity.DATA_EvolveTime, 300);
			if (entity instanceof Endspeaker2Entity _datEntSetI)
				_datEntSetI.getEntityData().set(Endspeaker2Entity.DATA_duration, 999);
		}
	}
}
