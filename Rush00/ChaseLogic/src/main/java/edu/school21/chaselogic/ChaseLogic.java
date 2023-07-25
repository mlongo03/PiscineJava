package edu.school21.chaselogic;

import java.util.AbstractQueue;

public class ChaseLogic {

    int mapSize;
    int map[][];
    int targetCalc[][];
    int playerCalc[][];

    public ChaseLogic(int[][] map, int targetX, int targetY) {
        this.map = map;
        mapSize = map.length;
        calcTargetCalc(targetX, targetY);
    }

    private boolean exists(int y, int x) {
        if (y < 0 || x < 0) {
            return false;
        }
        if (y >= mapSize || x >= mapSize) {
            return false;
        }
        return true;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    private void markTarget(int y, int x, int distance) {
        if (exists(y, x)) {
            if (targetCalc[y][x] == 0) {
                if (map[y][x] == 3) {
                    targetCalc[y][x] = -1;
                } else {
                    targetCalc[y][x] = distance + 1;
                    markAroundTarget(y, x);
                }
            }
            if (targetCalc[y][x] > 0) {
                if (targetCalc[y][x] > distance + 1) {
                    targetCalc[y][x] = distance + 1;
                    markAroundTarget(y, x);
                }
            }
        }
    }

    private void markPlayer(int y, int x, int distance) {
        if (exists(y, x)) {
            if (playerCalc[y][x] == 0) {
                if (map[y][x] == 3) {
                    playerCalc[y][x] = -1;
                } else {
                    playerCalc[y][x] = distance + 1;
                    markAroundPlayer(y, x);
                }
            }
            if (playerCalc[y][x] > 0) {
                if (playerCalc[y][x] > distance + 1) {
                    playerCalc[y][x] = distance + 1;
                    markAroundPlayer(y, x);
                }
            }
        }
    }

    private void markAroundTarget(int y, int x) {
        int actualDistance = targetCalc[y][x];
        if (actualDistance == -2) {
            actualDistance = 0;
        }
        markTarget(y + 1, x, actualDistance);
        markTarget(y - 1, x, actualDistance);
        markTarget(y, x + 1, actualDistance);
        markTarget(y, x - 1, actualDistance);
    }

    private void markAroundPlayer(int y, int x) {
        int actualDistance = playerCalc[y][x];
        if (actualDistance == -2) {
            actualDistance = 0;
        }
        markPlayer(y + 1, x, actualDistance);
        markPlayer(y - 1, x, actualDistance);
        markPlayer(y, x + 1, actualDistance);
        markPlayer(y, x - 1, actualDistance);
    }

    private void calcTargetCalc(int x, int y) {
        targetCalc = new int[map.length][map.length];
        targetCalc[y][x] = -2;
        markAroundTarget(y, x);
    }

    public void calcPlayerMap(int x, int y) {
        playerCalc = new int[map.length][map.length];
        playerCalc[y][x] = -2;
        markAroundPlayer(y, x);
    }

    private boolean checkDirectionToTarget(int x, int y, int distance) {
        if (exists(y, x)) {
            if (targetCalc[y][x] <= 0)
                return (false);
            if (!isFree(x, y))
                return (false);
            if (targetCalc[y][x] >= distance)
                return (false);
            return (true);
        }
        return (false);
    }

    private int moveTowardTarget(int x, int y) {
        int actualDistance = targetCalc[y][x];
        if (checkDirectionToTarget(x, y - 1, actualDistance)) {
            return ((y - 1) * mapSize + x);
        }
        if (checkDirectionToTarget(x, y + 1, actualDistance)) {
            return ((y + 1) * mapSize + x);
        }
        if (checkDirectionToTarget(x - 1, y, actualDistance)) {
            return (y * mapSize + x - 1);
        }
        if (checkDirectionToTarget(x + 1, y, actualDistance)) {
            return (y * mapSize + x + 1);
        }
        return (y * mapSize + x);
    }

    private boolean isFree(int x, int y) {
        if (map[y][x] == 0 || map[y][x] == 1) {
            return (true);
        }
        return (false);
    }

    private boolean checkDirectionToPlayer(int x, int y, int distance) {
        if (exists(y, x)) {
            if (playerCalc[y][x] == -2)
                return (true);
            if (playerCalc[y][x] <= 0)
                return (false);
            if (!isFree(x, y))
                return (false);
            if (playerCalc[y][x] >= distance)
                return (false);
            return (true);
        }
        return (false);
    }

    private int moveTowardPlayer(int x, int y) {
        int actualDistance = playerCalc[y][x];
        if (checkDirectionToPlayer(x, y - 1, actualDistance)) {
            return ((y - 1) * mapSize + x);
        }
        if (checkDirectionToPlayer(x, y + 1, actualDistance)) {
            return ((y + 1) * mapSize + x);
        }
        if (checkDirectionToPlayer(x - 1, y, actualDistance)) {
            return (y * mapSize + x - 1);
        }
        if (checkDirectionToPlayer(x + 1, y, actualDistance)) {
            return (y * mapSize + x + 1);
        }
        return (y * mapSize + x);
    }

    public int moveEnemy(int x, int y, int p) {
        int newPosition = y * mapSize + x;
        int playerX = p % mapSize;
        int playerY = p / mapSize;
        calcPlayerMap(playerX, playerY);
        if (targetCalc[y][x] <= 0) {
            return (newPosition);
        }
        if (playerCalc[y][x] <= 0) {
            return (newPosition);
        }
        if (playerCalc[y][x] < targetCalc[y][x] || playerCalc[y][x] == 1) {
            newPosition = moveTowardPlayer(x, y);
        } else {
            newPosition = moveTowardTarget(x, y);
        }
        return (newPosition);
    }

    public boolean checkPlayerPos(int x, int y) {
        if (targetCalc[y][x] <= 0) {
            return (false);
        }
        return (true);
    }
}
