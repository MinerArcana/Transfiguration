package com.minerarcana.transfiguration.compat.cctweaked.turtle;

import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.item.TransfiguringWandItem;
import com.minerarcana.transfiguration.transfiguring.TransfigurationType;
import com.minerarcana.transfiguration.util.ResourceLocationHelper;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dan200.computercraft.api.client.TransformedModel;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.api.turtle.TurtleUpgradeType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class TransfiguringTurtleUpgrade implements ITurtleUpgrade {
    private final RegistryEntry<TransfigurationType> transfigurationType;
    private final NonNullLazy<ItemStack> itemStack;

    public TransfiguringTurtleUpgrade(RegistryEntry<TransfigurationType> transfigurationTypeSupplier) {
        this.transfigurationType = transfigurationTypeSupplier;
        this.itemStack = NonNullLazy.of(this::getItemStack);
    }

    @Nonnull
    @Override
    public ResourceLocation getUpgradeID() {
        return Objects.requireNonNull(transfigurationType.getId());
    }

    @Nonnull
    @Override
    public String getUnlocalisedAdjective() {
        return "upgrade.transfiguration.transfiguring.advective";
    }

    @Nonnull
    @Override
    public TurtleUpgradeType getType() {
        return TurtleUpgradeType.TOOL;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingItem() {
        return itemStack.get();
    }

    @Nonnull
    @Override
    public TransformedModel getModel(@Nullable ITurtleAccess iTurtleAccess, @Nonnull TurtleSide turtleSide) {
        float xOffset = turtleSide == TurtleSide.LEFT ? -0.40625f : 0.40625f;
        Matrix4f transform = new Matrix4f(new float[]{
                0.0f, 0.0f, -1.0f, 1.0f + xOffset,
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, -1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 0.0f, 1.0f,
        });
        return TransformedModel.of(getCraftingItem(), new TransformationMatrix(transform));
    }

    @Nonnull
    public ItemStack getItemStack() {
        Item item = ForgeRegistries.ITEMS.getValue(ResourceLocationHelper.append(transfigurationType.get().getRegistryName(),
                "_", "wand"));
        if (item instanceof TransfiguringWandItem) {
            return new ItemStack(item);
        } else {
            return TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM
                    .map(projectileItem -> projectileItem.withTransfigurationType(transfigurationType.get()))
                    .orElse(ItemStack.EMPTY);
        }
    }
}
