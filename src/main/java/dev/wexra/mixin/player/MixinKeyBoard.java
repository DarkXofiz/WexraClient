package dev.wexra.mixin.player;

import net.minecraft.client.Keyboard;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.wexra.WexraClient;
import dev.wexra.manager.IMinecraft;

@Mixin(Keyboard.class)
public class MixinKeyBoard implements IMinecraft {
    @Inject(method = "onKey", at = @At("HEAD"))
    private void onKey(long window, int key, int scancode, int action, int modifiers, CallbackInfo ci) {
        if (action == 1 && !(mc.currentScreen instanceof Screen)) {
            WexraClient main = new WexraClient();
            main.keyPress(key);
        }
    }
}