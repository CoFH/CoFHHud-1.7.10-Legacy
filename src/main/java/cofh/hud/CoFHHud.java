package cofh.hud;

import cofh.CoFHCore;
import cofh.core.CoFHProps;
import cofh.core.network.PacketCoFHBase;
import cofh.core.util.ConfigHandler;
import cofh.hud.core.Proxy;
import cofh.hud.network.PacketHudBase;
import cofh.lib.util.helpers.StringHelper;
import cofh.mod.BaseMod;
import cofh.mod.updater.UpdateManager;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.CustomProperty;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartedEvent;
import java.io.File;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = CoFHHud.modId, name = CoFHHud.modName, version = CoFHHud.version, dependencies = CoFHHud.dependencies,
		customProperties = @CustomProperty(k = "cofhversion", v = "true") )
public class CoFHHud extends BaseMod {

	public static final String modId = "CoFHud";
	public static final String modName = "CoFH HUD";
	public static final String version = "1.7.10R1.0.0A1";
	public static final String dependencies = CoFHCore.version_group;
	public static final String releaseURL = "https://raw.github.com/CoFH/Version/master/ThermalAscension";

	@Instance(modId)
	public static CoFHHud instance;

	@SidedProxy(clientSide = "cofh.thermalascension.core.ProxyClient", serverSide = "cofh.thermalascension.core.Proxy")
	public static Proxy proxy;

	public static final Logger log = LogManager.getLogger(modId);
	public static final ConfigHandler config = new ConfigHandler(version);
	public static final ConfigHandler configClient = new ConfigHandler(version);

	public static CreativeTabs tab;

	/* INIT SEQUENCE */
	public CoFHHud() {

		super(log);
	}

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		UpdateManager.registerUpdater(new UpdateManager(this, releaseURL, CoFHProps.DOWNLOAD_URL));
		config.setConfiguration(new Configuration(new File(event.getModConfigurationDirectory(), "cofh/hud/common.cfg"), true));
		configClient.setConfiguration(new Configuration(new File(event.getModConfigurationDirectory(), "cofh/hud/client.cfg"), true));

		cleanConfig(true);
		configOptions();
	}

	@EventHandler
	public void initialize(FMLInitializationEvent event) {

		/* Register Handlers */
		MinecraftForge.EVENT_BUS.register(proxy);
		PacketHudBase.initialize();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

		proxy.registerRenderInformation();
	}

	@EventHandler
	public void loadComplete(FMLLoadCompleteEvent event) {

		cleanConfig(false);

		config.cleanUp(false, true);
		configClient.cleanUp(false, true);

		log.info("Load Complete.");
	}

	@EventHandler
	public void serverStarting(FMLServerStartedEvent event) {

		handleIdMapping();
	}

	public void handleConfigSync(PacketCoFHBase payload) {

		handleIdMapping();

		log.info("Receiving Server Configuration...");
	}

	public synchronized void handleIdMapping() {

	}

	// Called when the client disconnects from the server.
	public void resetClientConfigs() {

		handleIdMapping();

		log.info(StringHelper.localize("Restoring Client Configuration..."));
	}

	/* LOADING FUNCTIONS */
	void configOptions() {

	}

	void cleanConfig(boolean preInit) {

		if (preInit) {

		}
		String prefix = "config.cofhhud.";
		String[] categoryNames = config.getCategoryNames().toArray(new String[config.getCategoryNames().size()]);
		for (int i = 0; i < categoryNames.length; i++) {
			config.getCategory(categoryNames[i]).setLanguageKey(prefix + categoryNames[i]).setRequiresMcRestart(true);
		}
		categoryNames = configClient.getCategoryNames().toArray(new String[configClient.getCategoryNames().size()]);
		for (int i = 0; i < categoryNames.length; i++) {
			configClient.getCategory(categoryNames[i]).setLanguageKey(prefix + categoryNames[i]).setRequiresMcRestart(true);
		}
	}

	/* BaseMod */
	@Override
	public String getModId() {

		return modId;
	}

	@Override
	public String getModName() {

		return modName;
	}

	@Override
	public String getModVersion() {

		return version;
	}

}
