package at.ac.fhcampuswien.snake;

class GameMessage {
    private String messageDescription;
    private String messageType;

    GameMessage(String messageDescription, String messageType) {
        this.messageDescription = messageDescription;
        this.messageType = messageType;
    }

    public String getMessageType() {
        return messageType;
    }

    public String getMessageDescription() {
        return messageDescription;
    }

    @Override
    public String toString() {
        return messageDescription;
    }
}
