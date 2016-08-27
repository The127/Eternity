/**
 * Copyright (c) 2016 Julian Sven Baehr
 * 
 * See the file license.txt for copying permission.
 */
package de.eternity;

import java.util.Deque;
import java.util.LinkedList;

import de.eternity.gfx.RenderQueue;
import de.eternity.gfx.Renderer;

/**
 * This class represents a game.
 * Only one game can exist at a single point in time since some references are singletons.
 * This class handles the single game states and the game state stack as well as creating the rendering and updating thread.
 * These threads switch their context when both are done and therefore the game can run at best twice as fast as with a single thread.
 * All updating and pre-rendering is done in the update thread since this thread usually finishes its work on the current context before the render thread.
 * @author Julian Sven Baehr
 */
public class Game {
	
	//game time
	private static double relativeTime = 0;
	/**
	 * This method is to be used instead of any other time method since this method is faster.
	 * The return value of this method is the relative time in seconds since the start of the game as a double value.
	 * @return The time in seconds since the start of the game.
	 */
	public static double getRelativeTimeInSeconds(){
		return relativeTime;
	}

	private static int fps = 0;
	/**
	 * The fps is only updated once every second and is 0 in the first second since this value displays the fps in the previous second.
	 * @return The current fps of the game or 0 in the first second.
	 */
	public static int getFps(){
		return fps;
	}
	
	//class code
	private Deque<GameState> gameStates = new LinkedList<>();
	private GameState currentGameState = null;
	
	private Runnable updateScreen;
	private Renderer renderer;
	
	private boolean hasStarted = false;
	
	private GameData gameData;
	
	/**
	 * Creates a new game instance.
	 * @param renderer The renderer for the game.
	 * @param updateScreen This method declares how the rendered image is shown on the screen (in the Launcher class it is display::refreshScreen).
	 */
	public Game(GameData gameData, Renderer renderer, Runnable updateScreen){
		
		this.gameData = gameData;
		this.renderer = renderer;
		this.updateScreen = updateScreen;
	}
	
	/**
	 * A game data object holds nearly all information about a game including its settings and scripts.
	 * @return the game data instance of this game.
	 */
	public GameData getGameData(){
		return gameData;
	}
	
	/**
	 * Starts the game.
	 * A game can only be started once.
	 */
	public synchronized void start(){
		
		if(!hasStarted){
		
			hasStarted = true;
			
			new Thread(this::render, "renderThread").start();
			new Thread(this::update, "updateThread").start();
		}
	}
	
	/**
	 * Handles game rendering.
	 */
	protected void render(){
		
		while(true){
			
			renderer.switchRenderContext();
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
			
			currentGameState.update(relDelta);
			currentGameState.applyRenderContext(queue);
			queue.sort();
			
			//calculate fps
			fpsCounter++;
			if(fpsTimer >= 1){
				fps = fpsCounter;
				fpsCounter = 0;
				fpsTimer = 0;
			}
		}
	}
	
	/**
	 * Pushes a game state to the game state stack.
	 * @param gameState The new game state.
	 */
	public void pushGameState(GameState gameState){
		
		//handle previous game state if it exists
		if(currentGameState != null)
			if(currentGameState.doesRemainOnStack()){
				
				//pause the game state
				currentGameState.pause();
			}else{
				
				//remove the game state since it does not remain on the stack
				gameStates.removeLast();
			}
		
		//handle new game state
		gameStates.addLast(gameState);
		currentGameState = gameState;
		currentGameState.setGameData(gameData);
		currentGameState.startup();
	}
	
	/**
	 * Pops the current game state from the game state stack.
	 */
	public void popGameState(){
		
		//error handling !!!
		if(gameStates.size() > 0){
			
			//handle current game state
			currentGameState.shutdown();
			gameStates.removeLast();
			
			//if there are no more game states left exit the game
			if(gameStates.size() == 0){
				//everything is done now shut the game down
				System.exit(0);
			}else{
				
				//handle new game state
				currentGameState = gameStates.peekLast();
				currentGameState.unpause();
			}
		}
	}
}
