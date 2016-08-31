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
-
#TOML
See: https://github.com/toml-lang/toml for more information about TOML.
To be able to read TOML files in lua it is recomended to use a modified verision of lua-toml (available under the Happy License) which is itself available under the Happy License.
- original: https://github.com/jonstoler/lua-toml
- modified: 
-

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
