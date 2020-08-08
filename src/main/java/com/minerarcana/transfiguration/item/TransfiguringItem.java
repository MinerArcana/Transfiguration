package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationContainer;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;

import javax.annotation.Nonnull;

public class TransfiguringItem extends Item {
    private final TransfigurationType type;

    public TransfiguringItem(TransfigurationType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    @Nonnull
    public ActionResultType onItemUse(@Nonnull ItemUseContext context) {
        BlockTransfigurationContainer blockTransfigurationContainer = new BlockTransfigurationContainer(context);
        return context.getWorld().getRecipeManager().getRecipe(type.getBlockRecipeType(), blockTransfigurationContainer, context.getWorld())
                .map(blockTransfigurationRecipe -> blockTransfigurationRecipe.transfigure(blockTransfigurationContainer))
                .orElse(ActionResultType.PASS);
    }
}
