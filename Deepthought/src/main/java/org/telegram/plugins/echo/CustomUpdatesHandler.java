package org.telegram.plugins.echo;

import com.alibaba.fastjson.JSON;
import org.jetbrains.annotations.NotNull;
import org.telegram.api.chat.TLAbsChat;
import org.telegram.api.message.TLAbsMessage;
import org.telegram.api.message.TLMessage;
import org.telegram.api.update.*;
import org.telegram.api.update.encrypted.TLUpdateEncryptedChatTyping;
import org.telegram.api.update.encrypted.TLUpdateEncryptedMessagesRead;
import org.telegram.api.update.encrypted.TLUpdateEncryption;
import org.telegram.api.update.encrypted.TLUpdateNewEncryptedMessage;
import org.telegram.api.updates.TLAbsUpdates;
import org.telegram.api.updates.TLUpdateShortChatMessage;
import org.telegram.api.updates.TLUpdateShortMessage;
import org.telegram.api.updates.TLUpdateShortSentMessage;
import org.telegram.api.user.TLAbsUser;
import org.telegram.bot.handlers.DefaultUpdatesHandler;
import org.telegram.bot.handlers.interfaces.IChatsHandler;
import org.telegram.bot.handlers.interfaces.IUsersHandler;
import org.telegram.bot.kernel.IKernelComm;
import org.telegram.bot.kernel.database.DatabaseManager;
import org.telegram.bot.kernel.differenceparameters.IDifferenceParametersService;
import org.telegram.bot.services.BotLogger;
import org.telegram.bot.structure.BotConfig;
import org.telegram.bot.structure.IUser;
import org.telegram.plugins.echo.handlers.MessageHandler;
import org.telegram.plugins.echo.handlers.TLMessageHandler;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * @brief TODO
 * @date 16 of October of 2016
 */
public class CustomUpdatesHandler extends DefaultUpdatesHandler {
    private static final String LOGTAG = "CHATUPDATESHANDLER";

    private final DatabaseManager databaseManager;
    private BotConfig botConfig;
    private MessageHandler messageHandler;
    private IUsersHandler usersHandler;
    private IChatsHandler chatsHandler;
    private TLMessageHandler tlMessageHandler;

    public CustomUpdatesHandler(IKernelComm kernelComm, IDifferenceParametersService differenceParametersService, DatabaseManager databaseManager) {
        super(kernelComm, differenceParametersService, databaseManager);
        this.databaseManager = databaseManager;
    }

    public void setConfig(BotConfig botConfig) {
        this.botConfig = botConfig;
    }

    public void setHandlers(MessageHandler messageHandler, IUsersHandler usersHandler, IChatsHandler chatsHandler, TLMessageHandler tlMessageHandler) {
        this.messageHandler = messageHandler;
        this.chatsHandler = chatsHandler;
        this.usersHandler = usersHandler;
        this.tlMessageHandler = tlMessageHandler;
    }

    @Override
    public void onTLUpdateShortMessageCustom(TLUpdateShortMessage update) {
        final IUser user = databaseManager.getUserById(update.getUserId());
        if (user != null) {
            BotLogger.info(LOGTAG, "Received message from: " + update.getUserId());
            messageHandler.handleMessage(user, update);
        }
    }

    @Override
    public void onTLUpdateNewMessageCustom(TLUpdateNewMessage update) {
        onTLAbsMessageCustom(update.getMessage());
    }

    @Override
    protected void onTLAbsMessageCustom(TLAbsMessage message) {
        if (message instanceof TLMessage) {
            BotLogger.debug(LOGTAG, "Received TLMessage");
            onTLMessage((TLMessage) message);
        } else {
            BotLogger.debug(LOGTAG, "Unsupported TLAbsMessage -> " + message.toString());
        }
    }

    @Override
    protected void onUsersCustom(List<TLAbsUser> users) {
        usersHandler.onUsers(users);
    }

    @Override
    protected void onChatsCustom(List<TLAbsChat> chats) {
        chatsHandler.onChats(chats);
    }

    /**
     * Handles TLMessage
     * @param message Message to handle
     */
    private void onTLMessage(@NotNull TLMessage message) {
        if (message.hasFromId()) {
            final IUser user = databaseManager.getUserById(message.getFromId());
            if (user != null) {
                this.tlMessageHandler.onTLMessage(message);
            }
        }
    }

