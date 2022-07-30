package com.home.game.minesweeper;

import com.home.game.minesweeper.service.GameWinException;
import com.home.game.minesweeper.service.GameOverException;
import com.home.game.minesweeper.service.SapperService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SapperApplication {

    public static void main(String[] args) throws IOException {
        SapperService sapperService = SapperService.init(10, 10);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        while (!(line = reader.readLine()).equals("EXIT")) {
            String[] strings = line.split(",");
            try {
                if (isValidInput(strings)) {
                    sapperService.open(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
                    sapperService.printField();
                }
            } catch (GameOverException e) {
                System.out.println("You lose!");
                sapperService.printField();
                break;
            } catch (GameWinException e) {
                System.out.println("You win!");
                sapperService.printField();
                break;
            }
        }
    }

    private static boolean isValidInput(String[] strings) {
        if (strings.length != 2) {
            return false;
        }
        try {
            Integer.parseInt(strings[0]);
            Integer.parseInt(strings[1]);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
