package ru.lirveez.cryptography.C;

import ru.lirveez.cryptography.Alphabet;
import ru.lirveez.cryptography.CypherInterface;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MatrixCypher implements CypherInterface {

    private final int[][] encodeKey = {
            {1, 4, 8},
            {3, 7, 2},
            {6, 9, 5}
    };

    private final int[][] decodeKey = getDecodeKey();

    @Override
    public String encode(String text) {
        int textSize = text.length();
        boolean textLengthCanBeDividedByThree = textSize % 3 == 0;
        int vectorAmount = textLengthCanBeDividedByThree ? textSize / 3 : textSize / 3 + 1;
        int[][] vectors = new int[vectorAmount][3];
        int[] textArray = text.codePoints()
                .map(Alphabet::getLetterPosition)
                .toArray();
        for (int i = 0; i < textSize; i++) {
            int vectorNumber = i / 3;
            int row = i % 3;
            vectors[vectorNumber][row] = textArray[i];
        }
        String[] result = new String[vectorAmount];
        for (int i = 0; i < vectorAmount; i++) {
            result[i] = (IntStream.of(multiple(encodeKey, vectors[i]))
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(",")));
        }
        if (!textLengthCanBeDividedByThree)
            result[vectorAmount - 1] = formatAndReturnLastVector(result[vectorAmount - 1], textSize % 3);
        return String.join(",", result);
    }

    @Override
    public String decode(String text) {
        String[] split = text.split(",");
        boolean textLengthCanBeDividedByThree = split.length % 3 == 0;
        int vectorAmount = textLengthCanBeDividedByThree ? split.length / 3 : split.length / 3 + 1;
        int[][] vectors = new int[vectorAmount][3];
        for (int i = 0; i < vectorAmount; i++)
            for (int j = 0; j < 3; j++)
                if (i * 3 + j < split.length)
                    vectors[i][j] = Integer.parseInt(split[i * 3 + j]);
        String[] resultArray = new String[vectorAmount];
        for (int i = 0; i < vectorAmount; i++) {
            resultArray[i] = IntStream.of(multiple(decodeKey, vectors[i]))
                    .map(key -> (int) (key / getD()))
                    .mapToObj(Alphabet::getLetterByPosition)
                    .map(String::valueOf)
                    .collect(Collectors.joining());
        }
        StringBuilder result = new StringBuilder();
        for (String string : resultArray) {
            result.append(string);
        }
        return result.toString();
    }

    private int[] multiple(int[][] key, int[] vector) {
        int[] intResult = new int[key.length];
        for (int i = 0; i < key.length; i++) {
            for (int j = 0; j < key.length; j++)
                intResult[i] += key[i][j] * vector[j];
        }
        return intResult;
    }

    private String formatAndReturnLastVector(String string, int usedCharsAtLastVector) {
        String[] codesArray = string.split(",");
        if (usedCharsAtLastVector == 1) {
            return codesArray[0];
        }
        return codesArray[0] + "," + codesArray[1];
    }

    private int[][] getDecodeKey() {
        return new int[][]{
                {17, 52, -48},
                {-3, -43, 22},
                {-15, 15, -5}};
    }

    private double getD() {
        return encodeKey[0][0] * encodeKey[1][1] * encodeKey[2][2] +
                encodeKey[0][1] * encodeKey[1][2] * encodeKey[2][0] +
                encodeKey[0][2] * encodeKey[1][0] * encodeKey[2][1] -
                encodeKey[0][2] * encodeKey[1][1] * encodeKey[2][0] -
                encodeKey[0][1] * encodeKey[1][0] * encodeKey[2][2] -
                encodeKey[0][0] * encodeKey[1][2] * encodeKey[2][1];
    }

}
