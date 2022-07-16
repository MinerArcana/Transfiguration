package com.minerarcana.transfiguration.recipe.predicate;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationPredicates;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

import java.util.function.Predicate;

public record PositionPredicate(
        Direction.Axis axis,
        int min,
        int max
) implements TransfigurationPredicate {
    public static final Codec<PositionPredicate> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Direction.Axis.CODEC.fieldOf("axis").forGetter(PositionPredicate::axis),
            Codec.INT.optionalFieldOf("min", Integer.MIN_VALUE).forGetter(PositionPredicate::min),
            Codec.INT.optionalFieldOf("max", Integer.MAX_VALUE).forGetter(PositionPredicate::max)
    ).apply(instance, PositionPredicate::new));

    @Override
    public boolean test(TransfigurationContainer<?> transfigurationContainer) {
        BlockPos blockPos = transfigurationContainer.getTargetedPos();
        int value = axis().choose(blockPos.getX(), blockPos.getY(), blockPos.getZ());
        return value >= min() && value <= max();
    }

    @Override
    public PredicateType getType() {
        return TransfigurationPredicates.POSITION.get();
    }
}
