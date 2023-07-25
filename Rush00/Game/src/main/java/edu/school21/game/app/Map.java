package edu.school21.game.app;

import java.util.Scanner;

import com.diogonunes.jcolor.Ansi;
import com.diogonunes.jcolor.Attribute;
import edu.school21.chaselogic.ChaseLogic;

public class Map {
    public enum TileType {
        EMPTY,
        PLAYER,
        ENEMY,
        OBSTACLE,
        TARGET
    }

    private TileType[] tileMap;
    private int mapSize;
    private int availableSpace;
    private int playerPos;
    private int targetPos;
    private ConfigReader config;
    private Enemy[] enemies;
    private ChaseLogic cl;

    Map(int size, int enemy, int walls, String gMode) {
        this.mapSize = size;
        this.availableSpace = size * size;
        this.config = new ConfigReader(gMode);
        initMap(enemy, walls);
    }

    private void initMap(int enemy, int walls) {
        boolean isValid = false;
        while (!isValid) {
            this.tileMap = new TileType[availableSpace];
            for (int i = 0; i < (availableSpace); ++i)
                this.tileMap[i] = TileType.EMPTY;
            this.playerPos = getNextTile(false);
            this.tileMap[this.playerPos] = TileType.PLAYER;
            this.targetPos = getNextTile(true);
            this.tileMap[this.targetPos] = TileType.TARGET;
            enemies = new Enemy[enemy];
            for (int i = 0; i < enemy; ++i) {
                int tempTile = getNextTile(true);
                enemies[i] = new Enemy(tempTile % mapSize, tempTile / mapSize);
                this.tileMap[tempTile] = TileType.ENEMY;
            }
            for (int i = 0; i < walls; ++i) {
                this.tileMap[getNextTile(true)] = TileType.OBSTACLE;
            }
            cl = new ChaseLogic(tileMapToInt(), targetPos % mapSize, targetPos / mapSize);
            cl.calcPlayerMap(playerPos % mapSize, playerPos / mapSize);
            isValid = cl.checkPlayerPos(playerPos % mapSize, playerPos / mapSize);
        }
    }

    private int getNextTile(boolean checkIfFree) {
        int rnd = (int) (Math.random() * availableSpace);
        if (checkIfFree) {
            while (this.tileMap[rnd] != TileType.EMPTY) {
                rnd = (int) (Math.random() * availableSpace);
            }
        }
        return (rnd);
    }

    private void printTile(Map.TileType t) {
        Tile toPrint = config.getTiles()[t.ordinal()];
        System.out.print(Ansi.colorize(toPrint.getCharString(), toPrint.getColor()));
    }

    public void print(String message) {
        if (config.isProduction()) {
            System.out.println("\033[H\033[2J");
        }
        System.out.println(message);
        for (int i = 0; i < this.mapSize; i++) {
            for (int j = 0; j < this.mapSize; j++) {
                printTile(tileMap[i * mapSize + j]);
            }
            System.out.println("");
        }
    }

    private TileType getTile(int i, int j) {
        return tileMap[i * mapSize + j];
    }

    private boolean checkIfTileExist(int i, int j) {
        if (i >= this.mapSize || j >= this.mapSize)
            return false;
        if (i < 0 || j < 0)
            return (false);
        return true;
    }

    private void win() {
        if (config.isProduction()) {
            System.out.println("\033[H\033[2J");
        }
        System.out.println(Ansi.colorize(".-:.     ::-.   ...      ...    :::    .::    .   .:::::::::.    :::.",
                Attribute.TEXT_COLOR(255, 255, 0)));
        System.out.println(Ansi.colorize(" ';;.   ;;;;'.;;;;;;;.   ;;     ;;;    ';;,  ;;  ;;;' ;;;`;;;;,  `;;;",
                Attribute.TEXT_COLOR(255, 204, 0)));
        System.out.println(Ansi.colorize("   '[[,[[[' ,[[     \\[[,[['     [[[     '[[, [[, [['  [[[  [[[[[. '[[",
                Attribute.TEXT_COLOR(255, 153, 0)));
        System.out.println(Ansi.colorize("     c$$\"   $$$,     $$$$$      $$$       Y$c$$$c$P   $$$  $$$ \"Y$c$$",
                Attribute.TEXT_COLOR(255, 102, 0)));
        System.out.println(Ansi.colorize("   ,8P\"`    \"888,_ _,88P88    .d888        \"88\"888    888  888    Y88",
                Attribute.TEXT_COLOR(255, 51, 0)));
        System.out
                .println(Ansi.colorize("  mM\"         \"YMMMMMP\"  \"YmmMMMM\"\"         \"M \"M\"    MMM  MMM     YM",
                        Attribute.TEXT_COLOR(255, 0, 0)));
        System.exit(0);

    }

