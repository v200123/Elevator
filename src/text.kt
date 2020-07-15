import kotlinx.coroutines.*

import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

/**
 *@author LC
 *@createTime 16 21:44
 *@description
 */


@ExperimentalTime
fun main() {


    GlobalScope.launch(Dispatchers.Default) {

        val measureTime = measureTime {
            println("当前所处的线程${Thread.currentThread().name}")
            delay(1000L)
            println("推出234234234234了")

            withContext(Dispatchers.IO){
                println("当前所处的线程${Thread.currentThread().name}")
                delay(1000L)
                println("退出了IO线程")
            }
            withContext(Dispatchers.IO){
                delay(1000L)
                println("退出了IO线程02")
            }

            val async = async(start = CoroutineStart.LAZY) {
                delay(1000L)
                println("推出了1000ms的")
            }
            val async02 = async {
                delay(3000L)
                println("推出了3000ms的")
            }

            async02.await()
            async.await()
            println("我被执行的时间是")
        }

        println("用时是${measureTime}")
    }

        Thread.sleep(8000)

    println("推出了")


}
