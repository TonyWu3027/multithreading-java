# Multithreading Java Examples

My notes and examples on multithreading programming with Java.

## Examples

### `SimpleCountDownLatch`

[`simplecountdownlatch.SimpleCountDownLatch`](./src/simplecountdownlatch/SimpleCountDownLatch.java)
is a homegrown
version
of [CountDownLatch](https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/CountDownLatch.html)
that reproduces most of its functionalities with thread-safe guarantee.
We provide two different implementations of the `CountDownLatch` using different inter-thread
communication primitives:

- [`simplecountdownlatch.WaitNotifyCountDownLatch`](./src/simplecountdownlatch/WaitNotifyCountDownLatch.java)
  is a `SimpleCountDownLatch` implemented with `Object.wait()` and `Obejct.notifyAll()`
- [`simplecountdownlath.ConditionCountDown`](./src/simplecountdownlatch/ConditionCountDownLatch.java)
  is a `SimpleCountDownLatch` implemented with `java.util.concurrent.locks.Condition` condition
  variable of a reentrant lock