    public void gameOver() {
        if (config.isProduction()) {
            System.out.println("\033[H\033[2J");
        }
        System.out.println(
                Ansi.colorize(".-:.     ::-.   ...      ...    :::     :::         ...      .::::::. .,::::::  ",
                        Attribute.TEXT_COLOR(155, 0, 0)));
        System.out.println(
                Ansi.colorize(" ';;.   ;;;;'.;;;;;;;.   ;;     ;;;     ;;;      .;;;;;;;.  ;;;`    ` ;;;;''''  ",
                        Attribute.TEXT_COLOR(175, 0, 0)));
        System.out.println(
                Ansi.colorize("   '[[,[[[' ,[[     \\[[,[['     [[[     [[[     ,[[     \\[[,'[==/[[[[, [[cccc   ",
                        Attribute.TEXT_COLOR(195, 0, 0)));
        System.out.println(
                Ansi.colorize("     c$$\"   $$$,     $$$$$      $$$     $$'     $$$,     $$$  '''    $ $$\"\"\"\"   ",
                        Attribute.TEXT_COLOR(215, 0, 0)));
        System.out.println(
                Ansi.colorize("   ,8P\"`    \"888,_ _,88P88    .d888    o88oo,.__\"888,_ _,88P 88b    dP 888oo,__ ",
                        Attribute.TEXT_COLOR(235, 0, 0)));
        System.out.println(Ansi.colorize(
                "  mM\"         \"YMMMMMP\"  \"YmmMMMM\"\"    \"\"\"\"YUMMM  \"YMMMMMP\"   \"YMmMY\"  \"\"\"\"YUMMM",
                Attribute.TEXT_COLOR(255, 0, 0)));
        System.exit(0);
    }

    private int[][] tileMapToInt() {
        int ret[][] = new int[mapSize][mapSize];
        for (int i = 0; i < tileMap.length; ++i) {
            ret[i / mapSize][i % mapSize] = tileMap[i].ordinal();
        }
        return (ret);
    }

    private void replacePlayerPos(int newPos) {
        tileMap[playerPos] = TileType.EMPTY;
        playerPos = newPos;
        if (tileMap[playerPos] == TileType.TARGET)
            win();
        tileMap[playerPos] = TileType.PLAYER;
    }

    private void moveEnemiesDevelop() {
        Scanner input = new Scanner(System.in);
        String inputString;
        String message;
        String stdMessage;
        String state;
        boolean canMove;
        for (int i = 0; i < enemies.length; ++i) {
            stdMessage = "Move enemy in position [" + enemies[i].getXPos() + ", " + enemies[i].getYPos() + "]";
            message = "";
            canMove = false;
            state = "";
            while (!canMove) {
                print(message + stdMessage);
                System.out.print("-> ");
                inputString = input.nextLine();
                switch (inputString) {
                    case "s":
                        if (checkEnemy(enemies[i], 0, 1)) {
                            state = inputString;
                            message = "";
                            stdMessage = "Moving enemy down, confirm pressing 8, or select another direction.";

                        } else {
                            message = "Cannot move down! ";
                            state = "";
                        }
                        break;
                    case "w":
                        if (checkEnemy(enemies[i], 0, -1)) {
                            state = inputString;
                            message = "";
                            stdMessage = "Moving enemy up, confirm pressing 8, or select another direction.";
                        } else {
                            message = "Cannot move up! ";
                            state = "";
                        }
                        break;
                    case "a":
                        if (checkEnemy(enemies[i], -1, 0)) {
                            state = inputString;
                            message = "";
                            stdMessage = "Moving enemy left, confirm pressing 8, or select another direction.";
                        } else {
                            message = "Cannot move left! ";
                            state = "";
                        }
                        break;
                    case "d":
                        if (checkEnemy(enemies[i], 1, 0)) {
                            state = inputString;
                            message = "";
                            stdMessage = "Moving enemy right, confirm pressing 8, or select another direction.";

                        } else {
                            message = "Cannot move right! ";
                            state = "";
                        }
                        break;
                    case "8":
                        if (state == "") {
                            message = "Invalid input: " + inputString + " ";
                        } else {
                            confirmMoviment(enemies[i], state);
                            canMove = true;
                        }
                    case "9":
                        canMove = true;
                    default:
                        message = "Invalid input: " + inputString + " ";
                        break;
                }
            }
        }
    }

