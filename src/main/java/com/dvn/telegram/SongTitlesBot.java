package com.dvn.telegram;

import com.dvn.telegram.lastfmapi.SongSearcher;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class SongTitlesBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "@SongTitlesBot";
    }

    @Override
    public String getBotToken() {
        return "5564797213:AAFtzd0ICkugI4huupDJAu4teSfv8xCV_D0";
    }

    public static void main(String[] args) throws TelegramApiException {
        SongTitlesBot bot = new SongTitlesBot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                try {
                    execute(SendMessage.builder()
                            .chatId(message.getChatId().toString())
                            .text(getAnswer(message.getText())).build());
                } catch (TelegramApiException e) {
                    System.out.println(e.getStackTrace());;
                }
            }
        }
    }

    private static String getAnswer(String message) {

        //start of the searching for the tracks by the text of the received message
        SongSearcher songSearcher = new SongSearcher();

        String answer = "Ты имеешь в виду "
                + songSearcher.findSong(message).getTitle() + " by "
                + songSearcher.findSong(message).getArtist() + ", которую слушает "
                + songSearcher.findSong(message).getListeners() + " человек(а)?";

        //deleting of the data of the answer for the previous request
        songSearcher = null;

        return answer;
    }
}