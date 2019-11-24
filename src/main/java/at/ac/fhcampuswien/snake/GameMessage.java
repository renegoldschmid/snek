package at.ac.fhcampuswien.snake;

class GameMessage {
    private String messageDescription;
    private String messageType;

    GameMessage(String messageDescription, String messageType) {
        this.messageDescription = messageDescription;
        this.messageType = messageType;
    }

    String getMessageType() {
        return messageType;
    }

    @Override
    public String toString() {
        return messageDescription;
    }
}
