package org.telegram.channel;

import org.junit.Test;
import org.telegram.api.functions.channels.TLRequestChannelsKickFromChannel;
import org.telegram.api.input.chat.TLInputChannel;
import org.telegram.api.input.user.TLInputUser;
import org.telegram.framework.AbstractTest;
import org.telegram.framework.TestConstants;

public class KickFromChannelTest extends AbstractTest {

    @Test
    public void testKickMember() {
        TLRequestChannelsKickFromChannel tlRequestChannelsKickFromChannel =
                new TLRequestChannelsKickFromChannel();
        tlRequestChannelsKickFromChannel.setKicked(true);

        TLInputChannel tlInputChannel = new TLInputChannel();
        tlInputChannel.setAccessHash(TestConstants.TEST_BROADCAST_CHANNEL_HASH);
        tlInputChannel.setChannelId(TestConstants.TEST_BROADCAST_CHANNEL_ID);
        tlRequestChannelsKickFromChannel.setChannel(tlInputChannel);

        TLInputUser tlInputUser = new TLInputUser();
        tlInputUser.setUserId(TestConstants.TEST_USER_ID);
        tlInputUser.setAccessHash(TestConstants.TEST_USER_HASH);

        tlRequestChannelsKickFromChannel.setUserId(tlInputUser);
        this.sendRegularRequest(tlRequestChannelsKickFromChannel);
    }

}
