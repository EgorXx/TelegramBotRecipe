package bot.telegram.recipes.chatbot.presentation;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.Serializable;

public class TelegramBotSender {
    private final AbsSender sender;

    public TelegramBotSender(AbsSender sender) {
        this.sender = sender;
    }

    public void execute(AnswerCallbackQuery answerCallbackQuery) {
        try {
            sender.execute(answerCallbackQuery);
        } catch (TelegramApiException e) {
            throw new IllegalStateException("Не удалось отправить быстрый ответ на callback");
        }
    }

    public Message sendMessage(Long chatId, String text) {
        SendMessage msg = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .build();
        try {
            Message message = sender.execute(msg);
            return message;
        } catch (TelegramApiException e) {
            throw new IllegalStateException("Не удалось отправить сообщение");
        }
    }

    public Message sendMessageWithParseMode(Long chatId, String text, String parseM) {
        SendMessage msg = SendMessage.builder()
                .chatId(chatId.toString())
                .parseMode(parseM)
                .text(text)
                .build();

        try {
            Message message = sender.execute(msg);
            return message;
        } catch (TelegramApiException e) {
            throw new IllegalStateException("Не удалось отправить сообщение");
        }
    }


    public Message sendMessage(Long chatId, String text, InlineKeyboardMarkup keyboard) {
        SendMessage msg = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .replyMarkup(keyboard)
                .build();
        try {
            Message message = sender.execute(msg);
            return message;
        } catch (TelegramApiException e) {
            throw new IllegalStateException("Не удалось отправить сообщение");
        }
    }

    public Message sendMessageWithParseMode(Long chatId, String text, InlineKeyboardMarkup keyboard, String parseM) {
        SendMessage msg = SendMessage.builder()
                .chatId(chatId.toString())
                .parseMode(parseM)
                .text(text)
                .replyMarkup(keyboard)
                .build();
        try {
            Message message = sender.execute(msg);
            return message;
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Message editMessageTextWithKeybord(Long chatId, Integer mId, String newText, InlineKeyboardMarkup newKeyboard) {
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(chatId.toString())
                .messageId(mId)
                .text(newText)
                .replyMarkup(newKeyboard)
                .build();

        try {
            Serializable res = sender.execute(editMessageText);
            if (res instanceof Message) {
                return (Message) res;
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Message editMessageTextWithKeybord(Long chatId, Integer mId, String newText, InlineKeyboardMarkup newKeyboard, String parseM) {
        EditMessageText editMessageText = EditMessageText.builder()
                .chatId(chatId.toString())
                .messageId(mId)
                .text(newText)
                .parseMode(parseM)
                .replyMarkup(newKeyboard)
                .build();

        try {
            Serializable res = sender.execute(editMessageText);
            if (res instanceof Message) {
                return (Message) res;
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteMessage(Long chatId, Integer mId) {
        DeleteMessage deleteMessage = DeleteMessage.builder()
                .chatId(chatId.toString())
                .messageId(mId)
                .build();

        try {
            sender.execute(deleteMessage);
        } catch (TelegramApiException e) {
        }
    }
}

