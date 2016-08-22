local luaGameState = {}

local does_remain_on_stack = true

local map
local tex
local sound

local x, y, speed

speed = 10

local function startup()
	set_window_title('lua changed this title')
	map = get_game_map('test')
	tex = get_texture_storage():getTexture(to_global_texture_id("walls", 0))
	sound = load_sound("/sounds/t.wav")
	sound:loop()
end

local function shutdown()
	print('shutdown lua')
	sound:stop()
end

local function pause()
	print('pause lua')
	sound:pause()
end

local function unpause()
	print('unpause lua')
	sound:unpause()
end

local function update(delta)

	poll_keyboard()
	
	x = 0
	y = 0
	
	if is_key_pressed(VK_A) then
		x = -100 * delta
	end
	if is_key_pressed(VK_D) then
		x = 100 * delta
	end
	if is_key_pressed(VK_W) then
		y = -100 * delta
	end
	if is_key_pressed(VK_S) then
		y = 100 * delta
	end
	
	update_tile_animations(delta)
end

local function apply_render_context(renderQueue)

	renderQueue:getCamera():move(x, y)

	map:renderMap(renderQueue, get_game_data())
	renderQueue:addEntity(tex, 100, 100)
end

luaGameState.does_remain_on_stack = does_remain_on_stack
luaGameState.startup = startup
luaGameState.shutdown = shutdown
luaGameState.pause = pause
luaGameState.unpause = unpause
luaGameState.update = update
luaGameState.apply_render_context = apply_render_context

return luaGameState