# GameTest

Experimenting with [AlmasB](https://github.com/AlmasB) JavaFX library [FXGL](https://github.com/AlmasB/FXGL)

## Getting Started

I'm using IntelliJ on a Mac and I started with the [FXGL Wiki](https://github.com/AlmasB/FXGL/wiki/Introduction) Getting Started Tutorial. 

### Prerequisites

* Java 8
* Maven
* FXGL - [Get FXGL] (https://github.com/AlmasB/FXGL/wiki/Get-FXGL-%28Maven%2C-Gradle%2C-Uber%29)


## Content generation from these sources

* [LPC Sprite Character Generator](http://gaurav.munjal.us/Universal-LPC-Spritesheet-Character-Generator/) - For sprites
* [WolframTones](http://tones.wolfram.com/generate/G10BkPZMEQ97GUwl7OPdckUz5774SqvpLY7y6jfMqB) - For procedurally generated music
* [BFXR](https://www.bfxr.net/) - Used to generate sound effects


## Authors
* **AlmasB** - *FXGL and initial tutorial* - [AlmasB](https://github.com/AlmasB)
* **Eric Holsinger** - *Additional work* - [Eric Holsinger](https://github.com/ericholsinger)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.


## Acknowledgments

* [AlmasB](https://github.com/AlmasB) for FXGL


# ChangeLog
0.1
* displaying the x,y coordinates of the player object
* bounding the player object within the game screen
* playing a bumping sound at the edge of the screen
* background ambient music
* sprite sheet slicing for walking and idle animations
* animated the walk/idle sequence in all four cardinal coordinates (North, East, South, West)
* minimal "moonwalking", by adjusting the animation duration
* second "npc" character
* detect simple bounding box collision of entities
* npc turns toward player on collision

0.2
* refactored to FXGL 0.5.0
* refactored movement
* player cannot walk over NPCs bounding box
