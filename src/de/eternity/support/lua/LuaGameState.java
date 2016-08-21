package de.eternity.support.lua;

import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import de.eternity.GameState;
import de.eternity.gfx.IRenderQueue;

public class LuaGameState extends GameState{

	private final LuaValue luaGameState;
	
	private String name;
	
	private final LuaFunction 
		startup,
		shutdown,
		pause,
		unpause,
		update,
		applyRenderContext;
	
	public LuaGameState(LuaValue luaGameState, String name) {
		
		this.name = name;
		
		this.luaGameState = luaGameState.call();
		
		doesRemainOnStack = this.luaGameState.get("does_remain_on_stack").checkboolean();
		
		startup = this.luaGameState.get("startup").checkfunction();
		shutdown = this.luaGameState.get("shutdown").checkfunction();

		pause = this.luaGameState.get("pause").checkfunction();
		unpause = this.luaGameState.get("unpause").checkfunction();

		update = this.luaGameState.get("update").checkfunction();
		applyRenderContext = this.luaGameState.get("apply_render_context").checkfunction();
	}
	
	public String getName(){
		return name;
	}
	
	@Override
	protected void startup() {
		startup.call();
	}

	@Override
	protected void shutdown() {
		shutdown.call();
	}

	@Override
	protected void pause() {
		pause.call();
	}

	@Override
	protected void unpause() {
		unpause.call();
	}

	@Override
	protected void update(double delta) {
		update.call(LuaValue.valueOf(delta));
	}

	@Override
	protected void applyRenderContext(IRenderQueue renderQueue) {
		applyRenderContext.call(CoerceJavaToLua.coerce(renderQueue));
	}

}
