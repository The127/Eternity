# Eternity
Mutlithreaded Java 2D Game Engine.
Available under the MIT License.

Eternity is a java soft 2d game engine and requires Java 8 or above.
It is written in pure java and is tested under windows 7 and linux(fedora 24).

It uses the luaj library which is available under the MIT License to allow for a fully scriptable game. In fact it is the preffered way to create a game.
- http://www.luaj.org/luaj/3.0/README.html#1

The engine also uses toml4j and gson which are available under the MIT License and the Apache License Version 2.0 respectively.
- https://github.com/mwanji/toml4j
- https://github.com/google/gson

#TOML
See: https://github.com/toml-lang/toml for more information about TOML.
To be able to read TOML files in lua it is recomended to use a modified verision of lua-toml (available under the Happy License) which is itself available under the Happy License.
- original: https://github.com/jonstoler/lua-toml
- modified: https://github.com/The127/eternity-toml-lua

#Features
Engine
- fully java implementation
- 2 step multithreaded renering pipeline
- highly optimised 2d graphics rendering code (no hardware rendering)
- Tiled map editor json export support
- standard launcher implementation (with swing and awt components)
- readable TOML file format for settings and config files

Graphics
- highly optimised 2D rendering algorithms
- portable rendering engine (not tied to swing or awt)
- lightweight text rendering implementation

Map and tiles
- automatic tileset slicing
- automatic map loading
- local tile id conversion

Scripting
- lua scripting
- fully scriptable
- engine methods exposed to lua
- automatic script loading

Audio
- positional audio effect

-
#Scripting
The following lua functions are available.

#Input Methods

poll_input() : void
- Must be called in every update cycle exactly once at the beginning if keyboard and/or mouse input is needed.

get_mouse_position() : int, int
- Returns first the x and second the y position of the mouse.

is_mouse_pressed(int buttonCode) : boolean
- Returns true if the button is pressed, false otherwise.
- All button codes are available as a variable BUTTON(number(1-3))

is_key_pressed(int keyCode) : boolean
- Returns true if the button is pressed, false otherwise.
- All key codes are available as a variable VK_keyname

#Game State Methods

push_game_state(string gameStateName) : void
- This method pushes another game state on the stack.
- The parameter gameStateName is the name of the lua file for the game state.

pop_game_state() : void
- This method pops the current game state off the stack.

#Utility Methods

get_game_data() : GameData
- This method returns the game data object of the game.
- This object is sometimes needed as a parameter.
- This object provides access to the game data.

get_fps() : int
- This method returns the current fps of the game.
- The fps is only updated each second and therefore is 0 in the first second.

#Map Methods

get_game_map(string name) : GameMap
- This method returns the game map the the given name.

#Graphics Methods

new_text_line(string text, int color): Text
- Returns a new drawable text.

new_text_area(int chars_horizontal, int chars_vertical, int color): Text
- Returns a new drawable text with the specified area.
- This Text object does not yet contain any data.

set_window_title(string title) : void
- This method sets the title of the game window.

argb_to_color(int a, int r, int g, int b): int
- This method returns a single integer from the single argb color parts.

rgb_to_color( int r, int g, int b): int
- This method returns a single integer from the single rgb color parts (full alpha).

load_animation(string animation) : Animation
- This method loads a local animation file and returns a translated animation object.

udpate_tile_animations(double delta) : void
- This method should be called once every update cycle.
- This method updates the tile animations (as the name suggests).

get_textute_storage() : void
- This method returns the global texture storage.

to_global_texture_id(string tileset, int localId) : int
- This method returns the global texture id of the local texture id of the tileset.

#Sound Methods

load_sound(string sound) : Sound
- This method loads a sound file into a sound instance.
- Be aware that sound objects are big and leave a lot of garbage for the gc.
- See Sound class documentation for more methods and information.
