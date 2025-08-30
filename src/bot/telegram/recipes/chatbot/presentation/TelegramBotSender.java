package bot.telegram.recipes.chatbot.presentation;

import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class TelegramBotSender {
    private final AbsSender sender;

    public TelegramBotSender(AbsSender sender) {
        this.sender = sender;
    }

    public void execute(SendMessage sendMessage) {
        try {
            sender.execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new IllegalStateException("Не удалось отправить сообщение");
        }
    }

    public void execute(AnswerCallbackQuery answerCallbackQuery) {
        try {
            sender.execute(answerCallbackQuery);
        } catch (TelegramApiException e) {
            throw new IllegalStateException("Не удалось отправить быстрый ответ на callback");
        }
    }

    public void sendMessage(Long chatId, String text) {
        SendMessage msg = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .build();
        try {
            sender.execute(msg);
        } catch (TelegramApiException e) {
            throw new IllegalStateException("Не удалось отправить сообщение");
        }
    }

    public void sendMessageWithParseMode(Long chatId, String text, String parseM) {
        SendMessage msg = SendMessage.builder()
                .chatId(chatId.toString())
                .parseMode(parseM)
                .text(text)
                .build();

        try {
            sender.execute(msg);
        } catch (TelegramApiException e) {
            throw new IllegalStateException("Не удалось отправить сообщение");
        }
    }


    public void sendMessage(Long chatId, String text, InlineKeyboardMarkup keyboard) {
        SendMessage msg = SendMessage.builder()
                .chatId(chatId.toString())
                .text(text)
                .replyMarkup(keyboard)
                .build();
        try {
            sender.execute(msg);
        } catch (TelegramApiException e) {
            throw new IllegalStateException("Не удалось отправить сообщение");
        }
    }

    public void sendMessageWithParseMode(Long chatId, String text, InlineKeyboardMarkup keyboard, String parseM) {
        SendMessage msg = SendMessage.builder()
                .chatId(chatId.toString())
                .parseMode(parseM)
                .text(text)
                .replyMarkup(keyboard)
                .build();
        try {
            sender.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

