package dev.wexra;

import lombok.Getter;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import dev.wexra.manager.*;
import dev.wexra.manager.accountManager.AccountManager;
import dev.wexra.manager.ircManager.IrcManager;
import dev.wexra.manager.modulesManager.ChestStealerManager;
import dev.wexra.manager.proxyManager.ProxyManager;
import dev.wexra.manager.themeManager.StyleManager;
import dev.wexra.protect.NativeHelper;
import dev.wexra.modules.setting.BindBooleanSetting;
import dev.wexra.modules.setting.Setting;
import dev.wexra.events.Event;
import dev.wexra.events.impl.input.EventKey;
import dev.wexra.manager.commandManager.CommandManager;
import dev.wexra.manager.configManager.ConfigManager;
import dev.wexra.manager.dragManager.DragManager;
import dev.wexra.manager.dragManager.Dragging;
import dev.wexra.manager.friendManager.FriendManager;
import dev.wexra.manager.macroManager.MacroManager;
import dev.wexra.manager.notificationManager.NotificationManager;
import dev.wexra.manager.staffManager.StaffManager;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionManager;
import dev.wexra.modules.misc.UnHook;
import dev.wexra.screens.dropdown.ClickGUI;
import dev.wexra.manager.fontManager.FontUtils;
import dev.wexra.util.color.ColorUtil;
import dev.wexra.util.player.AudioUtil;
import dev.wexra.util.render.providers.ResourceProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.DosFileAttributeView;
import java.util.Objects;

@SuppressWarnings("All")
public final class WexraClient implements ModInitializer {
	private static WexraClient instance;
	private final File directory;
	private final File directoryAddon;
	public final String name = "Wexra Client";
	@Getter
	boolean initialized;

	public static WexraClient getInstance() {
		return instance;
	}

	public WexraClient() {
		instance = this;
		this.directory = new File(Objects.requireNonNull(MinecraftClient.getInstance().runDirectory), "files");
		this.directoryAddon = new File(Objects.requireNonNull(MinecraftClient.getInstance().runDirectory), "files/modules");
	}

	private void setupProtection() {
		NativeHelper.setProfile();
	}

	@Override
	public void onInitialize() {
		setupProtection();
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				shutDown();
			} catch (Exception ignored) {}
		}));
	}

	public void init() {
		ensureDirectoryExists();
		try {
			Manager.SYNC_MANAGER = new SyncManager();
			Manager.FUNCTION_MANAGER = new FunctionManager();
			Manager.STYLE_MANAGER = new StyleManager();
			Manager.STYLE_MANAGER.init();
			Manager.ACCOUNT_MANAGER = new AccountManager();
			Manager.ACCOUNT_MANAGER.init();
			Manager.FONT_MANAGER = new FontUtils();
			Manager.FONT_MANAGER.init();
			Manager.COMMAND_MANAGER = new CommandManager();
			Manager.DRAG_MANAGER = new DragManager();
			Manager.DRAG_MANAGER.init();
			Manager.MACROS_MANAGER = new MacroManager();
			Manager.MACROS_MANAGER.init();
			Manager.FRIEND_MANAGER = new FriendManager();
			Manager.FRIEND_MANAGER.init();
			Manager.STAFF_MANAGER = new StaffManager();
			Manager.STAFF_MANAGER.init();
			Manager.NOTIFICATION_MANAGER = new NotificationManager();
			Manager.CHESTSTEALER_MANAGER = new ChestStealerManager();
			Manager.PROXY_MANAGER = new ProxyManager();
			Manager.PROXY_MANAGER.init();

			Manager.IRC_MANAGER = new IrcManager();
			Manager.IRC_MANAGER.connect(Manager.USER_PROFILE.getName());

			Manager.CONFIG_MANAGER = new ConfigManager();
			Manager.CONFIG_MANAGER.init();

			ColorUtil.loadImage(ResourceProvider.color_image);

			if (Manager.FUNCTION_MANAGER.clientSounds.check.get("Вход в клиент")) {
				AudioUtil.playSound("join.wav");
			}
			initialized = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void keyPress(int key) {
		int processedKey = key >= 0 ? key : -(100 + key + 2);
		Event.call(new EventKey(processedKey));

		if (key == Manager.FUNCTION_MANAGER.unHook.unHookKey.getKey() && ClientManager.legitMode) {
			UnHook.functionsToBack.forEach(function -> function.setState(true));
			File folder = new File("C:\\WexraClient");
			if (folder.exists()) {
				try {
					Path folderPathObj = folder.toPath();
					DosFileAttributeView attributes = Files.getFileAttributeView(folderPathObj, DosFileAttributeView.class);
					attributes.setHidden(false);
				} catch (IOException ignored) {
				}
			}
			UnHook.functionsToBack.clear();
			ClientManager.legitMode = false;
		}

		if (!ClientManager.legitMode) {
			for (Function module : Manager.FUNCTION_MANAGER.getFunctions()) {
				if (module.bind == processedKey) {
					module.toggle();
				}
				for (Setting setting : module.getSettings()) {
					if (setting instanceof BindBooleanSetting bindSetting) {
						bindSetting.onKeyPress(key, true);
					}
				}
			}

			if (key == Manager.FUNCTION_MANAGER.clickGUI.getBindCode()) {
				MinecraftClient.getInstance().setScreen(new ClickGUI());
			}
			if (Manager.MACROS_MANAGER != null) {
				Manager.MACROS_MANAGER.onKeyPressed(key);
			}
		}
	}

	public void shutDown() {
		Manager.DRAG_MANAGER.save();
		Manager.ACCOUNT_MANAGER.saveAccounts();
		Manager.ACCOUNT_MANAGER.saveLastAlt();
		Manager.CONFIG_MANAGER.saveConfiguration("autocfg");
		Manager.IRC_MANAGER.shutdown();
		Manager.FUNCTION_MANAGER.globals.clear();
		System.out.println("[-] Client shutdown");
	}
	public static void openURL(String url) {
		try {
			String os = System.getProperty("os.name").toLowerCase();

			if (os.contains("win")) {
				Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", url});
			} else if (os.contains("mac")) {
				Runtime.getRuntime().exec(new String[]{"open", url});
			} else {
				Runtime.getRuntime().exec(new String[]{"xdg-open", url});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Dragging createDrag(Function function, String name, float x, float y) {
		DragManager.draggables.put(name, new Dragging(function, name, x, y));
		return DragManager.draggables.get(name);
	}
	private void ensureDirectoryExists() {
		if (!directory.exists() && !directory.mkdirs()) {
			System.err.println("Failed to create directory: " + directory.getAbsolutePath());
		}
		if (!directoryAddon.exists() && !directoryAddon.mkdirs()) {
			System.err.println("Failed to create directory: " + directoryAddon.getAbsolutePath());
		}
	}
}