local luaGameState = {}

local does_remain_on_stack = true

local function startup()
	print('startup lua')
end

local function shutdown()
	print('shutdown lua')
end

local function pause()
	print('pause lua')
end

local function unpause()
	print('unpause lua')
end

local function update(delta)
	poll_keyboard()
	if is_key_pressed(VK_A) then
		pop_game_state()
	end

end

local function apply_render_context(renderQueue)
end

luaGameState.does_remain_on_stack = does_remain_on_stack
luaGameState.startup = startup
luaGameState.shutdown = shutdown
luaGameState.pause = pause
luaGameState.unpause = unpause
luaGameState.update = update
luaGameState.apply_render_context = apply_render_context

return luaGameState