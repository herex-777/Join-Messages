package me.herex.joinmessages.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SilentJoinManager {

    private static final Set<UUID> SILENT_PLAYERS = new HashSet<>();

    public static boolean isSilent(UUID uuid) {
        return SILENT_PLAYERS.contains(uuid);
    }

    public static boolean toggle(UUID uuid) {
        if (SILENT_PLAYERS.contains(uuid)) {
            SILENT_PLAYERS.remove(uuid);
            return false; // disabled
        } else {
            SILENT_PLAYERS.add(uuid);
            return true; // enabled
        }
    }

    public static void remove(UUID uuid) {
        SILENT_PLAYERS.remove(uuid);
    }
}
