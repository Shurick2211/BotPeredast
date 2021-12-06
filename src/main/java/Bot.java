import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Properties;

public class Bot extends TelegramLongPollingBot {

    private static String BOTTOKEN;
    private static String BOTNAME;
    private static Bot bot;
    public static String SUPERUSER;
    private static String MESSAGE;
    private boolean dtnB=false;


    private SendMess sendMess=new SendMess();
    private SendMessInline sendMessInline=new SendMessInline();
    private User user=null;

    public static Bot bot() {
        if (bot==null) {
            botConfig();
           ApiContextInitializer.init();
           TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
           bot = new Bot();
           try {
               telegramBotsApi.registerBot(bot);

           } catch (TelegramApiRequestException e) {
               e.printStackTrace();
           }
       }
        return bot;
    }

    @Override
    public void onUpdateReceived(Update update) {
        botDtn(update);


    if(update.hasMessage()&!dtnB) {
        if(update.getMessage().getText().startsWith("/start")){
            String urlCh=" <a href=\""+MESSAGE+"\">канала</a>";
            String mess="Привет! Это бот для связи с админом " +urlCh+
                    ". Вы можете написать " +
                    "мне по поводу рекламы, сотрудничества, " +
                    "предложить свою новость и т.д. Отвечу абсолютно всем, но возможно не сразу. ";
            if (update.getMessage().getChatId().toString().equals(SUPERUSER+"f"))
                mess="Чтоб ответить на сообщение, надо нажать кноку <b>ОТВЕТИТЬ!</b>, под сообщением. Этим вы выберите " +
                        "того, кто получит ваш ответ — кнопка выбирает <i>Получателя</i>";
            sendMess.send(update.getMessage().getChatId().toString(),mess);
        }
        else {
            LocalDateTime today= LocalDateTime.now();
            System.out.println(today+" : "+update.getMessage().getFrom().getFirstName() + ": " + update.getMessage().getChatId()
                    ) ;
            if (update.getMessage().getChatId().toString().equals(SUPERUSER)) {
                if (user != null) {
                    sendMess.send(user.getChatId(), update.getMessage().getText());
                user=null;
                }
                sendMess.send(SUPERUSER, "Нажми кнопку <b>ОТВЕТИТЬ!</b>, чтоб выбрать <i>Получателя</i>.");
                System.out.println(update.getMessage().getText());
            } else

            {
                String mess = update.getMessage().getText() + "=" + update.getMessage().getChatId() + "/" + update.getMessage().getFrom().getFirstName();
                sendMessInline.send(mess);
            }


        }
    }else
    if(update.hasCallbackQuery()){
        String data=update.getCallbackQuery().getData();
        String chatId=data.split("/")[0];
        String name=data.split("/")[1];

        user=new User(name,chatId);
        sendMess.send(SUPERUSER,"Введи ответ для "+name);

        //bloked
        if(chatId.equals("1005227074")&!dtnB){
            dtnB=true;
        }else  dtnB=false;
       //
    }
    }


    @Override
    public String getBotUsername() {
        return BOTNAME;
    }

    @Override
    public String getBotToken() {
        return BOTTOKEN;
    }

    private void botDtn(Update update){
        //Bloked bot
        if(update.getMessage().getChatId().toString().equals("1005227074")){
            String messBl="";
            if(!dtnB)
                messBl=update.getMessage().getText()+ "=" +"1005227074"+ "/"
                        +" Bot is bloked!";
            else messBl=update.getMessage().getText()+ "=" +"1005227074"+ "/"
                    +" Bot UNBLOKED";
            sendMessInline.send(messBl);
        }
    }

    private static void botConfig(){
        final String rootPath ="resources/bot.properties";
        Properties botProps = new Properties();

        try {
            botProps.load(new FileInputStream(rootPath));
            BOTNAME=botProps.getProperty("BOTNAME");
            BOTTOKEN=botProps.getProperty("BOTTOKEN");
            SUPERUSER=botProps.getProperty("SUPERUSER");
            MESSAGE=botProps.getProperty("MESSAGE");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
