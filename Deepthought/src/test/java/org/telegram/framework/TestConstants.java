package org.telegram.framework;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * some constants for testing
 */
public class TestConstants {
    public static final int TEST_CHANNEL_ID = 1160021388;

    public static final long TEST_CHANNEL_HASH = 6598956788439284138L;

    public static final Map<Integer, Long> userInfoMap = ImmutableMap.of(581092383, -1636286313203905779L);

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

}
