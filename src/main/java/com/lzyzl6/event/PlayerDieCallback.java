package com.lzyzl6.event;

import com.lzyzl6.entity.WanderingSpirit;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

import java.io.IOException;

public interface PlayerDieCallback {
    Event<PlayerDieCallback> EVENT = EventFactory.createArrayBacked(PlayerDieCallback.class,
            (listeners) -> (player, ghost) -> {
                for (PlayerDieCallback listener : listeners) {
                    InteractionResult result = listener.summonGhost(player, ghost);

                    if (result != InteractionResult.PASS) {
                        return result;
                    }
                }

                return InteractionResult.PASS;
            });

    InteractionResult summonGhost(Player player, WanderingSpirit ghost) throws IOException;
}
