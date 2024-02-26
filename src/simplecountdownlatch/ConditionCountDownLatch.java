package simplecountdownlatch;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A condition variable implementation of the SimpleCountDownLatch.
 */
public class ConditionCountDownLatch implements SimpleCountDownLatch {

  private int count;
  private final Lock lock = new ReentrantLock();
  private final Condition condition = lock.newCondition();

  public ConditionCountDownLatch(int count) {
    this.count = count;
    if (count < 0) {
      throw new IllegalArgumentException("count cannot be negative");
    }
  }


  @Override
  public void await() throws InterruptedException {
    lock.lock();
    try {
      while (count > 0) {
        condition.await();
      }
    } finally {
      lock.unlock();
    }
  }


  @Override
  public void countDown() {
    lock.lock();
    try {
      if (count > 0) {
        count--;

        if (count == 0) {
          condition.signalAll();
        }
      }
    } finally {
      lock.unlock();
    }
  }

  @Override
  public int getCount() {
    return count;
  }
}
