package ichttt.battleship;

public enum EnumHitResult {
    HIT("X"), NO_HIT("O"), NO_TESTED("");

    public String text;

    EnumHitResult(String text) {
        this.text = text;
    }
}
