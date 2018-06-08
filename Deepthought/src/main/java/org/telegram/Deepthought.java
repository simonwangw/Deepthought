package org.telegram;

import com.google.common.collect.ImmutableList;
import org.telegram.api.chat.channel.TLChannel;
import org.telegram.api.contacts.TLImportedContacts;
import org.telegram.api.contacts.TLResolvedPeer;
import org.telegram.api.engine.RpcException;
import org.telegram.api.engine.TimeoutException;
import org.telegram.api.functions.channels.TLRequestChannelsGetParticipants;
import org.telegram.api.functions.channels.TLRequestChannelsJoinChannel;
import org.telegram.api.functions.contacts.TLRequestContactsImportContacts;
import org.telegram.api.functions.contacts.TLRequestContactsResolveUsername;
import org.telegram.api.functions.messages.TLRequestMessagesGetAllChats;
import org.telegram.api.functions.messages.TLRequestMessagesImportChatInvite;
import org.telegram.api.input.TLInputPhoneContact;
import org.telegram.api.input.chat.TLInputChannel;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.bot.TelegramFunctionCallback;
import org.telegram.bot.handlers.interfaces.IChatsHandler;
import org.telegram.bot.handlers.interfaces.IUsersHandler;
import org.telegram.bot.kernel.IKernelComm;
import org.telegram.bot.kernel.TelegramBot;
import org.telegram.bot.services.BotLogger;
import org.telegram.bot.structure.BotConfig;
import org.telegram.bot.structure.LoginStatus;
import org.telegram.plugins.echo.BotConfigImpl;
import org.telegram.plugins.echo.ChatUpdatesBuilderImpl;
import org.telegram.plugins.echo.CustomUpdatesHandler;
import org.telegram.plugins.echo.database.DatabaseManagerImpl;
import org.telegram.plugins.echo.handlers.ChatsHandler;
import org.telegram.plugins.echo.handlers.MessageHandler;
import org.telegram.plugins.echo.handlers.TLMessageHandler;
import org.telegram.plugins.echo.handlers.UsersHandler;
import org.telegram.tl.TLIntVector;
import org.telegram.tl.TLObject;
import org.telegram.tl.TLVector;

import java.util.List;
import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 16 of October of 2016
 */
public class Deepthought {
    private static final int APIKEY = 265470; // your api key
    private static final String APIHASH = "fc1ab20a1b78e3fa9c595455881778e4"; // your api hash
    //    private static final String PHONENUMBER = "+19253656716"; // Your phone number
    private static final String PHONENUMBER = "+8613811155779";

    public static void main(String[] args) {
        Logger.getGlobal().addHandler(new ConsoleHandler());
        Logger.getGlobal().setLevel(Level.ALL);

        final DatabaseManagerImpl databaseManager = new DatabaseManagerImpl();
        final BotConfig botConfig = new BotConfigImpl(PHONENUMBER);

        final IUsersHandler usersHandler = new UsersHandler(databaseManager);
        final IChatsHandler chatsHandler = new ChatsHandler(databaseManager);
        final MessageHandler messageHandler = new MessageHandler();
        final TLMessageHandler tlMessageHandler = new TLMessageHandler(messageHandler, databaseManager);

        final ChatUpdatesBuilderImpl builder = new ChatUpdatesBuilderImpl(CustomUpdatesHandler.class);
        builder.setBotConfig(botConfig)
                .setDatabaseManager(databaseManager)
                .setUsersHandler(usersHandler)
                .setChatsHandler(chatsHandler)
                .setMessageHandler(messageHandler)
                .setTlMessageHandler(tlMessageHandler);

        try {
            final TelegramBot kernel = new TelegramBot(botConfig, builder, APIKEY, APIHASH);
            LoginStatus status = kernel.init();
            if (status == LoginStatus.CODESENT) {
                Scanner in = new Scanner(System.in);
                boolean success = kernel.getKernelAuth().setAuthCode(in.nextLine().trim());
                if (success) {
                    status = LoginStatus.ALREADYLOGGED;
                }
            }
            if (status == LoginStatus.ALREADYLOGGED) {
                kernel.startBot();
            } else {
                throw new Exception("Failed to log in: " + status);
            }

            TLRequestMessagesGetAllChats tlRequestMessagesGetAllChats = new TLRequestMessagesGetAllChats();

            TLIntVector intVector = new TLIntVector();
            intVector.add(0);
            tlRequestMessagesGetAllChats.setExceptIds(intVector);

            kernel.getKernelComm().doRpcCallAsync(tlRequestMessagesGetAllChats, new TelegramFunctionCallback(){
                @Override
                public void onSuccess(TLObject result) {
                    System.out.println(result);
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


            TLRequestContactsImportContacts tlRequestContactsImportContacts =
                    new TLRequestContactsImportContacts();
            TLVector<TLInputPhoneContact> tlVector = new TLVector<TLInputPhoneContact>();
            TLInputPhoneContact tlInputPhoneContact = new TLInputPhoneContact();
            tlInputPhoneContact.setPhone("+8613811155779");
            tlInputPhoneContact.setClientId(123L);
            tlInputPhoneContact.setLastName("wang");
            tlInputPhoneContact.setFirstName("rui");

            tlVector.add(tlInputPhoneContact);
            tlRequestContactsImportContacts.setContacts(tlVector);

            kernel.getKernelComm().doRpcCallAsync(tlRequestContactsImportContacts, new TelegramFunctionCallback<TLImportedContacts>() {
                @Override
                public void onSuccess(TLImportedContacts result) {
                    System.out.println(result.toString());
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

            List<String> links = ImmutableList.of("https://telegram.me/examplechannel");
            for (String str : links){
                if (str.contains("telegram.me/joinchat")){
                    String hash = str.split("/")[(str.split("/").length)-1];
                    TLRequestMessagesImportChatInvite in = new TLRequestMessagesImportChatInvite();
                    in.setHash(hash);
                    try {
                        TLAbsUpdates bb = kernel.getKernelComm().getApi().doRpcCall(in);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (str.contains("telegram.me/")){
                    String username = str.split("/")[(str.split("/").length)-1];
                    try {
                        IKernelComm kernelComm = kernel.getKernelComm();
                        TLRequestContactsResolveUsername ru = new TLRequestContactsResolveUsername();
                        ru.setUsername(username);
                        TLResolvedPeer peer = kernelComm.getApi().doRpcCall(ru);
                        TLRequestChannelsJoinChannel join = new TLRequestChannelsJoinChannel();
                        TLInputChannel ch = new TLInputChannel();
                        ch.setChannelId(peer.getChats().get(0).getId());
                        ch.setAccessHash(((TLChannel) peer.getChats().get(0)).getAccessHash());
                        join.setChannel(ch);
                        kernelComm.getApi().doRpcCall(join);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            BotLogger.severe("MAIN", e);
        }
    }
}
