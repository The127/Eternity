package de.eternity;

import java.util.ArrayList;
import java.util.List;

import de.eternity.gfx.RenderQueue;
import de.eternity.gfx.Renderer;

public class Game {

	//game time
	private static double relativeTime = 0;
	public static double getRelativeTimeInSeconds(){
		return relativeTime;
	}
	public double getTime(){
		return relativeTime;
	}

	private static int fps = 0;
	public static int getFps(){
		return fps;
	}
	
	//class code
	private List<IGameState> gameStates = new ArrayList<>();
	private int currentGameState = -1;
	
	private Runnable updateScreen;
	private Renderer renderer;
	
	/**
	 * Creates a new game instance.
	 * @param renderer The renderer for the game.
	 * @param updateScreen This method declares how the rendered image is shown on the screen.
	 */
	public Game(Renderer renderer, Runnable updateScreen){
		this.renderer = renderer;
		this.updateScreen = updateScreen;
	}
	
	/**
	 * Starts the game.
	 */
	public void start(){
		
		new Thread(this::render, "renderThread").start();
		new Thread(this::update, "updateThread").start();
	}
	
	/**
	 * Handles game rendering.
	 */
	protected void render(){
		
		while(true){
			
			renderer.switchContext();
			renderer.clearScreen();
			renderer.renderQueue();
			
			updateScreen.run();
		}
	}
	
	/**
	 * Handles the updating of the game except for user input.
	 */
	protected void update(){
		
		int fpsCounter = 0;
		long lastTime = System.nanoTime();
		long currentTime = 0, absDelta = 0;
		double relDelta = 0, fpsTimer = 0;
		
		while(true){
			
			//await new context
			RenderQueue queue = renderer.getUpdateContext();
			
			//timer handling
			currentTime = System.nanoTime();
			absDelta = currentTime - lastTime;
			lastTime = currentTime;
			
			relDelta = absDelta / 1000000000d;
			//update game time and fps count
			relativeTime += relDelta;
			fpsTimer += relDelta;
			
			gameStates.get(currentGameState).update(relDelta);
			gameStates.get(currentGameState).applyRenderContext(queue);
			queue.sort();
			
			//calculate fps
			fpsCounter++;
			if(fpsTimer >= 1){
				fps = fpsCounter;
				fpsCounter = 0;
				fpsTimer = 0;
				
				System.out.println("FPS: " + fps);
			}
		}
	}
	
	/**
	 * Switches to the desired game state.
	 * @param gameState The id of the game state.
	 */
	public void setCurrentGameState(int gameState){
		
		if(currentGameState < 0 || currentGameState > gameStates.size())
			throw new IllegalArgumentException("'gameState' cannot be less than 0 or greater than the amount of registered game states!");
		else
			gameStates.get(currentGameState).shutdown();
		
		currentGameState = gameState;
		gameStates.get(currentGameState).startup();
	}
	
	/**
	 * Adds a game state to the game.
	 * @param gameState The new game state.
	 * @return The id of the game state.
	 */
	public int addGameState(IGameState gameState){
		
		gameStates.add(gameState);
		return gameStates.size()-1;
	}
}
