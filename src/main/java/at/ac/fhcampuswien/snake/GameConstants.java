package at.ac.fhcampuswien.snake;

class GameConstants {

	// Message Types
	static final String WALL_DEATH = "wallDeath";

	// Technical settings
	static final long FRAMEDELAY = 25000000;
	static final long FRAMEDELAY_MAX = 8000000;
	static final long DELAY_DECREASE = 600000;
	
	// Stage settings
	static final int STAGE_WIDTH = 1500;
	static final int STAGE_HEIGHT = 700;
	static final int STAGE_MIN_WIDTH = 50;
	static final int STAGE_MIN_HEIGHT = 50;
	
	// Game settings
	static final int INIT_SCORE = 0;
	static final int SPEED = 21;
	static final int FOOD_BORDER_OFFSET = 50;
	static final int SNAKE_WIDTH = 20;
	static final int SNAKE_HEIGHT = 20;
	static final int FOOD_WIDTH = 20;
	static final int FOOD_HEIGHT = 20;
	static final String TAIL_DEATH = "tailDeath";
	//Display Messages
	static final String MESSAGE_SCORE = "Score: ";

	private GameConstants() {
	}
}
