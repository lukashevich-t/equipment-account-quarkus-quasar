package by.gto.equipment.account.helpers;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс-помощник для облегчения закрытия множества AutoCloseables, используя механизм try-with resources.
 * Пример использования:
 * <pre>
 * try(AutoCloseableHelper ah = new AutoCloseableHelper()) {
 *   Connection c = ah.add(createConnection());
 *   ...
 *   Statement s = ah.add(c.createStatement())
 *   ..
 *   ResultSet rs = ah.add(s.execute());
 * }
 * </pre>
 */
public final class AutoCloseableHelper implements AutoCloseable {
    private final List<AutoCloseable> closeables = new ArrayList<>();

    @Override
    public void close() {
        for (int i = closeables.size() - 1; i >= 0; i--) {
            try {
                closeables.get(i).close();
            } catch (Throwable ignored) {
            }
        }
    }

    public <R extends AutoCloseable> R add(R autoCloseable) {
        if (autoCloseable != null) {
            closeables.add(autoCloseable);
        }
        return autoCloseable;
    }

    public <R extends AutoCloseable> void closeAndForget(R autoCloseable) {
        if (autoCloseable == null) {
            return;
        }
        while (closeables.remove(autoCloseable)) {
        }
        try {
            autoCloseable.close();
        } catch (Throwable ignored) {
        }
    }

    public AutoCloseableHelper() {
    }

    public AutoCloseableHelper(AutoCloseable... autoCloseables) {
        for (AutoCloseable a : autoCloseables) {
            if (a != null) {
                closeables.add(a);
            }
        }
    }
}
