package demo
import kotlinx.coroutines.*
import util.XXLog.log
import kotlin.system.*

fun main() = runBlocking {
    // 默认方法
    val time1 = measureTimeMillis {
        val one = doSomethingUsefulOne()
        val two = doSomethingUsefulTwo()
        log("The answer is ${one + two}")
    }
    log("Completed in time1=$time1 ms")

    // async 并发
    val time2 = measureTimeMillis {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        log("The answer is ${one.await() + two.await()}")
    }
    log("Completed in time2=$time2 ms")

    // 延迟启动
    val time3 = measureTimeMillis {
        val one = async(start = CoroutineStart.LAZY) { doSomethingUsefulOne() }
        val two = async(start = CoroutineStart.LAZY) { doSomethingUsefulTwo() }
        // 执行一些计算
        one.start() // 启动第一个
        two.start() // 启动第二个
        log("The answer is ${one.await() + two.await()}")
    }
    log("Completed in time3=$time3 ms")

    // 添加作用域
    val time4 = measureTimeMillis {
        log("The answer is ${concurrentSum()}")
    }
    log("Completed in time4=$time4 ms")
}

suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // 假设我们在这里做了些有用的事
    return 13
}

suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // 假设我们在这里也做了一些有用的事
    return 29
}