package common.data;

public enum DragonCharacter {
    EVIL("evil"),
    GOOD("good"),
    CHAOTIC_EVIL("chaotic evil"),
    FICKLE("fickle"),
    ERROR("error");

    private String tittle;

    private DragonCharacter(String tittle) {
        this.tittle = tittle;
    }

    public String getTittle() {
        return tittle;
    }

    public static String getStringOfTittles() {

        StringBuilder stringOfTittles = new StringBuilder();

        for (DragonCharacter itr : values()) {
            stringOfTittles.append(itr.getTittle()).append(", ");
        }

        return stringOfTittles.substring(0, stringOfTittles.length() - 2);

    }
}
