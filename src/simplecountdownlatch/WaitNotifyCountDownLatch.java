package simplecountdownlatch;

/**
 * A wait/notify implementation of the SimpleCountDownLatch.
 */
public class WaitNotifyCountDownLatch implements SimpleCountDownLatch {

  private int count;

  public WaitNotifyCountDownLatch(int count) {
    this.count = count;
    if (count < 0) {
      throw new IllegalArgumentException("count cannot be negative");
    }
  }

  @Override
  public void await() throws InterruptedException {
    synchronized (this) {
      while (count > 0) {
        wait();
      }
    }
  }

  @Override
  public void countDown() {
    synchronized (this) {
      if (getCount() > 0) {
        count--;

        if (getCount() == 0) {
          notifyAll();
        }
      }

    }
  }

  @Override
  public int getCount() {
    return count;
  }
}
