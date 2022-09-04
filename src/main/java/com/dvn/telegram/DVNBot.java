package com.dvn.telegram;

import com.dvn.telegram.lastfmapi.TrackSearcher;
import lombok.SneakyThrows;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class DVNBot extends TelegramLongPollingBot {

    @Override
    public String getBotUsername() {
        return "@SpecialforDVNBot";
    }

    @Override
    public String getBotToken() {
        return "5645589144:AAEeW5vQW5dy9NqfvL3ftHuje_2XzjUT510";
    }

    public static void main(String[] args) throws TelegramApiException {
        DVNBot bot = new DVNBot();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
    }

    @Override
    public void onRegister() {
        super.onRegister();
    }

    @Override
    @SneakyThrows
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                execute(SendMessage.builder()
                        .chatId(message.getChatId().toString()).
                        text(getTrack(message.getText())).build());
            }
        }
    }

    private static String getTrack(String message) throws ParserConfigurationException, IOException, SAXException {
        TrackSearcher trackSearcher = new TrackSearcher();
        String response = "Ты имеешь в виду " +
                trackSearcher.findTrack(message).getTitle() +
                " by " +
                trackSearcher.findTrack(message).getArtist() +
                ", которую слушает " +
                trackSearcher.findTrack(message).getListeners() +
                " человек(а)?";
        trackSearcher = null;
        return response;
    }
}