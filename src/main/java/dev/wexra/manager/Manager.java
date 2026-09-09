package dev.wexra.manager;

import dev.wexra.manager.accountManager.AccountManager;
import dev.wexra.manager.commandManager.CommandManager;
import dev.wexra.manager.configManager.ConfigManager;
import dev.wexra.manager.dragManager.DragManager;
import dev.wexra.manager.friendManager.FriendManager;
import dev.wexra.manager.ircManager.IrcManager;
import dev.wexra.manager.macroManager.MacroManager;
import dev.wexra.manager.modulesManager.ChestStealerManager;
import dev.wexra.manager.notificationManager.NotificationManager;
import dev.wexra.manager.proxyManager.ProxyManager;
import dev.wexra.manager.staffManager.StaffManager;
import dev.wexra.manager.themeManager.StyleManager;
import dev.wexra.modules.FunctionManager;
import dev.wexra.modules.combat.rotation.RotationController;
import dev.wexra.protect.UserProfile;
import dev.wexra.manager.fontManager.FontUtils;

public class Manager {
    public static final RotationController ROTATION = RotationController.get();
    public static UserProfile USER_PROFILE;
    public static FunctionManager FUNCTION_MANAGER;
    public static StyleManager STYLE_MANAGER;
    public static NotificationManager NOTIFICATION_MANAGER;
    public static FriendManager FRIEND_MANAGER;
    public static ConfigManager CONFIG_MANAGER;
    public static MacroManager MACROS_MANAGER;
    public static StaffManager STAFF_MANAGER;
    public static CommandManager COMMAND_MANAGER;
    public static DragManager DRAG_MANAGER;
    public static SyncManager SYNC_MANAGER;
    public static FontUtils FONT_MANAGER;
    public static AccountManager ACCOUNT_MANAGER;
    public static ChestStealerManager CHESTSTEALER_MANAGER;
    public static IrcManager IRC_MANAGER;
    public static ProxyManager PROXY_MANAGER;
}
