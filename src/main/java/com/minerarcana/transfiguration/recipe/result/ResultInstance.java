package com.minerarcana.transfiguration.recipe.result;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;

public abstract class ResultInstance {
    public abstract boolean tick(TransfigurationContainer<?> container, double powerModifier, int remainingTicks, ITrigger removeInputs);

    public interface ITrigger {
        boolean trigger(boolean removeInputs);
    }
}
