package com.dvn.telegrambot;

import com.dvn.telegrambot.lastfmapi.Song;
import com.dvn.telegrambot.lastfmapi.SongSearcher;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


@Component("botBean")
public class SongTitlesBot extends TelegramLongPollingBot {

    //Constructor
    public SongTitlesBot() {
        TelegramBotsApi telegramBotsApi = null;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "@SongTitlesBot";
    }

    @Override
    public String getBotToken() {
        return "5564797213:AAFtzd0ICkugI4huupDJAu4teSfv8xCV_D0";
    }


    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message receivedMessage = update.getMessage();

            if (receivedMessage.hasText()) {
                SendMessage reply = new SendMessage(
                        receivedMessage.getChatId().toString(),
                        getAnswer(receivedMessage)
                    );

                try {
                    execute(reply);
                } catch (TelegramApiException e) {
                    System.out.println(e.getStackTrace());
                }
            }
            receivedMessage = null;
        }
    }

    public String getAnswer(Message message) {

        //start of the searching for the tracks by the text of the received message
        SongSearcher songSearcher = new SongSearcher();
        Song song = songSearcher.findSong(message.getText());

        String answer = "Ты имеешь в виду "
                + song.getTitle() + " by "
                + song.getArtist() + ", которую слушает "
                + song.getListeners() + " человек(а)?";

        //deleting of the data of the answer for the previous request
        songSearcher = null;

        return answer;
    }
}