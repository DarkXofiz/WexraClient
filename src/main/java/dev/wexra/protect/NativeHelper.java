package dev.wexra.protect;


import dev.wexra.manager.Manager;
import dev.wexra.protect.loader.NativeProfile;

public class NativeHelper {
    public static void setProfile() {
        Manager.USER_PROFILE = new UserProfile(
                "levin1337",
                "Deleoper",
                "09.11.2025"
        );
    }
}
