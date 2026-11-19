package net.apocalypse.careunite.core;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.minecraftforge.fml.common.Mod;

import net.apocalypse.careunite.init.CaerulaArborModAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum CAMissNumbProvider implements IEntityComponentProvider {
    INSTANCE;

    public static final ResourceLocation UID  = new ResourceLocation("caerula_arbor_reunite:miss_numb_provider");

    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        Entity entity = entityAccessor.getEntity();
        if (entity instanceof LivingEntity living){
            int miss = (int) getMiss(living);
            int numb = (int) getNumb(living);
            if (miss > 0 || numb > 0){
                iTooltip.add(new CAMissNumbElement(miss, numb));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    private double getMiss(LivingEntity living){
        AttributeInstance instance = living.getAttribute(CaerulaArborModAttributes.MISSRATE.get());
        if(instance != null) return instance.getValue();
        return 0;
    }

    private double getNumb(LivingEntity living){
        AttributeInstance instance = living.getAttribute(CaerulaArborModAttributes.NUMB.get());
        if(instance != null) return instance.getBaseValue();
        return 0;
    }
}
