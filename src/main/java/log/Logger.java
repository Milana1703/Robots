package log;

public final class Logger
{
    private static final LogWindowSource defaultLogSource;
    static {
        defaultLogSource = new LogWindowSource(100);  // Статический инициализатор
    }

    private Logger()
    {
    }

    public static void debugLog(String strMessage) {
        defaultLogSource.append(LogLevel.Debug, strMessage);
    }

    public static void errorLog(String strMessage) {
        defaultLogSource.append(LogLevel.Error, strMessage);
    }

    public static LogWindowSource getDefaultLogSource() {
        return defaultLogSource;
    }
}