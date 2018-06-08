package org.telegram.channel;

import org.junit.Test;
import org.telegram.api.functions.messages.TLRequestMessagesSendMessage;
import org.telegram.api.input.peer.TLInputPeerChannel;
import org.telegram.framework.AbstractTest;

public class BroadcaseTest extends AbstractTest {
    @Test
    public void testBroadcast() {
        TLRequestMessagesSendMessage tlRequestMessagesSendMessage = new TLRequestMessagesSendMessage();
        tlRequestMessagesSendMessage.setFlags(16); //broadcast
        tlRequestMessagesSendMessage.setMessage("this is a broadcast");
        TLInputPeerChannel tlAbsPeer = new TLInputPeerChannel();
        tlAbsPeer.setChannelId(1160021388);
        tlAbsPeer.setAccessHash(6598956788439284138L);
        tlRequestMessagesSendMessage.setPeer(tlAbsPeer);
        tlRequestMessagesSendMessage.setRandomId(System.currentTimeMillis());

        this.sendRegularRequest(tlRequestMessagesSendMessage);
    }
}
