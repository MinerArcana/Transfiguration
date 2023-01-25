package com.minerarcana.transfiguration.recipe.nbt;

import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction;

import java.util.ArrayList;
import java.util.List;

public class NBTCopierBuilder {
    private final List<NBTCopyOperation> operationList;

    public NBTCopierBuilder() {
        operationList = new ArrayList<>();
    }

    public NBTCopierBuilder copy(String pSourcePath, String pTargetPath, CopyNbtFunction.MergeStrategy pCopyAction) {
        this.operationList.add(new NBTCopyOperation(pSourcePath, pTargetPath, pCopyAction));
        return this;
    }

    public NBTCopierBuilder copy(String pSourcePath, String pTargetPath) {
        return this.copy(pSourcePath, pTargetPath, CopyNbtFunction.MergeStrategy.REPLACE);
    }

    public NBTCopierBuilder copy(String path) {
        return this.copy(path, path, CopyNbtFunction.MergeStrategy.REPLACE);
    }

    public NBTCopier build() {
        return new NBTCopier(this.operationList);
    }

}
