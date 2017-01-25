package ru.seregamoskal.rdpexec.domain;

import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.IOException;

import static java.awt.event.KeyEvent.*;

/**
 * Created by Дмитрий on 25.01.2017.
 */
public class Test {

    public static void main(String[] args) throws IOException, AWTException, InterruptedException {
        final String phrase = "mstsc";
        final Robot robot = new Robot();
        final KeyBoardKeys printer = new KeyBoardKeys(robot);
        final char[] chars = phrase.toCharArray();
        robot.keyPress(VK_WINDOWS);
        robot.keyPress(VK_R);
        robot.keyRelease(VK_R);
        robot.keyRelease(VK_WINDOWS);
        robot.keyPress(VK_RIGHT);

        Thread.sleep(3000);
        for (char aChar : chars) {
            printer.keyPress(aChar);
        }
        robot.keyPress(VK_ENTER);
    }

    private static boolean isWentOk(String errors) throws IOException {
        return !StringUtils.hasText(errors);
    }
}
