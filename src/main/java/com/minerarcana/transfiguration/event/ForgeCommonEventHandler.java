package com.minerarcana.transfiguration.event;

import com.minerarcana.transfiguration.Transfiguration;
import com.minerarcana.transfiguration.item.ITransfiguring;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = Transfiguration.ID, bus = Bus.FORGE)
public class ForgeCommonEventHandler {

    @SubscribeEvent
    public static void handleEntityInteract(PlayerInteractEvent.EntityInteract event) {
        ItemStack itemStack = event.getEntity()
                .getItemInHand(event.getHand());
        if (itemStack.getItem() instanceof ITransfiguring transfiguring) {
            boolean transfigured = transfiguring.transfigureEntity(
                    event.getTarget(),
                    event.getEntity(),
                    itemStack
            );
            if (transfigured) {
                transfiguring.afterTransfiguration(itemStack, event.getEntity(), event.getHand());
                event.setCancellationResult(InteractionResult.SUCCESS);
            }
        }
    }
}
