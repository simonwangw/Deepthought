package org.telegram.channel;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.telegram.api.engine.RpcException;
import org.telegram.api.functions.channels.TLRequestChannelsCreateChannel;
import org.telegram.api.functions.messages.TLRequestMessagesSendMessage;
import org.telegram.api.input.peer.TLInputPeerChannel;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.framework.AbstractTest;
import org.telegram.framework.TestConstants;

import java.util.concurrent.ExecutionException;

public class BroadcaseTest extends AbstractTest {
    @Test
    public void testBroadcast() {
        TLRequestMessagesSendMessage tlRequestMessagesSendMessage = new TLRequestMessagesSendMessage();
        tlRequestMessagesSendMessage.setFlags(16); //broadcast
        tlRequestMessagesSendMessage.setMessage("this is a broadcast");
        TLInputPeerChannel tlAbsPeer = new TLInputPeerChannel();
        tlAbsPeer.setChannelId(TestConstants.TEST_CHANNEL_ID);
        tlAbsPeer.setAccessHash(TestConstants.TEST_CHANNEL_HASH);
        tlRequestMessagesSendMessage.setPeer(tlAbsPeer);
        tlRequestMessagesSendMessage.setRandomId(System.currentTimeMillis());

        this.sendRegularRequest(tlRequestMessagesSendMessage);
    }
}
