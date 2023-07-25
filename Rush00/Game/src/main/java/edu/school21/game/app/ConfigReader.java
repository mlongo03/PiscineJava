package edu.school21.game.app;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ConfigReader {

    private enum mode {
        PRODUCTION,
        DEVELOPMENT,
        OTHER
    }

    private mode gameMode;
    private String configFile;

    private Tile tiles[] = new Tile[5];

    ConfigReader(String gMode) {
        configFile = "application-" + gMode + ".properties";
        switch (gMode.toLowerCase()) {
            case "development":
                gameMode = mode.DEVELOPMENT;
                break;
            case "production":
                gameMode = mode.PRODUCTION;
                break;
            default:
                gameMode = mode.OTHER;
        }
        readConfigFile();
    }

    private void error(String message) {
        System.err.println(message);
        System.exit(-1);
    }

    public mode getGameMode() {
        return gameMode;
    }

    private void readConfigFile() {
        InputStreamReader inputStream = null;
        BufferedReader file = null;
        String line;

        for (int i = 0; i < tiles.length; ++i) {
            tiles[i] = new Tile();
        }
        inputStream = new InputStreamReader(
                Map.class.getClassLoader().getResourceAsStream(configFile));
        file = new BufferedReader(inputStream);
        try {
            while ((line = file.readLine()) != null)
                parseLine(line);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    private void retrieveCharProperty(String tile, String desc) {
        if (desc.length() > 1) {
            error("ConfigReader::retrieveCharProperty::the char property cannot be a String (found: " + desc + ")");
        }
        switch (tile.toLowerCase()) {
            case "enemy":
                tiles[Map.TileType.ENEMY.ordinal()].setCharString(desc);
                break;
            case "player":
                tiles[Map.TileType.PLAYER.ordinal()].setCharString(desc);
                break;
            case "wall":
                tiles[Map.TileType.OBSTACLE.ordinal()].setCharString(desc);
                break;
            case "goal":
                tiles[Map.TileType.TARGET.ordinal()].setCharString(desc);
                break;
            case "empty":
                tiles[Map.TileType.EMPTY.ordinal()].setCharString(desc);
                break;
            default:
                error("ConfigReader::retrieveCharProperty::type of game object unknown (found: " + tile + ")");
                break;
        }
    }

    private void retrieveColorProperty(String tile, String desc) {
        switch (tile.toLowerCase()) {
            case "enemy":
                tiles[Map.TileType.ENEMY.ordinal()].setColorFromString(desc);
                break;
            case "player":
                tiles[Map.TileType.PLAYER.ordinal()].setColorFromString(desc);
                break;
            case "wall":
                tiles[Map.TileType.OBSTACLE.ordinal()].setColorFromString(desc);
                break;
            case "goal":
                tiles[Map.TileType.TARGET.ordinal()].setColorFromString(desc);
                break;
            case "empty":
                tiles[Map.TileType.EMPTY.ordinal()].setColorFromString(desc);
                break;
            default:
                error("ConfigReader::retrieveColorProperty::type of game object unknown (found: " + tile + ")");
                break;
        }
    }

    private void parseLine(String line) {
        String splitted[] = line.split("=");
        String desc = " ";
        if (splitted.length == 2) {
            desc = splitted[1].trim();
        }
        if (splitted.length < 1 || splitted.length > 2) {
            error("ConfigReader::parseLine::line not valid, example: player.char = @ (found: " + line + ")");
        }
        String tileProperties[] = splitted[0].split("\\.");
        if (tileProperties.length != 2) {
            error("ConfigReader::parseLine::line not valid, example: player.char = @ (found: " + line + ")");
        }
        switch (tileProperties[1].trim().toLowerCase()) {
            case "char":
                retrieveCharProperty(tileProperties[0].trim(), desc);
                break;
            case "color":
                retrieveColorProperty(tileProperties[0], desc);
                break;
            default:
                error("ConfigReader::parseLine::property unknown (char, color) (found: " + tileProperties[1] + ")");
                break;
        }
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public boolean isProduction() {
        return (gameMode == mode.PRODUCTION);
    }

    public boolean isDevelopment() {
        return (gameMode == mode.DEVELOPMENT);
    }

}
