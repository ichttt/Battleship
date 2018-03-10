package ichttt.battleship;

public enum EnumHitResult {
    HIT("X"), NO_HIT("O");

    public String text;

    EnumHitResult(String text) {
        this.text = text;
    }
}
