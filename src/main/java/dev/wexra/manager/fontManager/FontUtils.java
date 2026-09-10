package dev.wexra.manager.fontManager;

import java.io.InputStream;
import java.util.Objects;

@SuppressWarnings("All")
public class FontUtils {

    public final String fontsDir = "/assets/wexraclient/font/";

    public volatile RenderFonts[] comfortaa = new RenderFonts[256];
    public static volatile RenderFonts[] durman = new RenderFonts[256];
    public static volatile RenderFonts[] glitched = new RenderFonts[256];
    public static volatile RenderFonts[] icons = new RenderFonts[256];
    public static volatile RenderFonts[] monsterrat = new RenderFonts[256];
    public static volatile RenderFonts[] profont = new RenderFonts[256];
    public static volatile RenderFonts[] sf_bold = new RenderFonts[256];
    public static volatile RenderFonts[] sf_medium = new RenderFonts[256];
    public static volatile RenderFonts[] iconsWex = new RenderFonts[256];
    public static volatile RenderFonts[] gilroy = new RenderFonts[256];
    public static volatile RenderFonts[] gilroy_bold = new RenderFonts[256];
    public static volatile RenderFonts[] hud = new RenderFonts[256];
    public static volatile RenderFonts[] icomoon = new RenderFonts[256];

    private boolean initialized = false;
    private volatile boolean initStarted = false;

    // NOT: Font yukleme artik java.awt.Font degil, stb_truetype tabanli
    // TrueTypeFont uzerinden yapiliyor, yani Android/PojavLauncher gibi
    // ortamlardaki AWT/Toolkit donma riski tamamen ortadan kalkti.
    // Yine de bu islemi ayri bir thread'de yapip, ana thread'i EN FAZLA
    // 8 saniye bekletiyoruz (join ile). Boylece:
    //  - Eger font yukleme cok yavas/donarsa oyun sonsuza kadar kilitlenmez,
    //  - Eger font yukleme normal hizda biterse (beklenen durum), ana menu
    //    ilk kez cizilmeden ONCE fontlar hazir olur ve null-pointer riski
    //    (dizi elemani henuz doldurulmadan kullanilmasi) ortadan kalkar.
    public void init() {
        if (initStarted) return;
        initStarted = true;

        Thread fontThread = new Thread(() -> {
            try {
                initializationFont(comfortaa, "comfortaa.ttf");
                initializationFont(durman, "durman.ttf");
                initializationFont(glitched, "glitched.ttf");
                initializationFont(icons, "icons.ttf");
                initializationFont(monsterrat, "monsterrat.ttf");
                initializationFont(profont, "profont.ttf");
                initializationFont(sf_bold, "sf_bold.ttf");
                initializationFont(sf_medium, "sf_medium.ttf");
                initializationFont(iconsWex, "iconsWex.ttf");
                initializationFont(hud, "hud.ttf");
                initializationFont(gilroy, "gilroy.ttf");
                initializationFont(gilroy_bold, "gilroy-bold.ttf");
                initializationFont(icomoon, "icomoon.ttf");
            } catch (Throwable t) {
                t.printStackTrace();
            } finally {
                initialized = true;
            }
        }, "WexraClient-FontLoader");
        fontThread.setDaemon(true);
        fontThread.start();

        try {
            fontThread.join(8000);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
        // 8 saniye icinde bitmediyse arka planda devam etmesine izin
        // veriyoruz (thread daemon oldugu icin JVM'i kapatmaz), ama
        // ana thread'i artik bekletmiyoruz.
    }

    public boolean isInitialized() {
        return initialized;
    }

    private void initializationFont(RenderFonts[] fontArray, String fontName) {
        if (fontArray == null) return;
        try (InputStream stream = Objects.requireNonNull(FontUtils.class.getResourceAsStream(fontsDir + fontName))) {
            TrueTypeFont font = TrueTypeFont.load(stream);
            for (int i = 1; i < fontArray.length; i++) {
                try {
                    fontArray[i] = new RenderFonts(font, i);
                } catch (Throwable innerT) {
                    // Bu boyut icin basarisiz oldu, digerlerine devam et.
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
