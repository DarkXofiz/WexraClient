package dev.wexra.manager.fontManager;

import java.io.InputStream;
import java.util.Objects;

@SuppressWarnings("All")
public class FontUtils {

    public final String fontsDir = "/wexra_data/fonts/";

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
    //
    // ONEMLI: Kod tabaninda hangi font/boyut kombinasyonlarinin GERCEKTEN
    // kullanildigi taranarak (FontUtils.xxx[N] seklindeki tum kullanimlar)
    // SADECE bu kombinasyonlar olusturuluyor. Onceden her font icin 1'den
    // 255'e kadar TUM boyutlar (13 font x 255 = 3315 nesne) olusturuluyordu;
    // bunlarin buyuk cogunlugu hic kullanilmiyordu (8 font tamamen,
    // digerlerinin de sadece birkac boyutu kullaniliyor). Bu, baslangicta
    // gereksiz yere cok fazla is yapilmasina ve potansiyel zamanlama
    // sorunlarina yol aciyordu. Simdi sadece ~15 nesne olusturuluyor.
    public void init() {
        if (initStarted) return;
        initStarted = true;

        Thread fontThread = new Thread(() -> {
            try {
                initializationFont(durman, "durman.ttf", 12, 13, 14, 15, 19, 21);
                initializationFont(sf_bold, "sf_bold.ttf", 13, 15, 20, 48, 54);
                initializationFont(sf_medium, "sf_medium.ttf", 16, 20);
                initializationFont(icomoon, "icomoon.ttf", 20);
                initializationFont(iconsWex, "iconsWex.ttf", 24);
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

    private void initializationFont(RenderFonts[] fontArray, String fontName, int... sizes) {
        if (fontArray == null) return;
        try (InputStream stream = Objects.requireNonNull(FontUtils.class.getResourceAsStream(fontsDir + fontName))) {
            TrueTypeFont font = TrueTypeFont.load(stream);
            for (int size : sizes) {
                if (size <= 0 || size >= fontArray.length) continue;
                try {
                    fontArray[size] = new RenderFonts(font, size);
                } catch (Throwable innerT) {
                    // Bu boyut icin basarisiz oldu, digerlerine devam et.
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
