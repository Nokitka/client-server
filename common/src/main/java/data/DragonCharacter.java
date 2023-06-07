package data;

import java.io.Serializable;

public enum DragonCharacter implements Serializable {
    EVIL(1),
    GOOD(2),
    CHAOTIC_EVIL(3),
    FICKLE(4);

    private final int code;

    private DragonCharacter(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static String getValues() {

        StringBuilder stringOfTittles = new StringBuilder();

        for (DragonCharacter itr : values()) {
            stringOfTittles.append(itr).append("(" + itr.getCode() + ")").append(", ");
        }

        return stringOfTittles.substring(0, stringOfTittles.length() - 2);

    }

    public static DragonCharacter searchByCode(int code) {
        for (DragonCharacter character : DragonCharacter.values()) {
            if (character.getCode() == code)
                return character;
        }
        return DragonCharacter.EVIL;
    }
}
