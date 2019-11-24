package at.ac.fhcampuswien.snake;

import java.util.ArrayList;
import java.util.Random;

class MessageFactory {
    private ArrayList<GameMessage> gameMessagesList = new ArrayList<>();

    void addGameMessage(String messageDescription, String messageType) {
        gameMessagesList.add(new GameMessage(messageDescription, messageType));
    }

    GameMessage getRandomGameMessageOfType(String messageType) {
        Random rand = new Random();
        return gameMessagesList.get(rand.nextInt(gameMessagesList.size()));
    }


}
