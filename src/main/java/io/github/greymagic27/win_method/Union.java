package io.github.greymagic27.win_method;

import java.lang.reflect.Field;

public abstract class Union extends Structure {

    private Field activeField;

    protected Union() {
        super();
    }

    @Override
    protected boolean isUnion() {
        return true;
    }

    public void setType(Class<?> type) {
        for (Field f : fields.values()) {
            if (f.getType() == type) {
                activeField = f;
                return;
            }
        }
        throw new IllegalArgumentException("No field of type: " + type + " in " + this);
    }

    public void setType(String fieldName) {
        Field f = fields.get(fieldName);
        if (f == null) throw new IllegalArgumentException("No field named: " + fieldName + " in " + this);
        activeField = f;
    }

    @Override
    public void write() {
        if (activeField == null) throw new IllegalArgumentException(getClass().getName() + ".write() called with no active field");
        writeField(activeField);
    }

    @Override
    public void read() {
        if (activeField == null) return;
        readField(activeField);
    }

    public boolean hasActiveField() {
        return activeField != null;
    }
}
