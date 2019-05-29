package top.alertcode.adelina.framework.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by gizmo on 15/12/11.
 *
 * @author Bob
 * @version $Id: $Id
 */
public final class ThreadUtils {
    private ThreadUtils() {
    }

    /**
     * <p>sleep.</p>
     *
     * @param millis a long.
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

    /**
     * <p>gracefulShutdown.</p>
     *
     * @param pool               a {@link java.util.concurrent.ExecutorService} object.
     * @param shutdownTimeout    a int.
     * @param shutdownNowTimeout a int.
     * @param timeUnit           a {@link java.util.concurrent.TimeUnit} object.
     */
    public static void gracefulShutdown(ExecutorService pool, int shutdownTimeout, int shutdownNowTimeout,
                                        TimeUnit timeUnit) {
        pool.shutdown();
        try {
            if (!pool.awaitTermination(shutdownTimeout, timeUnit)) {
                pool.shutdownNow();
                if (!pool.awaitTermination(shutdownNowTimeout, timeUnit)) {
                    System.err.println("Pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * <p>normalShutdown.</p>
     *
     * @param pool a {@link java.util.concurrent.ExecutorService} object.
     * @param timeout a int.
     * @param timeUnit a {@link java.util.concurrent.TimeUnit} object.
     */
    public static void normalShutdown(ExecutorService pool, int timeout, TimeUnit timeUnit) {
        try {
            pool.shutdownNow();
            if (!pool.awaitTermination(timeout, timeUnit)) {
                System.err.println("Pool did not terminate");
            }
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }

    public static class CustomizableThreadFactory implements ThreadFactory {

        private final String namePrefix;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        public CustomizableThreadFactory(String poolName) {
            namePrefix = poolName + "-pool-";
        }

        @Override
        public Thread newThread(Runnable runable) {
            return new Thread(runable, namePrefix + threadNumber.getAndIncrement());
        }
    }
}
