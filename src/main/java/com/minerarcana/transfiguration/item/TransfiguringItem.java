package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationContainer;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public abstract class TransfiguringItem extends Item implements ITransfiguring {
    private final Supplier<TransfigurationType> type;

    public TransfiguringItem(Supplier<TransfigurationType> type, Properties properties) {
        super(properties);
        this.type = type;
    }

    @Override
    @Nonnull
    public ActionResultType onItemUse(@Nonnull ItemUseContext context) {
        BlockTransfigurationContainer blockTransfigurationContainer = new BlockTransfigurationContainer(context);
        ActionResultType resultType = context.getWorld().getRecipeManager().getRecipe(this.getType().getBlockRecipeType(),
                blockTransfigurationContainer, context.getWorld())
                .map(blockTransfigurationRecipe -> blockTransfigurationRecipe.transfigure(blockTransfigurationContainer))
                .orElse(ActionResultType.PASS);
        if (resultType.isSuccessOrConsume()) {
            if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                this.afterTransfiguration(context.getItem(), context.getPlayer(), context.getHand());
            }
        }
        return resultType;
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName(@Nonnull ItemStack itemStack) {
        return new TranslationTextComponent(this.getDefaultTranslationKey(), this.getType().getDisplayName());
    }

    @Override
    public TransfigurationType getType() {
        return type.get();
    }
}
