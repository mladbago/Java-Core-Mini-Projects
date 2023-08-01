package uj.wmii.pwj.collections;


import java.util.*;

public class BattleshipGeneratorImpl implements BattleshipGenerator {
    @Override
    public String generateMap() {
        char [][]map = new char[10][10];
        for (int i = 0; i < 10; i ++) {
            for (int j = 0; j < 10; j ++) {
                map[i][j] = '.';
            }
        }
        StringBuilder s = new StringBuilder();
        placeShip(3, map);
        for (int i = 0; i < 10; i ++) {
            for (int j = 0; j < 10; j ++) {
                s.append(map[i][j]);
            }
        }
        return s.toString();
    }

    public void generateRandomShips(int size, char [][]map, int xRand, int yRand, List<Integer> posible) {
        boolean [] direction = new boolean[4];
        direction[0] = true; direction[1] = true;
        direction[2] = true; direction[3] = true;
        List <Integer> addElements = new ArrayList<>();
        addElements.clear();
        if (map[xRand][yRand] == '#')
            return;
        int s = 0;
        List<Integer> list = new ArrayList<>();
        list.clear();
        if (size == 3)
            map[xRand][yRand] = '#';
        if (size != 3) {
            if (!checkSides(xRand, yRand, map))
                return;
            if (!checkCornersAgain(xRand, yRand, map))
                return;
        }
        addElements.add(xRand);
        addElements.add(yRand);
        int save = -1;
        while (s < size) {
            if (size != 3) {
                if (!checkSides(xRand, yRand, map))
                    return;
                if (!checkCornersAgain(xRand, yRand, map))
                    return;
            }
            checkPositions(xRand, yRand, list, save);
            if (list.size() == 0)
                return;
            Random chooseRandom = new Random();
            if (!checkCorners(xRand, yRand, map, direction))
                return;
            int rnd = chooseRandom.nextInt(list.size());
            int choose = list.get(rnd);
            xRand = changePositions(xRand, yRand, choose, direction)[0];
            yRand = changePositions(xRand, yRand, choose, direction)[1];
            save = changePositions(xRand, yRand, choose, direction)[2];
//                if (!checkCorners(xRand, yRand, map, direction))
//                    return;
            if (size != 3) {
                if (!checkSides(xRand, yRand, map))
                    return;
                if (!checkCornersAgain(xRand, yRand, map))
                    return;
            }
            addElements.add(xRand);
            addElements.add(yRand);
            if (size == 3)
                map[xRand][yRand] = '#';
            list.clear();
            s ++;
        }
        if (s == size) {
            posible.addAll(addElements);
        }
    }

