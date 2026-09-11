package dev.wexra.mixin.client;

import net.minecraft.client.MinecraftClient;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import dev.wexra.manager.ClientManager;
import dev.wexra.manager.IMinecraft;
import dev.wexra.screens.mainmenu.MainMenu;
import dev.wexra.WexraClient;

@Mixin(TitleScreen.class)
public class MixinTitleScreen implements IMinecraft {
    @Inject(method = "init", at = @At("HEAD"), cancellable = true)
    private void onInit(CallbackInfo ci) {
        try {
            // WexraClient.init() artik burada cagriliyor (MinecraftClient'in
            // constructor'inda DEGIL). TitleScreen sadece vanilla'nin ilk
            // resource/registry yuklemesi TAMAMEN basarili bittikten SONRA
            // acilir, bu yuzden burada calisan (potansiyel olarak biraz zaman
            // alan, ornegin font yukleme gibi) kod, oyunun kendi ic
            // kayit/registry doldurma zamanlamasini asla bozamaz.
            WexraClient.getInstance().init();

            if (!ClientManager.legitMode) {
                mc.setScreen(new MainMenu());
                ci.cancel();
            }
        } catch (Throwable t) {
            // Beklenmedik bir hata olsa bile orijinal TitleScreen'in
            // acilmasina izin ver, oyunu tamamen bozma.
            t.printStackTrace();
        }
    }
}
