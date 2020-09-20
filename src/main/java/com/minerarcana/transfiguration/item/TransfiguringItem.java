package com.minerarcana.transfiguration.item;

import com.minerarcana.transfiguration.api.event.TransfigurationEvent;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.MinecraftForge;

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
        TransfigurationContainer<BlockState> blockTransfigurationContainer = TransfigurationContainer.block(
                context.getWorld(), context.getPos(), context.getFace(), context.getPlayer());
        TransfigurationEvent transfigurationEvent = new TransfigurationEvent(blockTransfigurationContainer,
                this.getTimeModifier(), this.getPowerModifier());
        MinecraftForge.EVENT_BUS.post(transfigurationEvent);
        ActionResultType resultType = context.getWorld().getRecipeManager().getRecipe(
                this.getType(context.getItem()).getBlockRecipeType(), blockTransfigurationContainer, context.getWorld())
                .map(blockTransfigurationRecipe -> blockTransfigurationRecipe.transfigure(blockTransfigurationContainer,
                        transfigurationEvent.getCurrentPowerModifier()))
                .orElse(ActionResultType.PASS);
        if (resultType.isSuccessOrConsume()) {
            if (context.getPlayer() != null && !context.getPlayer().isCreative()) {
                this.afterTransfiguration(context.getItem(), context.getPlayer(), context.getHand());
            }
        }
        return resultType;
    }

    public abstract double getTimeModifier();

    public double getPowerModifier() {
        return 1.0D;
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
