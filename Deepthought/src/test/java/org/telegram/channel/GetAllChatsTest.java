package org.telegram.channel;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.telegram.api.engine.RpcException;
import org.telegram.api.functions.messages.TLRequestMessagesGetAllChats;
import org.telegram.api.messages.chats.TLAbsMessagesChats;
import org.telegram.framework.AbstractTest;
import org.telegram.tl.TLIntVector;

import java.util.concurrent.ExecutionException;

public class GetAllChatsTest extends AbstractTest {

    @Test
    public void testGetAllChats() {
        TLRequestMessagesGetAllChats tlRequestMessagesGetAllChats
                = new TLRequestMessagesGetAllChats();

        TLIntVector vector = new TLIntVector();
        vector.add(0);
        tlRequestMessagesGetAllChats.setExceptIds(vector);

        try {
            TLAbsMessagesChats tlMessagesChats = this.getKernelComm().doRpcCallSync(tlRequestMessagesGetAllChats);
            System.out.println(JSONObject.toJSONString(tlMessagesChats));

            //tlMessagesChats.getChats().get(0).getId();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (RpcException e) {
            e.printStackTrace();
        }
    }
}
