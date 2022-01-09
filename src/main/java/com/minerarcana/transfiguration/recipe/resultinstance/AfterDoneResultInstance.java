package com.minerarcana.transfiguration.recipe.resultinstance;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.recipe.result.ResultInstance;

import java.util.function.BiConsumer;

public class AfterDoneResultInstance extends ResultInstance {
    private final int afterTime;
    private final boolean removeInputs;
    private final BiConsumer<TransfigurationContainer<?>, Double> result;

    public AfterDoneResultInstance(BiConsumer<TransfigurationContainer<?>, Double> result) {
        this(0, true, result);
    }

    public AfterDoneResultInstance(int afterTime, BiConsumer<TransfigurationContainer<?>, Double> result) {
        this(afterTime, true, result);
    }

    public AfterDoneResultInstance(int afterTime, boolean removeInputs, BiConsumer<TransfigurationContainer<?>, Double> result) {
        this.afterTime = -afterTime;
        this.removeInputs = removeInputs;
        this.result = result;
    }

    @Override
    public boolean tick(TransfigurationContainer<?> container, double powerModifier, int remainingTicks, ITrigger trigger) {
        if (remainingTicks <= 0) {
            trigger.trigger(removeInputs);
        }
        if (remainingTicks <= afterTime) {
            this.result.accept(container, powerModifier);
            return true;
        }
        return false;
    }
}
