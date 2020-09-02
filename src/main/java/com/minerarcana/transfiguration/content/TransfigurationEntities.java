package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.entity.TransfiguringProjectile;
import com.minerarcana.transfiguration.item.TransfiguringProjectileItem;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;

public class TransfigurationEntities {
    public static final RegistryEntry<EntityType<TransfiguringProjectile>> TRANSFIGURING_PROJECTILE =
            Transfiguration.getRegistrate()
                    .object("transfiguring_projectile")
                    .<TransfiguringProjectile>entity(TransfiguringProjectile::new, EntityClassification.MISC)
                    .lang("Transfiguring Projectile")
                    .register();

    public static final RegistryEntry<TransfiguringProjectileItem> TRANSFIGURING_PROJECTILE_ITEM =
            Transfiguration.getRegistrate()
                    .object("transfiguring_projectile")
                    .item(TransfiguringProjectileItem::new)
                    .model((context, modelProvider) -> modelProvider.generated(context, new ResourceLocation("item/fire_charge")))
                    .color(() -> () -> TransfiguringProjectileItem::getColor)
                    .register();

    public static void setup() {

    }
}