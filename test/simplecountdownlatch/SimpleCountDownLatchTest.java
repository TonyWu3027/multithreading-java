package simplecountdownlatch;

import java.lang.Thread.State;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public abstract class SimpleCountDownLatchTest {

  protected abstract SimpleCountDownLatch createLatch(int count);

  @Test
  void getCount_withPositiveCount_countDownDecrementsCount() {
    SimpleCountDownLatch latch = createLatch(100);
    List<Thread> decrementThreads = IntStream.range(0, 2).mapToObj(i -> new Thread(
        latch::countDown)).toList();
    decrementThreads.forEach(Thread::start);
    decrementThreads.forEach(thread -> {
      try {
        thread.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    Assertions.assertEquals(98, latch.getCount());
  }

  @Test
  void await_withCountGoingToZero_allThreadsAreReleased() {
    SimpleCountDownLatch latch = createLatch(100);

    List<Thread> waitingThreads = IntStream.range(0, 200).mapToObj(i -> new Thread(() -> {
      try {
        latch.await();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    })).toList();

    List<Thread> decrementThreads = IntStream.range(0, 100).mapToObj(i -> new Thread(
        latch::countDown)).toList();

    waitingThreads.forEach(Thread::start);
    decrementThreads.forEach(Thread::start);

    waitingThreads.forEach(thread -> {
      try {
        thread.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    decrementThreads.forEach(thread -> {
      try {
        thread.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    waitingThreads.forEach(thread -> Assertions.assertNotEquals(State.WAITING, thread.getState()));
  }

  @Test
  void getCount_withZeroCount_notGoingNegative() {
    SimpleCountDownLatch latch = createLatch(0);

    List<Thread> decrementThreads = IntStream.range(0, 10).mapToObj(i -> new Thread(
        latch::countDown)).toList();

    decrementThreads.forEach(thread -> {
      try {
        thread.join();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    });

    Assertions.assertEquals(0, latch.getCount());
  }

  @Test
  void await_withPositiveCount_waits() throws InterruptedException {
    SimpleCountDownLatch latch = createLatch(1);

    List<Thread> waitingThreads = IntStream.range(0, 20).mapToObj(i -> new Thread(() -> {
      try {
        latch.await();
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    })).toList();

    waitingThreads.forEach(Thread::start);

    Thread.sleep(1000);

    waitingThreads.forEach(thread -> Assertions.assertEquals(State.WAITING, thread.getState()));
  }
}