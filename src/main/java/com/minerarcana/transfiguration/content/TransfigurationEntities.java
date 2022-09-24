package com.minerarcana.transfiguration.content;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.entity.BlockTransfiguringEntity;
import com.minerarcana.transfiguration.entity.EntityTransfiguringEntity;
import com.minerarcana.transfiguration.entity.TransfiguringAreaEffectEntity;
import com.minerarcana.transfiguration.entity.TransfiguringProjectileEntity;
import com.minerarcana.transfiguration.item.TransfiguringProjectileItem;
import com.minerarcana.transfiguration.registrate.TransfigurationColors;
import com.minerarcana.transfiguration.renderer.TransfiguringEntityRenderer;
import com.minerarcana.transfiguration.renderer.TransfiguringProjectileRenderer;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class TransfigurationEntities {
    public static final RegistryEntry<EntityType<TransfiguringProjectileEntity>> TRANSFIGURING_PROJECTILE =
            Transfiguration.getRegistrate()
                    .object("transfiguring_projectile")
                    .<TransfiguringProjectileEntity>entity(TransfiguringProjectileEntity::new, MobCategory.MISC)
                    .lang("Transfiguring Projectile")
                    .renderer(() -> TransfiguringProjectileRenderer::new)
                    .register();

    public static final RegistryEntry<TransfiguringProjectileItem> TRANSFIGURING_PROJECTILE_ITEM =
            Transfiguration.getRegistrate()
                    .object("transfiguring_projectile")
                    .item(TransfiguringProjectileItem::new)
                    .model((context, provider) -> provider.withExistingParent(context.getName(), provider.modLoc("item/catalyst"))
                            .texture("orb", provider.modLoc("item/wand_orb"))
                    )
                    .color(TransfigurationColors.transfiguringTypeColors(1))
                    .register();

    public static final RegistryEntry<EntityType<AreaEffectCloud>> TRANSFIGURING_AREA_EFFECT =
            Transfiguration.getRegistrate()
                    .object("transfiguring_area_effect")
                    .<AreaEffectCloud>entity(TransfiguringAreaEffectEntity::new, MobCategory.MISC)
                    .properties(properties -> properties.fireImmune()
                            .sized(6.0F, 0.5F)
                            .clientTrackingRange(10)
                            .updateInterval(Integer.MAX_VALUE)
                    )
                    .lang("Transfiguring")
                    .renderer(() -> NoopRenderer::new)
                    .register();

    public static final RegistryEntry<EntityType<BlockTransfiguringEntity>> BLOCK_TRANSFIGURING =
            Transfiguration.getRegistrate()
                    .object("block_transfiguring")
                    .<BlockTransfiguringEntity>entity(BlockTransfiguringEntity::new, MobCategory.MISC)
                    .lang("Transfiguration")
                    .renderer(() -> TransfiguringEntityRenderer::new)
                    .properties(properties -> properties.fireImmune()
                            .sized(1.0F, 1.0F)
                    )
                    .register();

    public static final RegistryEntry<EntityType<EntityTransfiguringEntity>> ENTITY_TRANSFIGURING =
            Transfiguration.getRegistrate()
                    .object("block_transfiguring")
                    .<EntityTransfiguringEntity>entity(EntityTransfiguringEntity::new, MobCategory.MISC)
                    .lang("Transfiguration")
                    .renderer(() -> TransfiguringEntityRenderer::new)
                    .properties(properties -> properties.fireImmune()
                            .sized(1.0F, 1.0F)
                    )
                    .register();


    public static void setup() {

    }
}