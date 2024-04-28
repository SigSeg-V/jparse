package main.ast;

public enum Field {
    Identifier,
    Name,
    Operation,
    Parameter,
    Comment;

    public static Field next(Field item) {

        if (item.ordinal() + 1 >= Field.values().length) {
            return item;
        }
        return Field.values()[item.ordinal() + 1];
    }

    public Field next() {
        return Field.next(this);
    }
}
