package util

object XXLog {
    fun log(msg: String) {
        println("[${Thread.currentThread().name}]: $msg")
    }
}