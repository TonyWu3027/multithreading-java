package simplecountdownlatch;

/**
 * A homegrown version of CountDownLatch.
 *
 * @see java.util.concurrent.CountDownLatch
 */
public interface SimpleCountDownLatch {

  /**
   * Causes the current thread to wait until the latch has counted down to zero. If the current
   * count is already zero then this method returns immediately.
   */
  void await() throws InterruptedException;

  /**
   * Decrements the count of the latch, releasing all waiting threads when the count reaches zero.
   * If the current count already equals zero then nothing happens.
   */
  void countDown();

  /**
   * Returns the current count.
   */
  int getCount();
}
