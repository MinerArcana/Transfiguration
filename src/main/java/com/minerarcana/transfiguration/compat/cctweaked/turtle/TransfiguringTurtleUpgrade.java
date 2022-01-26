package com.minerarcana.transfiguration.compat.cctweaked.turtle;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.api.util.ResourceLocationHelper;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.entity.TransfiguringProjectileEntity;
import com.minerarcana.transfiguration.item.TransfiguringWandItem;
import com.minerarcana.transfiguration.recipe.block.BlockTransfigurationRecipe;
import com.mojang.math.Matrix4f;
import com.mojang.math.Transformation;
import com.tterrag.registrate.util.entry.RegistryEntry;
import dan200.computercraft.api.client.TransformedModel;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.ITurtleUpgrade;
import dan200.computercraft.api.turtle.TurtleCommandResult;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.api.turtle.TurtleUpgradeType;
import dan200.computercraft.api.turtle.TurtleVerb;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
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
        return TransformedModel.of(getCraftingItem(), new Transformation(transform));
    }

    @Nonnull
    @Override
    public TurtleCommandResult useTool(@Nonnull ITurtleAccess turtle, @Nonnull TurtleSide side, @Nonnull TurtleVerb verb, @Nonnull Direction direction) {
        switch (verb) {
            case DIG:
                return dig(turtle, direction);
            case ATTACK:
                return attack(turtle, direction);
            default:
                return TurtleCommandResult.failure();
        }
    }

    @Nonnull
    private TurtleCommandResult dig(@Nonnull ITurtleAccess turtle, @Nonnull Direction direction) {
        TransfigurationContainer<BlockState> blockTransfigurationContainer = TransfigurationContainer.block(
                turtle.getLevel(), turtle.getPosition().relative(direction), null);

        return BlockTransfigurationRecipe.tryTransfigure(this.transfigurationType.get(), blockTransfigurationContainer, 1.0, 1.0) ?
                TurtleCommandResult.success() : TurtleCommandResult.failure();
    }

    @Nonnull
    private TurtleCommandResult attack(@Nonnull ITurtleAccess turtle, @Nonnull Direction direction) {
        BlockPos position = turtle.getPosition();
        double x = position.getX() + 0.7D * (double) direction.getStepX();
        double y = position.getY() + 0.7D * (double) direction.getStepY();
        double z = position.getZ() + 0.7D * (double) direction.getStepZ();
        Level world = turtle.getLevel();
        TransfiguringProjectileEntity transfiguringProjectileEntity = new TransfiguringProjectileEntity(world, x, y, z);
        transfiguringProjectileEntity.setItem(TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM.get()
                .withTransfigurationType(transfigurationType.get()));
        transfiguringProjectileEntity.shoot(direction.getStepX(), direction.getStepY() + 0.1F, direction.getStepZ(),
                1.1F, 6.0F);
        world.addFreshEntity(transfiguringProjectileEntity);
        return TurtleCommandResult.success();
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
