package by.vorakh.alex.best_belarus_places_bot.bot;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import by.vorakh.alex.best_belarus_places_bot.service.CityPlaceService;

@Component
public class Bot extends TelegramLongPollingBot {
    
    private final static String NO_FOUND_PLACES = "Sorry, I do not know about cityplaces in this city.";
    private final static String WELCOME = "Welcome to Belarus!\nWrite a city and I will recommend some places to visit.";
    
    @Autowired
    private CityPlaceService service;
    
    @Value("${bot.token}")
    private String token;
    
    @Value("${bot.username}")
    private String username;

    private static final Logger logger = LoggerFactory
            .getLogger(Bot.class);
    
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            SendMessage response = new SendMessage();
            Long chatId = message.getChatId();
            response.setChatId(chatId);
            String text = message.getText();
            switch (text) {
                case "/start":
                    response.setText(WELCOME);
                    break;
                default:
                    String placesInfo = service.getCityPlaceInfo(text).orElse(NO_FOUND_PLACES);
                    response.setText(placesInfo);
                    break;
            }
            try {
                execute(response);
                logger.info("Sent message \"{}\" to {}", text, chatId);
            } catch (TelegramApiException e) {
                logger.error("Failed to send message \"{}\" to {} due to error: {}", text, chatId, e.getMessage());
            }
        }

    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @PostConstruct
    public void start() {
        logger.info("username: {}, token: {}", username, token);
    }

}