    public int[] changePositions(int xRand, int yRand, int choose, boolean []direction) {


        int []newVal = new int[3];
        direction[0] = false; direction[1] = false;
        direction[2] = false; direction[3] = false;
        if (choose == 0) {
            newVal[0] = xRand + 1;
            newVal[1] = yRand;
            newVal[2] = 1;
            direction[0] = true;
        }
        if (choose == 1) {
            newVal[0] = xRand - 1;
            newVal[1] = yRand;
            newVal[2] = 0;
            direction[1] = true;
        }
        if (choose == 2) {
            newVal[0] = xRand;
            newVal[1] = yRand + 1;
            newVal[2] = 3;
            direction[2] = true;
        }
        if (choose == 3) {
            newVal[0] = xRand;
            newVal[1] = yRand - 1;
            newVal[2] = 2;
            direction[3] = true;
        }
        return newVal;
    }
    public boolean checkSides(int x, int y, char[][]map) {
        if (isValid(x + 1, y)) {
            if (map[x + 1][y] == '#')
                return false;
        }
        if (isValid(x - 1, y)) {
            if (map[x - 1][y] == '#')
                return false;
        }
        if (isValid(x, y + 1)) {
            if (map[x][y + 1] == '#')
                return false;
        }
        if (isValid(x, y - 1)) {
            if (map[x][y - 1] == '#')
                return false;
        }
        return true;
    }
    public void checkPositions(int xRand, int yRand, List<Integer> list, int save) {
        if (isValid(xRand + 1, yRand) && save != 0) {
            list.add(0);
        }
        if (isValid(xRand - 1, yRand) && save != 1) {
            list.add(1);
        }
        if (isValid(xRand, yRand + 1) && save != 2) {
            list.add(2);
        }
        if (isValid(xRand, yRand - 1) && save != 3) {

            list.add(3);
        }
    }
    public boolean checkCorners (int x, int y, char[][] map, boolean[] direction) {
        if (isValid(x + 1, y - 1) && (direction[0] || direction[3])) {
            if (map[x + 1][y - 1] == '#')
                return false;
        }

        if (isValid(x + 1, y + 1) && (direction[0] || direction[2])) {
            if (map[x + 1][y + 1] == '#')
                return false;
        }

        if (isValid(x - 1, y - 1) && (direction[1] || direction[3])) {
            if (map[x - 1][y - 1] == '#')
                return false;
        }

        if (isValid(x - 1, y + 1) && (direction[1] || direction[2])) {
            if (map[x - 1][y + 1] == '#')
                return false;
        }
        return true;
    }
    public boolean checkCornersAgain (int x, int y, char[][] map) {
        if (isValid(x + 1, y - 1)) {
            if (map[x + 1][y - 1] == '#')
                return false;
        }

        if (isValid(x + 1, y + 1)) {
            if (map[x + 1][y + 1] == '#')
                return false;
        }

        if (isValid(x - 1, y - 1)) {
            if (map[x - 1][y - 1] == '#')
                return false;
        }

        if (isValid(x - 1, y + 1)) {
            if (map[x - 1][y + 1] == '#')
                return false;
        }
        return true;
    }

    public void placeShip (int size, char [][]map) {
        Random rnd = new Random();
        int xRand = rnd.nextInt(10);
        int yRand = rnd.nextInt(10);
        List<Integer> finalList = new ArrayList<>();
        generateRandomShips(size, map, xRand, yRand, finalList);
        shipPlacer(map, 2, finalList);
        shipPlacer(map, 2, finalList);
        shipPlacer(map, 1, finalList);
        shipPlacer(map, 1, finalList);
        shipPlacer(map, 1, finalList);
        shipPlacer(map, 0, finalList);
        shipPlacer(map, 0, finalList);
        shipPlacer(map, 0, finalList);
        shipPlacer(map, 0, finalList);
    }

    public void shipPlacer(char[][] map, int shipSize, List<Integer> finalList) {
        finalList.clear();
        for (int i = 0; i < 10; i ++) {
            for (int j = 0; j < 10; j ++) {
                generateRandomShips(shipSize, map, i, j, finalList);
            }
        }
        Random rand = new Random();
        int s = rand.nextInt(finalList.size());
        int finalRand;
        if (shipSize == 2) {
            finalRand = s - s % 6;
            map[finalList.get(finalRand)][finalList.get(finalRand + 1)] = '#';
            map[finalList.get(finalRand + 2)][finalList.get(finalRand + 3)] = '#';
            map[finalList.get(finalRand + 4)][finalList.get(finalRand + 5)] = '#';
        }
        if (shipSize == 1) {
            finalRand = s - s % 4;
            map[finalList.get(finalRand)][finalList.get(finalRand + 1)] = '#';
            map[finalList.get(finalRand + 2)][finalList.get(finalRand + 3)] = '#';
        }
        if (shipSize == 0) {
            finalRand = s - s % 2;
            map[finalList.get(finalRand)][finalList.get(finalRand + 1)] = '#';
        }
    }
    public boolean isValid (int x, int y) {
        return x >= 0 && y >= 0 && x < 10 && y < 10;
    }
}
