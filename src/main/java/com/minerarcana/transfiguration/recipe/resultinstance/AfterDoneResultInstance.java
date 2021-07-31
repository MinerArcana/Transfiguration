package com.minerarcana.transfiguration.recipe.resultinstance;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.recipe.result.ResultInstance;

import java.util.function.BiConsumer;

public class AfterDoneResultInstance extends ResultInstance {
    private final int afterTime;
    private final BiConsumer<TransfigurationContainer<?>, Double> result;

    public AfterDoneResultInstance(BiConsumer<TransfigurationContainer<?>, Double> result) {
        this(0, result);
    }

    public AfterDoneResultInstance(int afterTime, BiConsumer<TransfigurationContainer<?>, Double> result) {
        this.afterTime = -afterTime;
        this.result = result;
    }

    @Override
    public boolean tick(TransfigurationContainer<?> container, double powerModifier, int remainingTicks) {
        if (remainingTicks <= afterTime) {
            this.result.accept(container, powerModifier);
            return true;
        }
        return false;
    }
}
