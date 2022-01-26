package com.minerarcana.transfiguration.effect;

import com.minerarcana.transfiguration.api.TransfigurationType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class TransformationEffect extends MobEffect {
    private final Supplier<TransfigurationType> typeSupplier;

    public TransformationEffect(Supplier<TransfigurationType> typeSupplier) {
        super(MobEffectCategory.NEUTRAL, 0);
        this.typeSupplier = typeSupplier;
    }

    @Override
    public boolean isInstantenous() {
        return true;
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, @Nonnull LivingEntity entityLiving, int amplifier, double health) {

    }

    @Override
    public int getColor() {
        return typeSupplier.get().getPrimaryColor();
    }
}
