package com.workerp.common_lib.util;

import java.util.Random;

public class CodeGenerator {

    public static String generateSixDigitCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public static void main(String[] args) {
        System.out.println(generateSixDigitCode());
    }
}