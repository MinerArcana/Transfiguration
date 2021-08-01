package com.minerarcana.transfiguration.api.event;

import com.minerarcana.transfiguration.api.TransfigurationType;
import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import net.minecraftforge.eventbus.api.Event;

public class TransfigurationEvent extends Event {
    private final TransfigurationType type;
    private final TransfigurationContainer<?> transfigurationContainer;
    private final double originalTimeModifier;
    private final double originalPowerModifier;

    private double currentTimeModifier;
    private double currentPowerModifier;

    public TransfigurationEvent(TransfigurationType type, TransfigurationContainer<?> transfigurationContainer,
                                double originalTimeModifier, double originalPowerModifier) {
        this.type = type;
        this.transfigurationContainer = transfigurationContainer;
        this.originalTimeModifier = originalTimeModifier;
        this.originalPowerModifier = originalPowerModifier;
        this.currentTimeModifier = originalTimeModifier;
        this.currentPowerModifier = originalPowerModifier;
    }

    public TransfigurationType getType() {
        return type;
    }

    public TransfigurationContainer<?> getTransfigurationContainer() {
        return transfigurationContainer;
    }

    public double getOriginalTimeModifier() {
        return originalTimeModifier;
    }

    public double getOriginalPowerModifier() {
        return originalPowerModifier;
    }

    public double getCurrentTimeModifier() {
        return currentTimeModifier;
    }

    public void setCurrentTimeModifier(double currentTimeModifier) {
        this.currentTimeModifier = currentTimeModifier;
    }

    public double getCurrentPowerModifier() {
        return currentPowerModifier;
    }

    public void setCurrentPowerModifier(double currentPowerModifier) {
        this.currentPowerModifier = currentPowerModifier;
    }
}
