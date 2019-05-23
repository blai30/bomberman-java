## IDE
IntelliJ IDEA Ultimate 2019.1

## Java JDK Version
JDK 11.0.2

## Current working directory used for the game
bomberman/src/main/java

## Controls
### Player 1
| Command | Key |
|---------|-----|
| UP      | ↑   |
| DOWN    | ↓   |
| LEFT    | ←   |
| RIGHT   | →   |
| BOMB    | /   |

### Player 2
| Command | Key |
|---------|-----|
| UP      | W   |
| DOWN    | S   |
| LEFT    | A   |
| RIGHT   | D   |
| BOMB    | E   |

### Player 3
| Command | Key |
|---------|-----|
| UP      | T   |
| DOWN    | G   |
| LEFT    | F   |
| RIGHT   | H   |
| BOMB    | Y   |

### Player 4
| Command | Key |
|---------|-----|
| UP      | I   |
| DOWN    | K   |
| LEFT    | J   |
| RIGHT   | L   |
| BOMB    | O   |

### System
| Command       | Key |
|---------------|-----|
| EXIT          | ESC |
| RESET         | F5  |
| View Controls | F1  |

## To load .csv map file
```
java -jar csc413-secondgame-blai30.jar [map filename]
```

Example:
```
java -jar csc413-secondgame-blai30.jar maps/big_map.csv
```

Alternatively, place a .csv map file in the same directory as .jar and run:
```
java -jar csc413-secondgame-blai30.jar *.csv
```

## Creating your own map
Format is .csv (Comma-separated values). Width and height of the map should be at least 32x32 tiles (1024x1024 pixels). Recommended to surround map with hard wall tiles to prevent players from leaving the map.

### Possible tiles:
* Player1 initial spawn: `1`
* Player2 initial spawn: `2`
* Player3 initial spawn: `3`
* Player4 initial spawn: `4`
* Soft wall: `S`
* Hard wall: `H`
* Powerup Bomb: `PB`
* Powerup Firepower up: `PU`
* Powerup Firepower max: `PM`
* Powerup Speed: `PS`
* Powerup Pierce: `PP`
* Powerup Kick: `PK`
* Powerup Timer: `PT`

## Default stats
| Stat      | Value |
|-----------|-------|
| Bomb      | 1     |
| Firepower | 1     |
| Speed     | 1     |
| Pierce    | false |
| Kick      | false |
| Timer     | 250   |

## Available powerups
* Bomb: Increase maximum number of placed bombs by 1 (max: 6)
* Firepower up: Increase firepower by 1 (max: 6)
* Firepower max: Set firepower to max (max: 6)
* Speed: Increase movement speed by 0.5 (max: 4)
* Pierce: Enables piercing bomb explosions (explosions extend past soft walls)
* Kick: Enables kicking bombs
* Timer: Decrease bomb detonation timer by 15 (min: 160)

## Rules
* Bombs explode in 4 directions and the length of the explosion depends on the firepower of the original player who places it.
* Bombs are solid when players collide with it from outside.
* Soft walls have a 50% chance to drop a random powerup upon getting destroyed.
* Colliding with an explosion will kill the player.
* Players are able to be killed by explosions caused by their own bombs.

### Strategy
Destroy as many crates as you can to collect powerups and become stronger. Hopefully you get lucky and get a lot of powerups. Avoid players who have become very strong. Last player alive scores a point. This is a game of battle royale!

### Tips
* Look out for pierce bombs as they can explode through walls and potentially kill you when you least expect it.
* Kicking bombs make them travel at very high speed so be prepared to dodge the explosion when you see another player kicking a bomb in your direction.
* Powerups are destructible just like soft walls so if you end up coming across a powerup you don't need, consider using it as a shield against explosions that are not by pierce bombs.
* Kicking bombs through powerups will destroy the powerup, this means you can deny another player from collecting a powerup if you are too far from it but have the kick ability.
* Try baiting another player into a dead end and trapping them by placing a bomb to block them inside the dead end (will not work if they have kick).
