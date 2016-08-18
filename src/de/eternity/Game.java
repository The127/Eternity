package de.eternity;

import java.util.Deque;
import java.util.LinkedList;

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
	private Deque<GameState> gameStates = new LinkedList<>();
	private GameState currentGameState = null;
	
	private Runnable updateScreen;
	private Renderer renderer;
	
	private boolean hasStarted = false;
	
	private GameData gameData;
	
	/**
	 * Creates a new game instance.
	 * @param renderer The renderer for the game.
	 * @param updateScreen This method declares how the rendered image is shown on the screen.
	 */
	public Game(GameData gameData, Renderer renderer, Runnable updateScreen){
		
		this.gameData = gameData;
		this.renderer = renderer;
		this.updateScreen = updateScreen;
	}
	
	/**
	 * Starts the game.
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
				
				System.out.println("FPS: " + fps);
			}
		}
	}
	
	/**
	 * Pushes a game state to the gamestate stack.
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
