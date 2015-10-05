package cofh.hud.network;

import cofh.core.network.PacketCoFHBase;
import cofh.core.network.PacketHandler;
import cofh.hud.CoFHHud;
import net.minecraft.entity.player.EntityPlayer;

public class PacketHudBase extends PacketCoFHBase {

	public static void initialize() {

		PacketHandler.instance.registerPacket(PacketHudBase.class);
	}

	public enum PacketTypes {

	}

	@Override
	public void handlePacket(EntityPlayer player, boolean isServer) {

		try {
			int type = getByte();

			switch (PacketTypes.values()[type]) {
			default:
				CoFHHud.log.error("Unknown Packet! Internal: TEPH, ID: " + type);
			}
		} catch (Exception e) {
			CoFHHud.log.error("Packet payload failure! Please check your config files!");
			e.printStackTrace();
		}
	}

	public static PacketCoFHBase getPacket(PacketTypes theType) {

		return new PacketHudBase().addByte(theType.ordinal());
	}

}
