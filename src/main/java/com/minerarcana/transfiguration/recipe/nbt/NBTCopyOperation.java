package com.minerarcana.transfiguration.recipe.nbt;

import com.google.gson.JsonObject;
import com.minerarcana.transfiguration.Transfiguration;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.arguments.NbtPathArgument;
import net.minecraft.nbt.Tag;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.functions.CopyNbtFunction.MergeStrategy;

import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

public class NBTCopyOperation {
    private final String sourcePathText;
    private final NbtPathArgument.NbtPath sourcePath;
    private final String targetPathText;
    private final NbtPathArgument.NbtPath targetPath;
    private final MergeStrategy op;

    public NBTCopyOperation(String pSourcePathText, String pTargetPathText, MergeStrategy pMergeStrategy) {
        this.sourcePathText = pSourcePathText;
        this.sourcePath = compileNbtPath(pSourcePathText);
        this.targetPathText = pTargetPathText;
        this.targetPath = compileNbtPath(pTargetPathText);
        this.op = pMergeStrategy;
    }

    private NbtPathArgument.NbtPath compileNbtPath(String pNbtPath) {
        try {
            return (new NbtPathArgument()).parse(new StringReader(pNbtPath));
        } catch (CommandSyntaxException syntaxException) {
            throw new IllegalArgumentException("Failed to parse path " + pNbtPath, syntaxException);
        }
    }

    public void apply(Supplier<Tag> pTargetTag, Tag pSourceTag) {
        try {
            List<Tag> list = this.sourcePath.get(pSourceTag);
            if (!list.isEmpty()) {
                this.op.merge(pTargetTag.get(), this.targetPath, list);
            }
        } catch (CommandSyntaxException syntaxException) {
            Transfiguration.LOGGER.warn("Failed to copy nbt", syntaxException);
        }
    }

    public JsonObject toJson() {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty("source", this.sourcePathText);
        jsonobject.addProperty("target", this.targetPathText);
        jsonobject.addProperty("op", this.op.name().toLowerCase(Locale.US));
        return jsonobject;
    }

    public static NBTCopyOperation fromJson(JsonObject pJson) {
        String source = GsonHelper.getAsString(pJson, "source");
        String target = GsonHelper.getAsString(pJson, "target");
        MergeStrategy mergeStrategy = MergeStrategy.getByName(GsonHelper.getAsString(pJson, "op"));
        return new NBTCopyOperation(source, target, mergeStrategy);
    }
}