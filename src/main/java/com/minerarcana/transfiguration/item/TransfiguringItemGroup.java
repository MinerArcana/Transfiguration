package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.content.TransfigurationItems;
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
        return TransfigurationItems.NETHERI_DUST
                .map(ItemStack::new)
                .orElse(ItemStack.EMPTY);
    }
}
