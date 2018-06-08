package org.telegram.channel;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.telegram.api.contacts.TLImportedContacts;
import org.telegram.api.engine.RpcException;
import org.telegram.api.engine.TimeoutException;
import org.telegram.api.functions.contacts.TLRequestContactsImportContacts;
import org.telegram.api.input.TLInputPhoneContact;
import org.telegram.bot.TelegramFunctionCallback;
import org.telegram.framework.AbstractTest;
import org.telegram.tl.TLVector;

public class ImportContactTest extends AbstractTest {

    @Test
    public void testImportContact() {
        TLRequestContactsImportContacts tlRequestContactsImportContacts =
                new TLRequestContactsImportContacts();
        TLVector<TLInputPhoneContact> tlVector = new TLVector<TLInputPhoneContact>();
        TLInputPhoneContact tlInputPhoneContact = new TLInputPhoneContact();
        tlInputPhoneContact.setPhone("+8613811155779");
        tlInputPhoneContact.setClientId(123L);
        tlInputPhoneContact.setLastName("liu");
        tlInputPhoneContact.setFirstName("chong");

        tlVector.add(tlInputPhoneContact);
        tlRequestContactsImportContacts.setContacts(tlVector);

        this.getKernelComm().doRpcCallAsync(tlRequestContactsImportContacts, new TelegramFunctionCallback<TLImportedContacts>() {
            @Override
            public void onSuccess(TLImportedContacts result) {
                System.out.println(JSONObject.toJSONString(result));
            }

            @Override
            public void onRpcError(RpcException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onTimeout(TimeoutException e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onUnknownError(Throwable e) {
                System.out.println(e.getMessage());
            }
        });
    }
}
