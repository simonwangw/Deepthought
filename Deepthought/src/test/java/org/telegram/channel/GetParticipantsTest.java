package org.telegram.channel;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.telegram.api.channel.TLChannelParticipants;
import org.telegram.api.channel.participants.filters.TLChannelParticipantsFilterAdmins;
import org.telegram.api.channel.participants.filters.TLChannelParticipantsFilterRecent;
import org.telegram.api.engine.RpcException;
import org.telegram.api.functions.channels.TLRequestChannelsGetParticipants;
import org.telegram.api.input.chat.TLInputChannel;
import org.telegram.framework.AbstractTest;

import java.util.concurrent.ExecutionException;

public class GetParticipantsTest extends AbstractTest {

    @Test
    public void testGetParticipants() {
        TLRequestChannelsGetParticipants tlRequestChannelsGetParticipants
                = new TLRequestChannelsGetParticipants();
        TLInputChannel tlAbsInputChannel = new TLInputChannel();
        tlAbsInputChannel.setChannelId(1160021388);
        tlAbsInputChannel.setAccessHash(6598956788439284138L);
        tlRequestChannelsGetParticipants.setChannel(tlAbsInputChannel);
        tlRequestChannelsGetParticipants.setFilter(new TLChannelParticipantsFilterRecent());
        tlRequestChannelsGetParticipants.setLimit(100);
        tlRequestChannelsGetParticipants.setOffset(0);

        try {
            TLChannelParticipants tlChannelParticipants = this.getKernelComm().doRpcCallSync(tlRequestChannelsGetParticipants);

            System.out.println(JSONObject.toJSONString(tlChannelParticipants));
            System.out.println(tlChannelParticipants.getUsers().size());
            System.out.println(tlChannelParticipants.getParticipants().get(0).getClassId());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (RpcException e) {
            e.printStackTrace();
        }
    }

}
