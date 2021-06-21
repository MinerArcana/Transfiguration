package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.entity.BlockTransfiguringEntity;
import com.minerarcana.transfiguration.entity.TransfiguringProjectileEntity;
import com.minerarcana.transfiguration.item.TransfiguringProjectileItem;
import com.minerarcana.transfiguration.renderer.TransfiguringEntityRenderer;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ResourceLocation;

public class TransfigurationEntities {
    public static final RegistryEntry<EntityType<TransfiguringProjectileEntity>> TRANSFIGURING_PROJECTILE =
            Transfiguration.getRegistrate()
                    .object("transfiguring_projectile")
                    .<TransfiguringProjectileEntity>entity(TransfiguringProjectileEntity::new, EntityClassification.MISC)
                    .lang("Transfiguring Projectile")
                    .register();

    public static final RegistryEntry<TransfiguringProjectileItem> TRANSFIGURING_PROJECTILE_ITEM =
            Transfiguration.getRegistrate()
                    .object("transfiguring_projectile")
                    .item(TransfiguringProjectileItem::new)
                    .model((context, modelProvider) -> modelProvider.generated(context, new ResourceLocation("item/fire_charge")))
                    .color(() -> () -> TransfiguringProjectileItem::getColor)
                    .register();

    public static final RegistryEntry<EntityType<BlockTransfiguringEntity>> BLOCK_TRANSFIGURING =
            Transfiguration.getRegistrate()
                    .object("block_transfiguring")
                    .<BlockTransfiguringEntity>entity(BlockTransfiguringEntity::new, EntityClassification.MISC)
                    .lang("Transfiguration")
                    .renderer(() -> renderManager -> new TransfiguringEntityRenderer<>(renderManager,
                            Minecraft.getInstance().getItemRenderer()))
                    .properties(properties -> properties.immuneToFire().size(0.5F, 0.1F))
                    .register();

    public static void setup() {

    }
}