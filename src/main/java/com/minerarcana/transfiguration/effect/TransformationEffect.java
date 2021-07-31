package com.minerarcana.transfiguration.effect;

import com.minerarcana.transfiguration.api.TransfigurationType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

public class TransformationEffect extends Effect {
    private final Supplier<TransfigurationType> typeSupplier;

    public TransformationEffect(Supplier<TransfigurationType> typeSupplier) {
        super(EffectType.NEUTRAL, 0);
        this.typeSupplier = typeSupplier;
    }

    @Override
    public boolean isInstant() {
        return true;
    }

    @Override
    public void affectEntity(@Nullable Entity source, @Nullable Entity indirectSource, @Nonnull LivingEntity entityLiving, int amplifier, double health) {

    }

    @Override
    public int getLiquidColor() {
        return typeSupplier.get().getPrimaryColor();
    }
}
