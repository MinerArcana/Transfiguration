package com.minerarcana.transfiguration.recipe.predicate;

import com.minerarcana.transfiguration.api.recipe.TransfigurationContainer;
import com.minerarcana.transfiguration.content.TransfigurationPredicates;
import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.dimension.end.EndDragonFight;
import net.minecraft.world.level.levelgen.feature.EndPodiumFeature;

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
                return transfigurationContainer.getTargetedPos().distManhattan(EndPodiumFeature.END_PODIUM_LOCATION) < 7;
            }
        }
        return false;
    }
}
