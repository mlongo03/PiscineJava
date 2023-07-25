package edu.school21.game.app;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(separators = "=")
public class ArgumentReader {
    @Parameter(names = "--enemiesCount", validateWith = PositiveInteger.class, description = "number of enemies", required = true)
    private Integer enemiesCount;

    @Parameter(names = "--wallsCount", validateWith = PositiveInteger.class, description = "number of walls", required = true)
    private Integer wallsCount;

    @Parameter(names = "--size", validateWith = PositiveInteger.class, description = "size of the map", required = true)
    private Integer mapSize;

    @Parameter(names = "--profile", description = "profile", required = true)
    private String profile;

    public int getEnemiesCount() {
        return enemiesCount;
    }

    public int getWallsCount() {
        return wallsCount;
    }

    public int getSize() {
        return mapSize;
    }

    public String getProfile() {
        return profile;
    }

    public void checkSpace() throws IllegalParametersException {
        if (enemiesCount + wallsCount + 2 > mapSize * mapSize)
            throw new IllegalParametersException("There is not enough space with such parameters.");
    }
}
