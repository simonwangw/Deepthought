package org.telegram.framework;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * some constants for testing
 */
public class TestConstants {
    public static final int TEST_CHANNEL_ID = 1174366442;

    public static final long TEST_CHANNEL_HASH = 7055224328944874088L;

    public static final int TEST_BROADCAST_CHANNEL_ID = 1295525098;

    public static final long TEST_BROADCAST_CHANNEL_HASH = 9030140708348872057L;

    public static final int TEST_USER_ID = 581092383;

    public static final long TEST_USER_HASH = 7587085723650870029L;

    public static final Map<Integer, Long> userInfoMap = ImmutableMap.of(600033548, 4779419125502341675L,
            581092383,7587085723650870029L, 563840139,2978680338987691618L);

    static class UserInfo {
        private long accessHash;

        private int id;

        public UserInfo(long accessHash, int id) {
            this.accessHash = accessHash;
            this.id = id;
        }

        public void setAccessHash(long accessHash) {
            this.accessHash = accessHash;
        }

        public void setId(int id) {
            this.id = id;
        }

        public long getAccessHash() {
            return accessHash;
        }

        public int getId() {
            return id;
        }
    }

    public static void main(String[] args) {
        long l = -5671066344313595079L & Long.MAX_VALUE;
        System.out.println(l);
    }

}
