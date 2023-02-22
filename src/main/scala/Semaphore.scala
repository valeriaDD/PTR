class Semaphore(private var count: Int) {
  def acquire(): Unit = synchronized {
    while (count == 0) {
      wait()
    }
    count = count - 1
  }

  def release(): Unit = synchronized {
    count = count + 1
    notify()
  }
}

object Semaphore extends App {
  private val semaphore = new Semaphore(1)
  private val threads = (1 to 3).map { i =>
    new Thread(() => {
      semaphore.acquire()
        println(s"Thread $i entered the critical section")
        // Simulate some work
        Thread.sleep(1000)
        semaphore.release()
        println(s"Thread $i exited the critical section")
    })
  }

  threads.foreach(_.start())
  threads.foreach(_.join())

  println("All threads finished")
}
