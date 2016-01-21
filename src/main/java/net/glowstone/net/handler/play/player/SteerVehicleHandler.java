package net.glowstone.net.handler.play.player;

import com.flowpowered.networking.MessageHandler;
import net.glowstone.GlowServer;
import net.glowstone.entity.GlowPlayer;
import net.glowstone.net.GlowSession;
import net.glowstone.net.message.play.player.SteerVehicleMessage;

public final class SteerVehicleHandler implements MessageHandler<GlowSession, SteerVehicleMessage> {
    @Override
    public void handle(GlowSession session, SteerVehicleMessage message) {
        GlowServer.logger.info(session + ": " + message);

        GlowPlayer player = session.getPlayer();

        if (message.isUnmount() && player.isInsideVehicle()) {
            player.leaveVehicle();
        }
        // todo
    }
}
