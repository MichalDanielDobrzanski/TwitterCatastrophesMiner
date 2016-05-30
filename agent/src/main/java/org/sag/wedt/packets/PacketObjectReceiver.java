package org.sag.wedt.packets;

import java.io.Serializable;

/**
 * Created by Michał Breiter.
 */
public interface PacketObjectReceiver {
    void onPacket(Serializable packet);
}
