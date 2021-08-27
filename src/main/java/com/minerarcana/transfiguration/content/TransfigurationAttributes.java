package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;

import java.util.UUID;

public class TransfigurationAttributes {
    public static final UUID POWER_UUID = UUID.fromString("febfa6cf-5308-4513-bed2-e07a981e3efd");
    public static final RegistryEntry<Attribute> POWER_MODIFIER = Transfiguration.getRegistrate()
            .object("power")
            .simple(Attribute.class, () -> new RangedAttribute(
                    "transfiguration.power",
                    0.0D,
                    0.0D,
                    Double.MAX_VALUE
            ).setShouldWatch(true));

    public static final UUID TIME_UUID = UUID.fromString("4d98ef73-cb31-41ca-bf41-113d3d6109f9");
    public static final RegistryEntry<Attribute> TIME_MODIFIER = Transfiguration.getRegistrate()
            .object("time")
            .simple(Attribute.class, () -> new RangedAttribute(
                    "transfiguration.time",
                    0.0D,
                    0.0D,
                    Double.MAX_VALUE
            ).setShouldWatch(true));

    public static void setup(IEventBus modBus) {
        modBus.addListener(TransfigurationAttributes::registerPlayerAttributes);
    }

    private static void registerPlayerAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, POWER_MODIFIER.get());
        event.add(EntityType.PLAYER, TIME_MODIFIER.get());
    }
}
