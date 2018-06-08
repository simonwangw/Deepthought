package org.telegram.channel;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.telegram.api.engine.RpcException;
import org.telegram.api.functions.channels.TLRequestChannelsCreateChannel;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.framework.AbstractTest;

import java.util.concurrent.ExecutionException;

public class CreateChannelTest extends AbstractTest {

    private static final int BROADCASEGROUP = 1;

    private static final int MEGAGROUP = 2;

    @Test
    public void testCreateChannel() {
        TLRequestChannelsCreateChannel tlRequestChannelsCreateChannel
                = new TLRequestChannelsCreateChannel();
        tlRequestChannelsCreateChannel.setTitle("mega-channel"+System.currentTimeMillis());
        tlRequestChannelsCreateChannel.setFlags(MEGAGROUP);
        tlRequestChannelsCreateChannel.setAbout("about");

        try {
            TLAbsUpdates tlAbsUpdate = this.getKernelComm().doRpcCallSync(tlRequestChannelsCreateChannel);

            System.out.println(JSONObject.toJSONString(tlAbsUpdate));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (RpcException e) {
            e.printStackTrace();
        }
    }
}
