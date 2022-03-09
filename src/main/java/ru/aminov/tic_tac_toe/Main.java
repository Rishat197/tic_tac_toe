package ru.aminov.tic_tac_toe;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        // массив хранения всех значений клеток, где клетка которая содержит знак пробела означает, что туда ещё не сходили
        char[] iSquare = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
        int iNumber;                // выбранная клетка
        boolean bVictory = true;    // выиграл или нет
        boolean bPlayOrNot = true;  // играть ещё партию?
        String character;           // выбор пользователя, играть ли ещё или нет
        String name1;               // имя первого игрока
        String name2;               // имя второго игрока
        String sName;               // текущий игрок
        String currentSign;         // текущий знак
        int numberOfGames = 0;      // количество сыгранных игр
        int numberOfGamesWon1 = 0;  // количество выигранных партий 1-м игроком
        int numberOfGamesWon2 = 0;  // количество выигранных партий 2-м игроком
        FileWriter gameStatistics = null;   // файл статистики игр
        BufferedWriter buff = null;         // буфер для записи в файл

        Scanner scanner = new Scanner(System.in);

        System.out.println("Ну что, может сыграем в крестики-нолики?");
        System.out.println("Каждая клетка пронумерована. Необходимо выбрать нужный номер клетки");
        System.out.println("У первого игрока устанавливается символ \"X\", а у второго - \"O\"");
        System.out.println("Введите имя первого игрока:");
        name1 = scanner.next();
        System.out.println("Введите имя второго игрока:");
        name2 = scanner.next();

        do {
            vDrawing(iSquare);
            for (int i = 1; i <= 9; i++) {

                // кто ходит?
                if (i == 2 || i == 4 || i == 6 || i == 8) {
                    sName = name2;
                    currentSign = "нолик";
                } else {
                    sName = name1;
                    currentSign = "крестик";
                }
                iNumber = vMove(iSquare, sName, currentSign);

                if (sName.equals(name1)) {
                    iSquare[iNumber] = 'X';
                } else {
                    iSquare[iNumber] = 'O';
                }

                vDrawing(iSquare);

                bVictory = fVictory(iSquare);
                if (bVictory) {
                    System.out.println("Ура! " + sName + ", вы выиграли!!!");
                    if (sName.equals(name1))
                        numberOfGamesWon1++;
                    else
                        numberOfGamesWon2++;
                    break;
                }
            }

            if (!bVictory) {
                System.out.println("Похоже это ничья.");
            }
            numberOfGames++;

            System.out.println("Сыграем ещё? Если хотите продолжить нажмите любую клавишу, а если уже хватит, нажмите клавишу \"n\" ");
            character = scanner.next();
            if (character.equals("n")) {
                try {
                    gameStatistics = new FileWriter("GameStat.txt", true);
                    buff = new BufferedWriter(gameStatistics);
                    buff.write("\r\n" + name1 + " выиграл y " + name2 + " всего " + numberOfGamesWon1 + " партий\r\n");
                    buff.write(name2 + " выиграл y " + name1 + " всего " + numberOfGamesWon2 + " партий\r\n");
                    buff.write("Всего " + (numberOfGames - numberOfGamesWon1 - numberOfGamesWon2) +
                            " партий между " + name1 + " и " + name2 + " завершилось в ничью.\r\n");
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        buff.flush();
                        buff.close();
                        gameStatistics.close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }

                System.out.println("Сыграно " + numberOfGames + " партий");
                System.out.println(name1 + " выиграл y " + name2 + " всего " + numberOfGamesWon1 + " партий");
                System.out.println(name2 + " выиграл y " + name1 + " всего " + numberOfGamesWon2 + " партий");
                System.out.println("Всего " + (numberOfGames - numberOfGamesWon1 - numberOfGamesWon2) +
                        " партий между " + name1 + " и " + name2 + " завершилось в ничью.");
                System.out.println("До скорой встречи!!!");
                break;
            } else {
                // убрать все крестики и нолики с массива для новой игры
                for (int i = 0; i < 9; i++) {
                    iSquare[i] = ' ';
                }
            }

        } while (bPlayOrNot);
    }

    // выбор позиции
    static int vMove(char[] i, String name, String sign) {
        boolean bCorrectPosition;
        int iPosition;              // выбранная клетка

        Scanner input = new Scanner(System.in);
        do {
            bCorrectPosition = false;
            System.out.println(name + " выберите позицию (от 1 до 9), куда вы хотите поставить " + sign + ":");
            iPosition = input.nextInt();

            if (iPosition < 1 | iPosition > 9) {
                bCorrectPosition = true;
                System.out.println("Некорректный ввод! Число может быть только от 1 до 9");
            } else if (i[iPosition - 1] != ' ') {
                bCorrectPosition = true;
                System.out.println("Некорректный ввод! Эта позиция уже занята. " + name + " выберите другую позицию");
            }
        } while (bCorrectPosition);
        return iPosition - 1;
    }

    // прорисовка поля игры
    static void vDrawing(char[] i) {
        System.out.println("___________________________");
        System.out.println("|1       |2       |3      |");
        System.out.println("|   " + i[0] + "    |   " + i[1] + "    |   " + i[2] + "   |");
        System.out.println("|________|________|_______|");
        System.out.println("|4       |5       |6      |");
        System.out.println("|   " + i[3] + "    |   " + i[4] + "    |   " + i[5] + "   |");
        System.out.println("|________|________|_______|");
        System.out.println("|7       |8       |9      |");
        System.out.println("|   " + i[6] + "    |   " + i[7] + "    |   " + i[8] + "   |");
        System.out.println("|________|________|_______|");
    }

    // все варианты выигрыша; возвращает true, если победа
    static boolean fVictory(char[] i) {
        if (i[0] != ' ' & i[0] == i[1] & i[1] == i[2]) return true;
        if (i[3] != ' ' & i[3] == i[4] & i[4] == i[5]) return true;
        if (i[6] != ' ' & i[6] == i[7] & i[7] == i[8]) return true;
        if (i[0] != ' ' & i[0] == i[3] & i[3] == i[6]) return true;
        if (i[1] != ' ' & i[1] == i[4] & i[4] == i[7]) return true;
        if (i[2] != ' ' & i[2] == i[5] & i[5] == i[8]) return true;
        if (i[0] != ' ' & i[0] == i[4] & i[4] == i[8]) return true;
        if (i[2] != ' ' & i[2] == i[4] & i[4] == i[6]) return true;
        return false;
    }
}
