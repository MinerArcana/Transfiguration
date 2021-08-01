package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.recipe.TransfigurationRecipe;
import com.minerarcana.transfiguration.recipe.result.ResultInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.util.NonNullPredicate;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public abstract class TransfiguringEntity<T extends TransfigurationRecipe<U, V>, U extends NonNullPredicate<V>, V>
        extends Entity implements IRendersAsItem {
    private static final DataParameter<String> RECIPE_NAME = EntityDataManager.createKey(
            TransfiguringEntity.class,
            DataSerializers.STRING
    );

    private T recipe;
    private long startTime;
    private int modifiedTime;
    private double powerModifier;
    private int noRecipeTicks;
    private ResultInstance resultInstance;
    private ItemStack itemStack;
    private UUID caster;
    private boolean removedInputs;

    public TransfiguringEntity(EntityType<? extends Entity> entityType, World world) {
        super(entityType, world);
        this.removedInputs = false;
    }

    public TransfiguringEntity(EntityType<? extends Entity> entityType, World world, BlockPos blockPos, T recipe,
                               int modifiedTime, double powerModifier) {
        super(entityType, world);
        this.setPosition(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        this.startTime = world.getGameTime();
        this.recipe = recipe;
        this.setRecipeName(recipe.getId().toString());
        this.modifiedTime = modifiedTime;
        this.powerModifier = powerModifier;
        this.removedInputs = false;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getEntityWorld().isRemote()) {
            if (recipe == null) {
                recipe = this.getRecipe();
                noRecipeTicks++;
                if (noRecipeTicks > 5) {
                    this.remove();
                }
            } else {
                int remainingTicks = this.modifiedTime - (int) (this.getEntityWorld().getGameTime() - startTime);
                if (remainingTicks <= 0 && !this.removedInputs) {
                    this.removedInputs = true;
                    this.removeInput();
                    this.getEntityWorld().createExplosion(
                            null,
                            this.getPosX() + 0.5,
                            this.getPosY() + 0.5,
                            this.getPosZ() + 0.5,
                            1.9F,
                            Explosion.Mode.NONE
                    );
                }
                TransfigurationContainer<V> transfigurationContainer = this.createTransfigurationContainer();
                if (transfigurationContainer != null) {
                    if (this.getResultInstance(recipe).tick(transfigurationContainer, powerModifier, remainingTicks)) {
                        this.remove();
                    }
                } else {
                    this.remove();
                }
            }
        }
    }

    @Override
    protected void registerData() {
        this.getDataManager().register(RECIPE_NAME, "");
    }

    @Override
    public void readAdditional(@Nonnull CompoundNBT compound) {
        this.setRecipeName(compound.getString("recipeName"));
        this.startTime = compound.getLong("startTime");
        this.modifiedTime = compound.getInt("modifiedTime");
        this.powerModifier = compound.getDouble("powerModifier");
    }

    @Override
    public void writeAdditional(@Nonnull CompoundNBT compound) {
        compound.putString("recipeName", this.getRecipeName());
        compound.putLong("startTime", this.startTime);
        compound.putInt("modifiedTime", this.modifiedTime);
        compound.putDouble("powerModifier", this.powerModifier);
    }

    @Override
    @Nonnull
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    public void setRecipeName(String recipeName) {
        this.getDataManager().set(RECIPE_NAME, recipeName);
    }

    public String getRecipeName() {
        return this.getDataManager().get(RECIPE_NAME);
    }

    @Nullable
    public Entity getCaster() {
        return null;
    }

    @Nonnull
    public ResultInstance getResultInstance(@Nonnull T recipe) {
        if (this.resultInstance == null) {
            this.resultInstance = recipe.getResult().create();
        }
        return this.resultInstance;
    }

    @Nullable
    public abstract TransfigurationContainer<V> createTransfigurationContainer();

    @Nullable
    public abstract T getRecipe();

    public abstract void removeInput();

    @Override
    @Nonnull
    public ItemStack getItem() {
        if (this.itemStack == null && this.getRecipe() != null) {
            this.itemStack = TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM
                    .map(transfiguringProjectileItem -> transfiguringProjectileItem.withTransfigurationType(
                            this.getRecipe().getTransfigurationType()
                    ))
                    .orElse(null);
        }
        if (this.itemStack == null) {
            return ItemStack.EMPTY;
        } else {
            return this.itemStack;
        }
    }
}
