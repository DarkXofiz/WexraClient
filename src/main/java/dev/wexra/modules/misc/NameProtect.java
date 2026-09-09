package dev.wexra.modules.misc;

import dev.wexra.modules.setting.BooleanSetting;
import dev.wexra.modules.setting.TextSetting;
import dev.wexra.events.Event;
import dev.wexra.manager.Manager;
import dev.wexra.modules.Function;
import dev.wexra.modules.FunctionAnnotation;
import dev.wexra.modules.Type;

@FunctionAnnotation(name = "NameProtect", desc = "", type = Type.Misc)
public class NameProtect extends Function {
    public final TextSetting text = new TextSetting("Ник","levin1337");
    public final BooleanSetting friend = new BooleanSetting("Скрывать друзей",true);

    public NameProtect() {
        addSettings(text,friend);
    }
    public String getCustomName() {
        return Manager.FUNCTION_MANAGER.nameProtect.state ? text.getValue().replaceAll("&", "\u00a7") : mc.getGameProfile().getName();
    }
    public String getProtectedName(String originalName) {
        if (!Manager.FUNCTION_MANAGER.nameProtect.state) return originalName;

        if (isSelf(originalName)) {
            return applyFormatting(text.getValue());
        }

        if (friend.get() && Manager.FRIEND_MANAGER.isFriend(originalName)) {
            return applyFormatting(text.getValue());
        }

        return originalName;
    }
    private String applyFormatting(String name) {
        return name.replace('&', '§');
    }

    private boolean isSelf(String name) {
        return name.equals(mc.getSession().getUsername());
    }
    @Override
    public void onEvent(Event event) {

    }
}