    private void generateLogForMethodInput(TLAbsUpdate tlAbsUpdate) {
        System.out.println(JSON.toJSONString(tlAbsUpdate));
    }

    private void generateLogForMethodInput(TLAbsUpdates tlAbsUpdates) {
        System.out.println(JSON.toJSONString(tlAbsUpdates));
    }

    @Override
    protected void onTLUpdateChatParticipantsCustom(TLUpdateChatParticipants update) {
        super.onTLUpdateChatParticipantsCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateChannelNewMessageCustom(TLUpdateChannelNewMessage update) {
        super.onTLUpdateChannelNewMessageCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateChannelCustom(TLUpdateChannel update) {
        super.onTLUpdateChannelCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateBotInlineQueryCustom(TLUpdateBotInlineQuery update) {
        super.onTLUpdateBotInlineQueryCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateBotInlineSendCustom(TLUpdateBotInlineSend update) {
        super.onTLUpdateBotInlineSendCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateChannelMessageViewsCustom(TLUpdateChannelMessageViews update) {
        super.onTLUpdateChannelMessageViewsCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateChannelPinnedMessageCustom(TLUpdateChannelPinnedMessage update) {
        super.onTLUpdateChannelPinnedMessageCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateChatAdminCustom(TLUpdateChatAdmin update) {
        super.onTLUpdateChatAdminCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateChatParticipantAddCustom(TLUpdateChatParticipantAdd update) {
        super.onTLUpdateChatParticipantAddCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateChatParticipantAdminCustom(TLUpdateChatParticipantAdmin update) {
        super.onTLUpdateChatParticipantAdminCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateChatParticipantDeleteCustom(TLUpdateChatParticipantDelete update) {
        super.onTLUpdateChatParticipantDeleteCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateChatUserTypingCustom(TLUpdateChatUserTyping update) {
        super.onTLUpdateChatUserTypingCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateContactLinkCustom(TLUpdateContactLink update) {
        super.onTLUpdateContactLinkCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateContactRegisteredCustom(TLUpdateContactRegistered update) {
        super.onTLUpdateContactRegisteredCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateDcOptionsCustom(TLUpdateDcOptions update) {
        super.onTLUpdateDcOptionsCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateDeleteChannelMessagesCustom(TLUpdateDeleteChannelMessages update) {
        super.onTLUpdateDeleteChannelMessagesCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateDeleteMessagesCustom(TLUpdateDeleteMessages update) {
        super.onTLUpdateDeleteMessagesCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateEditChannelMessageCustom(TLUpdateEditChannelMessage update) {
        super.onTLUpdateEditChannelMessageCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateMessageIdCustom(TLUpdateMessageId update) {
        super.onTLUpdateMessageIdCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateNewStickerSetCustom(TLUpdateNewStickerSet update) {
        super.onTLUpdateNewStickerSetCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateNotifySettingsCustom(TLUpdateNotifySettings update) {
        super.onTLUpdateNotifySettingsCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdatePrivacyCustom(TLUpdatePrivacy update) {
        super.onTLUpdatePrivacyCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateReadChannelInboxCustom(TLUpdateReadChannelInbox update) {
        super.onTLUpdateReadChannelInboxCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateReadMessagesContentsCustom(TLUpdateReadMessagesContents update) {
        super.onTLUpdateReadMessagesContentsCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateReadMessagesInboxCustom(TLUpdateReadMessagesInbox update) {
        super.onTLUpdateReadMessagesInboxCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateReadMessagesOutboxCustom(TLUpdateReadMessagesOutbox update) {
        super.onTLUpdateReadMessagesOutboxCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateSavedGifsCustom(TLUpdateSavedGifs update) {
        super.onTLUpdateSavedGifsCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateServiceNotificationCustom(TLUpdateServiceNotification update) {
        super.onTLUpdateServiceNotificationCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateStickerSetsCustom(TLUpdateStickerSets update) {
        super.onTLUpdateStickerSetsCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateStickerSetsOrderCustom(TLUpdateStickerSetsOrder update) {
        super.onTLUpdateStickerSetsOrderCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateUserBlockedCustom(TLUpdateUserBlocked update) {
        super.onTLUpdateUserBlockedCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateUserNameCustom(TLUpdateUserName update) {
        super.onTLUpdateUserNameCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateUserPhoneCustom(TLUpdateUserPhone update) {
        super.onTLUpdateUserPhoneCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateUserPhotoCustom(TLUpdateUserPhoto update) {
        super.onTLUpdateUserPhotoCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateUserStatusCustom(TLUpdateUserStatus update) {
        super.onTLUpdateUserStatusCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateUserTypingCustom(TLUpdateUserTyping update) {
        super.onTLUpdateUserTypingCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateWebPageCustom(TLUpdateWebPage update) {
        super.onTLUpdateWebPageCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLFakeUpdateCustom(TLFakeUpdate update) {
        super.onTLFakeUpdateCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateShortChatMessageCustom(TLUpdateShortChatMessage update) {
        super.onTLUpdateShortChatMessageCustom(update);
    }

    @Override
    protected void onTLUpdateShortSentMessageCustom(TLUpdateShortSentMessage update) {
        super.onTLUpdateShortSentMessageCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateEncryptionCustom(TLUpdateEncryption update) {
        super.onTLUpdateEncryptionCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateEncryptedMessagesReadCustom(TLUpdateEncryptedMessagesRead update) {
        super.onTLUpdateEncryptedMessagesReadCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateNewEncryptedMessageCustom(TLUpdateNewEncryptedMessage update) {
        super.onTLUpdateNewEncryptedMessageCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateEncryptedChatTypingCustom(TLUpdateEncryptedChatTyping update) {
        super.onTLUpdateEncryptedChatTypingCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateConfigCustom(TLUpdateConfig update) {
        super.onTLUpdateConfigCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateDraftMessageCustom(TLUpdateDraftMessage update) {
        super.onTLUpdateDraftMessageCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdatePtsChangedCustom(TLUpdatePtsChanged update) {
        super.onTLUpdatePtsChangedCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateReadChannelOutboxCustom(TLUpdateReadChannelOutbox update) {
        super.onTLUpdateReadChannelOutboxCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateReadFeaturedStickersCustom(TLUpdateReadFeaturedStickers update) {
        super.onTLUpdateReadFeaturedStickersCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateRecentStickersCustom(TLUpdateRecentStickers update) {
        super.onTLUpdateRecentStickersCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateBotCallbackQueryCustom(TLUpdateBotCallbackQuery update) {
        super.onTLUpdateBotCallbackQueryCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateEditMessageCustom(TLUpdateEditMessage update) {
        super.onTLUpdateEditMessageCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateInlineBotCallbackQueryCustom(TLUpdateInlineBotCallbackQuery update) {
        super.onTLUpdateInlineBotCallbackQueryCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateChannelWebPageCustom(TLUpdateChannelWebPage update) {
        super.onTLUpdateChannelWebPageCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdatePhoneCallCustom(TLUpdatePhoneCall update) {
        super.onTLUpdatePhoneCallCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateDialogPinnedCustom(TLUpdateDialogPinned update) {
        super.onTLUpdateDialogPinnedCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdatePinnedDialogsCustom(TLUpdatePinnedDialogs update) {
        super.onTLUpdatePinnedDialogsCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateBotWebhookJSONCustom(TLUpdateBotWebhookJSON update) {
        super.onTLUpdateBotWebhookJSONCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateBotWebhookJSONQueryCustom(TLUpdateBotWebhookJSONQuery update) {
        super.onTLUpdateBotWebhookJSONQueryCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateBotShippingQueryCustom(TLUpdateBotShippingQuery update) {
        super.onTLUpdateBotShippingQueryCustom(update);
        this.generateLogForMethodInput(update);
    }

    @Override
    protected void onTLUpdateBotPrecheckoutQueryCustom(TLUpdateBotPrecheckoutQuery update) {
        super.onTLUpdateBotPrecheckoutQueryCustom(update);
        this.generateLogForMethodInput(update);
    }
}
