package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
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
        TransfigurationContainer<BlockState> blockTransfigurationContainer = new TransfigurationContainer<>(
                context.getWorld().getBlockState(context.getPos()), context.getPlayer(), context.getWorld(),
                context.getPos());
        ActionResultType resultType = context.getWorld().getRecipeManager().getRecipe(
                this.getType(context.getItem()).getBlockRecipeType(), blockTransfigurationContainer, context.getWorld())
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
        return new TranslationTextComponent(this.getDefaultTranslationKey(), this.getType(itemStack).getDisplayName());
    }

    @Override
    public TransfigurationType getType(ItemStack itemStack) {
        return type.get();
    }
}
