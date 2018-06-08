package org.telegram.channel;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import org.junit.Test;
import org.telegram.api.chat.channel.TLChannel;
import org.telegram.api.contacts.TLResolvedPeer;
import org.telegram.api.functions.channels.TLRequestChannelsJoinChannel;
import org.telegram.api.functions.contacts.TLRequestContactsResolveUsername;
import org.telegram.api.functions.messages.TLRequestMessagesImportChatInvite;
import org.telegram.api.input.chat.TLInputChannel;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.bot.kernel.IKernelComm;
import org.telegram.framework.AbstractTest;

import java.util.List;
import java.util.logging.Logger;

public class JoinChannelTest extends AbstractTest {

    @Test
    public void testJoinChannel() {
        IKernelComm kernelComm = this.getKernelComm();
        List<String> links = ImmutableList.of("https://telegram.me/examplechannel");
        for (String str : links){
            if (str.contains("joinchat")){
                String hash = str.split("/")[(str.split("/").length)-1];
                TLRequestMessagesImportChatInvite in = new TLRequestMessagesImportChatInvite();
                in.setHash(hash);
                try {
                    TLAbsUpdates bb = kernelComm.getApi().doRpcCall(in);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (str.contains("telegram.me/") || str.contains("t.me/")){
                String username = str.split("/")[(str.split("/").length)-1];
                try {
                    TLRequestContactsResolveUsername ru = new TLRequestContactsResolveUsername();
                    ru.setUsername(username);
                    TLResolvedPeer peer = kernelComm.getApi().doRpcCall(ru);
                    TLRequestChannelsJoinChannel join = new TLRequestChannelsJoinChannel();
                    TLInputChannel ch = new TLInputChannel();
                    int channelId = peer.getChats().get(0).getId();
                    ch.setChannelId(channelId);
                    long hash = ((TLChannel) peer.getChats().get(0)).getAccessHash();
                    ch.setAccessHash(hash);

                    Logger.getGlobal().info("channelid is: " + channelId + " with hash: " + hash);
                    join.setChannel(ch);
                    TLAbsUpdates tlAbsUpdates = kernelComm.getApi().doRpcCall(join);
                    System.out.println("response for joinChannel: " + JSONObject.toJSONString(tlAbsUpdates));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
