import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {


    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(new destiny_of_bot());
    }


    public static class destiny_of_bot extends TelegramLongPollingBot {

        static final String TOKEN_BOT = "5712114808:AAHJ6IEcE9rmvGOppcmpNzahcMIaNBhreks";
        static final String NAME_BOT = "destiny_of_bot";

        @Override
        public String getBotUsername() {
            return NAME_BOT;
        }

        @Override
        public String getBotToken() {
            return TOKEN_BOT;
        }

        private final List<String> ANSWERS = Arrays.asList("Бесспорно", "Предрешено", "Никаких сомнений",
                "Определённо да", "Можешь быть уверен в этом", "Мне кажется — «да»",
                "Вероятнее всего", "Хорошие перспективы", "Знаки говорят — «да»", "Да",
                "Пока не ясно, попробуй снова", "Спроси позже", "Лучше не рассказывать",
                "Сейчас нельзя предсказать", "Сконцентрируйся и спроси опять",
                "Даже не думай", "Мой ответ — «нет»", "По моим данным — «нет»",
                "Перспективы не очень хорошие", "Весьма сомнительно");


        @Override
        public void onUpdateReceived(Update update) {
            String chatId = update.getMessage().getChatId().toString();

            sendMsg(ANSWERS.get((int) (Math.random() * 20)), chatId);
        }

        void sendMsg(String text, String chatId) {
            SendMessage msg = new SendMessage();
            setButtons(msg);
            msg.setChatId(chatId);
            msg.setProtectContent(true);
            msg.setText(text);
            try {
                execute(msg);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }

        public synchronized void setButtons(SendMessage sendMessage) {

            // Create a keyboard
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);

            // Create a list of keyboard rows
            List keyboard = new ArrayList<>();
            // First keyboard row
            KeyboardRow keyboardFirstRow = new KeyboardRow();

            // Add buttons to the first keyboard row
            keyboardFirstRow.add(new KeyboardButton("Хочу узнать твоё мнение!"));

            // Add all of the keyboard rows to the list
            keyboard.add(keyboardFirstRow);
            // and assign this list to our keyboard
            replyKeyboardMarkup.setKeyboard(keyboard);
        }
    }
}
