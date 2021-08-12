package com.minerarcana.transfiguration.recipe.resultinstance;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.recipe.result.ResultInstance;

public class ChanceResultInstance extends ResultInstance {
    private final ResultInstance resultInstance;
    private final boolean success;

    public ChanceResultInstance(ResultInstance resultInstance, boolean success) {
        this.resultInstance = resultInstance;
        this.success = success;
    }

    @Override
    public boolean tick(TransfigurationContainer<?> container, double powerModifier, int remainingTicks) {
        return success ? resultInstance.tick(container, powerModifier, remainingTicks) : remainingTicks > 0;
    }
}
