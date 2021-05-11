package com.minerarcana.transfiguration.entity;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationEntities;
import com.minerarcana.transfiguration.recipe.TransfigurationRecipe;
import com.minerarcana.transfiguration.recipe.resulthandler.ResultHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.IRendersAsItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.HangingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.NonNullLazy;
import net.minecraftforge.common.util.NonNullPredicate;
import net.minecraftforge.fml.network.NetworkHooks;
import org.apache.commons.lang3.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class TransfiguringEntity<T extends TransfigurationRecipe<U, V>, U extends NonNullPredicate<V>, V>
        extends HangingEntity implements IRendersAsItem {
    private static final DataParameter<String> RECIPE_NAME = EntityDataManager.createKey(TransfiguringEntity.class,
            DataSerializers.STRING);

    private final NonNullLazy<Integer> offset;

    private T recipe;
    private long startTime;
    private int modifiedTime;
    private double powerModifier;
    private int noRecipeTicks;
    private ResultHandler resultHandler;
    private ItemStack itemStack;
    private TransfigurationContainer<V> transfigurationContainer;

    public TransfiguringEntity(EntityType<? extends HangingEntity> entityType, World world) {
        super(entityType, world);
        this.offset = NonNullLazy.of(() -> this.getEntityWorld().getRandom().nextInt(20));
    }

    public TransfiguringEntity(EntityType<? extends HangingEntity> entityType, World world, BlockPos blockPos,
                               Direction placedOn, T recipe, int modifiedTime, double powerModifier) {
        super(entityType, world, blockPos);
        this.updateFacingWithBoundingBox(placedOn);
        this.startTime = world.getGameTime();
        this.recipe = recipe;
        this.setRecipeName(recipe.getId().toString());
        this.modifiedTime = modifiedTime;
        this.powerModifier = powerModifier;
        this.offset = NonNullLazy.of(() -> this.getEntityWorld().getRandom().nextInt(20));
    }

    @Override
    protected void updateFacingWithBoundingBox(@Nonnull Direction placedOn) {
        Validate.notNull(placedOn);
        this.facingDirection = placedOn;
        if (placedOn.getAxis().isHorizontal()) {
            this.rotationPitch = 0.0F;
            this.rotationYaw = (float) (this.facingDirection.getHorizontalIndex() * 90);
        } else {
            this.rotationPitch = (float) (-90 * placedOn.getAxisDirection().getOffset());
            this.rotationYaw = 0.0F;
        }

        this.prevRotationPitch = this.rotationPitch;
        this.prevRotationYaw = this.rotationYaw;
        this.updateBoundingBox();
    }

    protected void updateBoundingBox() {
        if (this.facingDirection != null) {
            double d1 = (double) this.hangingPosition.getX() + 0.5D - (double) this.facingDirection.getXOffset() * 0.46875D;
            double d2 = (double) this.hangingPosition.getY() + 0.5D - (double) this.facingDirection.getYOffset() * 0.46875D;
            double d3 = (double) this.hangingPosition.getZ() + 0.5D - (double) this.facingDirection.getZOffset() * 0.46875D;
            this.setRawPosition(d1, d2, d3);
            double d4 = this.getWidthPixels();
            double d5 = this.getHeightPixels();
            double d6 = this.getWidthPixels();
            Direction.Axis direction$axis = this.facingDirection.getAxis();
            switch (direction$axis) {
                case X:
                    d4 = 1.0D;
                    break;
                case Y:
                    d5 = 1.0D;
                    break;
                case Z:
                    d6 = 1.0D;
            }

            d4 = d4 / 32.0D;
            d5 = d5 / 32.0D;
            d6 = d6 / 32.0D;
            this.setBoundingBox(new AxisAlignedBB(d1 - d4, d2 - d5, d3 - d6, d1 + d4, d2 + d5, d3 + d6));
        }
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
                ResultHandler resultHandler = this.getResultHandler(recipe);
                if (this.getEntityWorld().getGameTime() - startTime > modifiedTime) {
                    this.getTransfigurationContainer().removeTarget();
                    if (resultHandler.process(this.getTransfigurationContainer(), this.powerModifier)) {
                        this.remove();
                    }
                } else {
                    resultHandler.preprocess(this.getTransfigurationContainer(), this.powerModifier);
                }
            }
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.getDataManager().register(RECIPE_NAME, "");
    }

    @Override
    public void readAdditional(@Nonnull CompoundNBT compound) {
        super.readAdditional(compound);
        this.setRecipeName(compound.getString("recipeName"));
        this.startTime = compound.getLong("startTime");
        this.modifiedTime = compound.getInt("modifiedTime");
        this.powerModifier = compound.getDouble("powerModifier");
    }

    @Override
    public void writeAdditional(@Nonnull CompoundNBT compound) {
        super.writeAdditional(compound);
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

    public int getOffset() {
        return this.offset.get();
    }

    @Nullable
    public LivingEntity getCaster() {
        return null;
    }

    @Nonnull
    public ResultHandler getResultHandler(@Nonnull T recipe) {
        if (this.resultHandler == null) {
            this.resultHandler = recipe.createResultHandler();
        }
        return this.resultHandler;
    }

    @Nonnull
    public abstract TransfigurationContainer<V> createTransfigurationContainer();

    public TransfigurationContainer<V> getTransfigurationContainer() {
        if (this.transfigurationContainer == null) {
            this.transfigurationContainer = this.createTransfigurationContainer();
        }
        return this.transfigurationContainer;
    }

    @Nullable
    public abstract T getRecipe();

    @Override
    @Nonnull
    public ItemStack getItem() {
        if (this.itemStack == null && this.getRecipe() != null) {
            this.itemStack = TransfigurationEntities.TRANSFIGURING_PROJECTILE_ITEM
                    .map(transfiguringProjectileItem -> transfiguringProjectileItem.withTransfigurationType(
                            this.getRecipe().getTransfigurationType())
                    )
                    .orElse(null);
        }
        if (this.itemStack == null) {
            return ItemStack.EMPTY;
        } else {
            return this.itemStack;
        }
    }

    @Override
    public int getHeightPixels() {
        return 8;
    }

    @Override
    public int getWidthPixels() {
        return 8;
    }

    @Override
    public void playPlaceSound() {

    }

    @Override
    public void onBroken(@Nullable Entity brokenEntity) {

    }

    public boolean onValidSurface() {
        if (!this.world.hasNoCollisions(this)) {
            return false;
        } else {
            T recipe = this.getRecipe();
            if (recipe != null) {
                return recipe.matches(this.getTransfigurationContainer(), this.getEntityWorld());
            } else {
                return false;
            }
        }
    }
}
