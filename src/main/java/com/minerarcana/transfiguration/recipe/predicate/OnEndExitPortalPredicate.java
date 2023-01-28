package com.minerarcana.transfiguration.recipe.predicate;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationPredicates;
import com.minerarcana.transfiguration.mixin.EndDragonFightAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.dimension.end.EndDragonFight;

public class OnEndExitPortalPredicate implements TransfigurationPredicate {
    public static final Codec<OnEndExitPortalPredicate> CODEC = Codec.unit(OnEndExitPortalPredicate::new);

    @Override
    public PredicateType getType() {
        return TransfigurationPredicates.ON_END_PODIUM.get();
    }

    @Override
    public boolean test(TransfigurationContainer<?> transfigurationContainer) {
        if (transfigurationContainer.getLevel() instanceof ServerLevel serverLevel) {
            EndDragonFight fight = serverLevel.dragonFight();
            if (fight != null && fight.hasPreviouslyKilledDragon()) {
                BlockPos portalPos = ((EndDragonFightAccessor) fight).getPortalLocation();
                if (portalPos != null) {
                    return transfigurationContainer.getTargetedPos().distManhattan(portalPos) < 5;
                }
            }
        }
        return false;
    }
}
