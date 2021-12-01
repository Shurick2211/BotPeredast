import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.exceptions.TelegramApiException;

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
        buttonsRow.add(new InlineKeyboardButton().setText("Ответить!").setCallbackData(dat));
        buttons.add(buttonsRow);
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        inlineKeyboard.setKeyboard(buttons);
        sendMessage.setReplyMarkup(inlineKeyboard);
    }
    /*
    private EditMessageReplyMarkup setInlineOt(long chatId , int mesegeID , String name, String dat) {
        List<List<InlineKeyboardButton>> buttons = new ArrayList<List<InlineKeyboardButton>>();
        List<InlineKeyboardButton> buttons1 = new ArrayList<InlineKeyboardButton>();
        buttons1.add(new InlineKeyboardButton().setText(name).setCallbackData(dat));
        buttons.add(buttons1);
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        inlineKeyboard.setKeyboard(buttons);

        return new EditMessageReplyMarkup().setChatId(chatId).setMessageId(mesegeID).setReplyMarkup(inlineKeyboard);
    }
    */

}
