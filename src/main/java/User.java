public class User {
    private String name;
    private String chatId;

    public User(String name, String chatId) {
        this.name = name;
        this.chatId = chatId;
    }


    public String getChatId() {
        return chatId;
    }


}
