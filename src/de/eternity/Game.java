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
	private static void addDelta(double delta){
		relativeTime += delta;
	}
	public double getTime(){
		return relativeTime;
	}

	//class code
	private List<IGameState> gameStates = new ArrayList<>();
	private int currentGameState = -1;
	
	private Runnable updateScreen;
	private Renderer renderer;
	
	private Thread renderThread, updateThread;
	
	//TODO: interface for renderer to be less restricted
	/**
	 * 
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
		
		renderThread = new Thread(() -> {
			
			//TODO: calculate fps? (fps are always equal to ups in this setup)
			while(true){
				
				//TODO: maybe use an interface with a doRendering method for more flexibility
				renderer.clearScreen();
				renderer.switchContext();
				renderer.renderQueue();
				
				updateScreen.run();
			}
		});
		
		updateThread = new Thread(() -> {
			
			long lastTime = System.nanoTime();
			long currentTime = 0, absDelta = 0;
			double relDelta = 0;
			
			while(true){
				
				try {
					//await new context
					RenderQueue queue = renderer.getUpdateContext();
					
					//timer handling
					currentTime = System.nanoTime();
					absDelta = currentTime - lastTime;
					lastTime = currentTime;
					
					relDelta = absDelta / 1000000000d;
					addDelta(relDelta);
					
					//TODO: handle input data in some way
					
					gameStates.get(currentGameState).update(relDelta);
					gameStates.get(currentGameState).applyRenderContext(queue);
					
				} catch (Exception e) {
					//TODO: maybe not use Exception in catch clause
					
					//TODO: use a logging framework of some sort
					e.printStackTrace();
					System.exit(-1);
				}
			}
		});
		
		updateThread.start();
		renderThread.start();
	}
	
	public void setCurrentGameState(int gameState){
		
		if(currentGameState != -1)
			gameStates.get(currentGameState).shutdown();
		
		currentGameState = gameState;
		gameStates.get(currentGameState).startup();
	}
	
	public int addGameState(IGameState gameState){
		
		gameStates.add(gameState);
		return gameStates.size()-1;
	}
}
