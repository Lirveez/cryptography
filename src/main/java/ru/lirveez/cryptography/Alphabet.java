package ru.lirveez.cryptography;

public class Alphabet {
    public static int firstUpperLetterCode = 'А';
    public static int lastUpperLetterCode = 'Я';
    public static int firstLetterCode = 'а';
    public static int lastLetterCode = 'я';
    public static int size = lastLetterCode - firstLetterCode;

    public static char[] upperAlphabetRU = {
            'А', 'Б', 'В',
            'Г', 'Д', 'Е',
            'Ж', 'З',
            'И', 'Й', 'К',
            'Л', 'М', 'Н',
            'О', 'П', 'Р',
            'С', 'Т', 'У',
            'Ф', 'Х', 'Ц',
            'Ч', 'Ш', 'Щ',
            'Ъ', 'Ы', 'Ь',
            'Э', 'Ю', 'Я'};
    public static char[] lowerAlphabetRU = {
            'а', 'б', 'в',
            'г', 'д', 'е',
            'ж', 'з',
            'и', 'й', 'к',
            'л', 'м', 'н',
            'о', 'п', 'р',
            'с', 'т', 'у',
            'ф', 'х', 'ц',
            'ч', 'ш', 'щ',
            'ъ', 'ы', 'ь',
            'э', 'ю', 'я'
    };

    public static int getLetterPosition(int letterCode) {
        char letter = (char) letterCode;
        for (int i = 0; i < upperAlphabetRU.length; i++)
            if (upperAlphabetRU[i] == letter)
                return i + 1;
        for (int i = 0; i < lowerAlphabetRU.length; i++)
            if (lowerAlphabetRU[i] == letter)
                return i + 33;
        return (int) letter;
    }

    public static char getLetterByPosition(int position) {
        if (position > 32 && position <= 64)
            return lowerAlphabetRU[position - 33];
        if (position > 0 && position <= 32)
            return upperAlphabetRU[position - 1];
        return ' ';
    }
}
