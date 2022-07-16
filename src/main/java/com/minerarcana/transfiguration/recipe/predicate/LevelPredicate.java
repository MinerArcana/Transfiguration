package com.minerarcana.transfiguration.recipe.predicate;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationPredicates;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record LevelPredicate(ResourceKey<Level> level) implements TransfigurationPredicate {

    public static final Codec<LevelPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Level.RESOURCE_KEY_CODEC.fieldOf("level").forGetter(LevelPredicate::level)
    ).apply(instance, LevelPredicate::new));

    @Override
    public boolean test(TransfigurationContainer<?> transfigurationContainer) {
        return transfigurationContainer.getLevel().dimension() == level;
    }

    @Override
    public PredicateType getType() {
        return TransfigurationPredicates.LEVEL.get();
    }
}
