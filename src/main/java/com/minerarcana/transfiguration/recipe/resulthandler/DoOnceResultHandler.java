package com.minerarcana.transfiguration.recipe.resulthandler;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;

import java.util.function.BiConsumer;

public class DoOnceResultHandler extends ResultHandler {
    private final BiConsumer<TransfigurationContainer<?>, Double> onProcess;

    public DoOnceResultHandler(BiConsumer<TransfigurationContainer<?>, Double> onProcess) {
        this.onProcess = onProcess;
    }

    @Override
    public boolean process(TransfigurationContainer<?> transfigurationContainer, double powerModifier) {
        onProcess.accept(transfigurationContainer, powerModifier);
        return true;
    }
}
