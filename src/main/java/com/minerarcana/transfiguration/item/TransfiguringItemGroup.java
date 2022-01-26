package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nonnull;

public class TransfiguringItemGroup extends CreativeModeTab {
    public TransfiguringItemGroup() {
        super("transfiguration");
    }

    @Override
    @Nonnull
    public ItemStack makeIcon() {
        return TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM
                .map(item -> item.withTransfigurationType(TransfigurationTypes.NETHERI.get()))
                .orElse(ItemStack.EMPTY);
    }
}
