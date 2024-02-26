package simplecountdownlatch;

class ConditionCountDownLatchTest extends SimpleCountDownLatchTest {

  @Override
  protected SimpleCountDownLatch createLatch(int count) {
    return new ConditionCountDownLatch(count);
  }
}