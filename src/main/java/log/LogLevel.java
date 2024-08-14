package log;

// Enum — класс, предназначенный для создания ограниченного круга значений.

public enum LogLevel
{
    Trace(0),
    Debug(1),
    Info(2),
    Warning(3),
    Error(4),
    Fatal(5);

    private final int level;

    private LogLevel(int iLevel) {
        this.level = iLevel;
    }

    public int getLevel() {
        return level;
    }
}