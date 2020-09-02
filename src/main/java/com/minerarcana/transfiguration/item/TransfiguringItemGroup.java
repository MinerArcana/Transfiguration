package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.content.TransfigurationTypes;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public class TransfiguringItemGroup extends ItemGroup {
    public TransfiguringItemGroup() {
        super("transfiguration");
    }

    @Override
    @Nonnull
    public ItemStack createIcon() {
        return TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM
                .map(item -> item.withTransfigurationType(TransfigurationTypes.NETHERI.get()))
                .orElse(ItemStack.EMPTY);
    }
}
