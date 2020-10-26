public enum Estado {
    ABIERTA("A"),CERRADA("C"),BLOQUEADA("B"),MARCADA("M");

    private final String text;

    Estado(final String text) {
        this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }
}
