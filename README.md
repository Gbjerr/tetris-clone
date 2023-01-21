# tetris-clone

A tetris clone developed in JavaFX.<br><br>
<b>Controls:</b><br>
The Start button is used to initialize the game, but also used to start over when the player has lost.
<ul>
    <li>UP key:    rotates figure</li>
    <li>DOWN key:  moves piece faster down</li>
    <li>LEFT key:  moves piece to the left</li>
    <li>RIGHT key: moves piece to the right</li>
</ul>

## Usage
Requires java 16. <br>
Run with maven: <b>mvn clean javafx:run</b>

## Screenshot
<img width="355" alt="tetris" src="https://user-images.githubusercontent.com/46920882/213861284-a3d0e696-a158-4aad-91f2-8c3c90c38a77.png">

## Structure
```bash

src
└── main
    ├── java
    │   ├── controller
    │   │   └── GameController.java
    │   ├── model
    │   │   ├── GameField.java
    │   │   ├── Point.java
    │   │   └── Tetromino.java
    │   ├── module-info.java
    │   ├── startup
    │   │   └── Main.java
    │   └── view
    │       ├── GameDisplay.java
    │       ├── ShapeDisplay.java
    │       └── View.java
    └── resources
        └── fonts
            └── Retro_Gaming.ttf
```