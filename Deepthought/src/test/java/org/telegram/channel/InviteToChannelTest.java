package org.telegram.channel;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.telegram.api.contact.TLContact;
import org.telegram.api.contacts.TLAbsContacts;
import org.telegram.api.contacts.TLContacts;
import org.telegram.api.engine.RpcException;
import org.telegram.api.functions.channels.TLRequestChannelsInviteToChannel;
import org.telegram.api.functions.contacts.TLRequestContactsGetContacts;
import org.telegram.api.functions.users.TLRequestUsersGetUsers;
import org.telegram.api.input.chat.TLInputChannel;
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.api.input.user.TLInputUser;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.framework.AbstractTest;
import org.telegram.framework.TestConstants;
import org.telegram.tl.TLVector;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * not completed yet
 */
public class InviteToChannelTest extends AbstractTest {

    @Test
    public void testInviteToChannel() {
        TLRequestChannelsInviteToChannel tlRequestChannelsInviteToChannel =
                new TLRequestChannelsInviteToChannel();


        TLInputChannel tlInputChannel = new TLInputChannel();
        tlInputChannel.setChannelId(TestConstants.TEST_CHANNEL_ID);
        tlInputChannel.setAccessHash(TestConstants.TEST_CHANNEL_HASH);
        tlRequestChannelsInviteToChannel.setChannel(tlInputChannel);

        try {
            tlRequestChannelsInviteToChannel.setChannel(tlInputChannel);
            TLVector<TLAbsInputUser> vector = new TLVector<>();
            Map<Integer, Long> userInfoMap = TestConstants.userInfoMap;

            for (Integer userId : userInfoMap.keySet()) {
                TLInputUser tlInputUser = new TLInputUser();
                tlInputUser.setUserId(userId);
                tlInputUser.setAccessHash(userInfoMap.get(userId));
                vector.add(tlInputUser);
            }

            tlRequestChannelsInviteToChannel.setUsers(vector);
            TLAbsUpdates updates = this.getKernelComm().doRpcCallSync(tlRequestChannelsInviteToChannel);
            System.out.println(JSONObject.toJSONString(updates));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (RpcException e) {
            e.printStackTrace();
        }
    }

}
