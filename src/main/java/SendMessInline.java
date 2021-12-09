import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class SendMessInline {




    public void send(String mess) {
        String text=mess.trim().split("=")[0];
        String dat=mess.trim().split("=")[1];

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(Bot.SUPERUSER);
        sendMessage.enableMarkdown(true);
        sendMessage.enableHtml(true);
        sendMessage.setText(text);

        try {
            setInButon(sendMessage, dat);
            Bot.bot().execute(sendMessage);
        } catch (TelegramApiException e) {
                 e.printStackTrace();
        }
    }

    private void setInButon(SendMessage sendMessage, String dat) {

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>();
        List<InlineKeyboardButton> buttonsRow = new ArrayList<>();
        InlineKeyboardButton inButton =new InlineKeyboardButton();
        inButton.setText("Ответить!");
        inButton.setCallbackData(dat+"");
        buttonsRow.add(inButton);
        buttons.add(buttonsRow);
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        inlineKeyboard.setKeyboard(buttons);
        sendMessage.setReplyMarkup(inlineKeyboard);
    }


}
