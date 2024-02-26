package simplecountdownlatch;

class WaitNotifyCountDownLatchTest extends SimpleCountDownLatchTest {

  @Override
  protected SimpleCountDownLatch createLatch(int count) {
    return new WaitNotifyCountDownLatch(count);
  }

}