package com.gb.lessons;

import java.util.Random;
import java.util.Scanner;

public class Main {
    private static char[][] map;
    private final static int SIZE = 3;
    private final static char DOT_X = 'X';
    private final static char DOT_O = 'O';
    private final static char DOT_EMPTY = '.';

    public static void main(String[] args) {
        map = new char[SIZE][SIZE];
        initMap();
        printMap();

        while (true) {

            humanTurn();
            printMap();

            if (checkWin(DOT_X)) {
                System.out.println("Победа " + DOT_X);
                break;
            }

            if (isDraw()) {
                System.out.println("Победила дружба :)");
                break;
            }

            aiTurn();
            printMap();

            if (checkWin(DOT_O)) {
                System.out.println("Победа " + DOT_O);
                break;
            }

            if (isDraw()) {
                System.out.println("Победила дружба :)");
                break;
            }
        }
    }

    private static void initMap() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                map[i][j] = DOT_EMPTY;
            }
        }
    }

    private static void printMap() {
        for (int i = 0; i <= SIZE; i++) {
            System.out.print(i + " ");
        }

        System.out.println();

        for (int i = 0; i < SIZE; i++) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < SIZE; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static boolean isCellValid(int x, int y) {
        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {
            return false;
        }

        if (map[x][y] == DOT_EMPTY) {
            return true;
        }

        return false;
    }

    private static boolean isDraw() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY)
                    return false;
            }
        }

        return true;
    }

    private static void humanTurn() {
        Scanner scanner = new Scanner(System.in);
        int x, y;

        do {
            System.out.println("Введите коорд. x и y: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isCellValid(x, y));

        map[x][y] = DOT_X;
    }


    private static void aiTurn() {
        if (!aiFindMove(DOT_O)) { // Спера АИ ищет выигрышный ход, для этого передаем методу в качестве параметра 'O'
            if (!aiFindMove(DOT_X)) { // Если такого нет, то ищем выигрышный ход человека и блокируем ее, если и такого нет, то делаем ход в случайную клетку
                Random random = new Random();
                int x, y;
                do {
                    x = random.nextInt(SIZE);
                    y = random.nextInt(SIZE);
                } while (!isCellValid(x, y));

                map[x][y] = DOT_O;
            }
        }
    }

    /*За основу взят метод checkWin, который проверяет есть ли победа
     * Для начала анализуем все поля и мыслинно ставим символ на пустую клетку
     * Если этот поставленный символ образовывает победу, то оставляем и выходим из цикла
     * Аналогично реализовано и блокирование выигрыша человека
     * За выигрышный ход или блокирование отвечает переданный параметр*/
    private static boolean aiFindMove(char symb) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (map[i][j] == DOT_EMPTY) {
                    map[i][j] = symb;
                    if (checkWin(symb)) {
                        map[i][j] = DOT_O;
                        return true;
                    } else {
                        map[i][j] = DOT_EMPTY;
                    }
                }
            }
        }

        return false;
    }

    private static boolean checkWin(char symb) {
        if (checkLines(symb)) {
            return true;
        } else if (checkDiag(symb)) {
            return true;
        }

        return false;
    }

    // Проверка горизонталей и вертикалей
    private static boolean checkLines(char symb) {
        boolean col = true;
        boolean row = true;

        for (int i = 0; i < SIZE; i++) {
            col = true;
            row = true;

            for (int j = 0; j < SIZE; j++) {
                col &= (map[i][j] == symb);
                row &= (map[j][i] == symb);
            }

            if (col || row) return true;
        }

        return false;
    }

    // Проверка диагоналей
    private static boolean checkDiag(char symb) {
        boolean firstDiag = true;
        boolean secondDiag = true;
        int len = SIZE - 1;

        for (int i = 0; i < SIZE; i++) {
            firstDiag &= (map[i][i] == symb);
            secondDiag &= (map[i][len]) == symb;

            len -= 1;
        }


        return firstDiag || secondDiag;
    }
}
