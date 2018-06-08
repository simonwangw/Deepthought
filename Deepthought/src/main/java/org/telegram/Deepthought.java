package org.telegram;

import org.telegram.api.engine.RpcException;
import org.telegram.api.engine.TimeoutException;
import org.telegram.bot.TelegramFunctionCallback;
import org.telegram.bot.handlers.interfaces.IChatsHandler;
import org.telegram.bot.handlers.interfaces.IUsersHandler;
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
import org.telegram.api.functions.messages.TLRequestMessagesGetAllChats;
import org.telegram.tl.TLIntVector;
import org.telegram.tl.TLObject;

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
    private static final String PHONENUMBER = "+18022760178";

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
            TLIntVector vector = new TLIntVector();
            vector.add(0);
            tlRequestMessagesGetAllChats.setExceptIds(vector);

            kernel.getKernelComm().doRpcCallAsync(tlRequestMessagesGetAllChats, new TelegramFunctionCallback(){
                @Override
                public void onSuccess(TLObject result) {
                    System.out.println(result);
                }

                @Override
                public void onRpcError(RpcException e) {
                }

                @Override
                public void onTimeout(TimeoutException e) {
                }

                @Override
                public void onUnknownError(Throwable e) {
                }
            });

        } catch (Exception e) {
            BotLogger.severe("MAIN", e);
        }
    }
}
