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
import org.telegram.api.input.user.TLAbsInputUser;
import org.telegram.api.input.user.TLInputUser;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.api.user.TLAbsUser;
import org.telegram.framework.AbstractTest;
import org.telegram.tl.TLVector;

import java.util.concurrent.ExecutionException;

/**
 * not completed yet
 */
public class InviteToChannelTest extends AbstractTest {

    @Test
    public void testInviteToChannel() {
        TLRequestContactsGetContacts tlRequestContactsGetContacts = new TLRequestContactsGetContacts();
        tlRequestContactsGetContacts.setHash(AbstractTest.APIHASH);

        TLRequestChannelsInviteToChannel tlRequestChannelsInviteToChannel =
                new TLRequestChannelsInviteToChannel();

        TLRequestUsersGetUsers tlRequestUsersGetUsers = new TLRequestUsersGetUsers();

        try {
            TLAbsContacts tlAbsContacts = this.getKernelComm().doRpcCallSync(tlRequestContactsGetContacts);
            TLContacts tlContacts = (TLContacts) tlAbsContacts;
            TLVector<TLContact> vector = tlContacts.getContacts();

            TLVector<TLAbsInputUser> userTLVector = new TLVector<>();

            for (TLContact contact : vector) {
                int userId = contact.getUserId();
                TLInputUser inputUser = new TLInputUser();
                inputUser.setUserId(contact.getUserId());
                userTLVector.add(inputUser);
            }

            tlRequestUsersGetUsers.setId(userTLVector);
            TLVector<TLAbsUser> tlAbsUserTLVector = this.getKernelComm().doRpcCallSync(tlRequestUsersGetUsers);

//            tlRequestChannelsInviteToChannel.setUsers();
            TLAbsUpdates updates = this.getKernelComm().doRpcCallSync(tlRequestChannelsInviteToChannel);
            System.out.println(JSONObject.toJSONString(updates));
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (RpcException e) {
            e.printStackTrace();
        }
    }

}
