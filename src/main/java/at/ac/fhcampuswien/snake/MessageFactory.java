package at.ac.fhcampuswien.snake;

import java.util.ArrayList;
import java.util.Random;

class MessageFactory {
    private ArrayList<GameMessage> gameMessagesList = new ArrayList<>();
    private Random randomMessageOffset = new Random();

    void addGameMessage(String messageDescription, String messageType) {
        gameMessagesList.add(new GameMessage(messageDescription, messageType));
    }

    GameMessage getRandomGameMessageOfType(String messageType) {
        ArrayList<GameMessage> gameMessagesOfRequestedType = new ArrayList<>();

        for (GameMessage gameMessage : gameMessagesList) {
            if (gameMessage.getMessageType().equals(messageType)) {
                gameMessagesOfRequestedType.add(gameMessage);
            }
        }
        return gameMessagesOfRequestedType.get(this.randomMessageOffset.nextInt(gameMessagesOfRequestedType.size()));
    }


}