    private void moveEnemiesProduction() {
        for (int i = 0; i < enemies.length; ++i) {
            cl.setMap(tileMapToInt());
            int newPos = cl.moveEnemy(enemies[i].getXPos(), enemies[i].getYPos(), playerPos);
            moveEnemyPos(enemies[i], newPos);
        }
    }

    private void moveEnemies() {
        if (config.isDevelopment()) {
            moveEnemiesDevelop();
        }
        if (config.isProduction()) {
            moveEnemiesProduction();
        }
    }

    private boolean checkEnemy(Enemy e, int x, int y) {
        int xPos = e.getXPos();
        int yPos = e.getYPos();
        if (!checkIfTileExist(xPos + x, yPos + y))
            return (false);
        TileType temp = getTile(yPos + y, xPos + x);
        if (temp != TileType.EMPTY && temp != TileType.PLAYER)
            return (false);
        return (true);
    }

    private void confirmMoviment(Enemy e, String direction) {
        switch (direction) {
            case "s":
                moveEnemy(e, 0, 1);
                break;
            case "w":
                moveEnemy(e, 0, -1);
                break;
            case "a":
                moveEnemy(e, -1, 0);
                break;
            case "d":
                moveEnemy(e, 1, 0);
                break;
        }
    }

    private void moveEnemy(Enemy e, int x, int y) {
        int enemyPos = e.getYPos() * mapSize + e.getXPos();
        tileMap[enemyPos] = TileType.EMPTY;
        e.setXPos(e.getXPos() + x);
        e.setYPos(e.getYPos() + y);
        int enemyNewPos = e.getYPos() * mapSize + e.getXPos();
        if (tileMap[enemyNewPos] == TileType.PLAYER)
            gameOver();
        tileMap[enemyNewPos] = TileType.ENEMY;
    }

    private void moveEnemyPos(Enemy e, int newPos) {
        int enemyPos = e.getYPos() * mapSize + e.getXPos();
        tileMap[enemyPos] = TileType.EMPTY;
        e.setXPos(newPos % mapSize);
        e.setYPos(newPos / mapSize);
        if (tileMap[newPos] == TileType.PLAYER)
            gameOver();
        tileMap[newPos] = TileType.ENEMY;
    }

    public String moveDownward() {
        String ret = "";
        int xPos = playerPos % mapSize;
        int yPos = playerPos / mapSize;
        if (!checkIfTileExist(xPos, yPos + 1))
            return ("Cannot move downward!");
        TileType temp = getTile(yPos + 1, xPos);
        if (temp != TileType.EMPTY && temp != TileType.TARGET)
            return ("Cannot move downward!");
        replacePlayerPos((yPos + 1) * mapSize + xPos);
        moveEnemies();
        return (ret);
    }

    public String moveForward() {
        String ret = "";
        int xPos = playerPos % mapSize;
        int yPos = playerPos / mapSize;
        if (!checkIfTileExist(xPos, yPos - 1))
            return ("Cannot move forward!");
        TileType temp = getTile(yPos - 1, xPos);
        if (temp != TileType.EMPTY && temp != TileType.TARGET)
            return ("Cannot move forward!");
        replacePlayerPos((yPos - 1) * mapSize + xPos);
        moveEnemies();
        return (ret);
    }

    public String moveLeft() {
        String ret = "";
        int xPos = playerPos % mapSize;
        int yPos = playerPos / mapSize;
        if (!checkIfTileExist(xPos - 1, yPos))
            return ("Cannot move left!");
        TileType temp = getTile(yPos, xPos - 1);
        if (temp != TileType.EMPTY && temp != TileType.TARGET)
            return ("Cannot move left!");
        replacePlayerPos(yPos * mapSize + xPos - 1);
        moveEnemies();
        return (ret);
    }

    public String moveRight() {
        String ret = "";
        int xPos = playerPos % mapSize;
        int yPos = playerPos / mapSize;
        if (!checkIfTileExist(xPos + 1, yPos))
            return ("Cannot move right!");
        TileType temp = getTile(yPos, xPos + 1);
        if (temp != TileType.EMPTY && temp != TileType.TARGET)
            return ("Cannot move right!");
        replacePlayerPos(yPos * mapSize + xPos + 1);
        moveEnemies();
        return (ret);
    }
}
