import kotlinx.coroutines.*
import java.lang.IllegalArgumentException
import java.util.*
import javax.xml.bind.JAXBElement

/**
 *@author LC
 *@createTime 15 21:04
 *@description
 */
class elevator {

}

fun main() {
    var nowPosition = 1
    var mCount = 1
    val mFloorsList = arrayListOf<Int>()

    val job = GlobalScope.launch {
        withContext(Dispatchers.IO) {
            println("每秒刷新一次当前楼层")
            while (true) {

                delay(1000)
                if (mFloorsList.isNotEmpty()) {
                    mFloorsList.sort()
                    if (mFloorsList.all { it < nowPosition })
                        nowPosition--
                    else {
                        nowPosition++
                    }
                    println("到达楼层为$nowPosition")
                    mFloorsList.indexOf(nowPosition).apply {
                        if (this < 0) {
                            println("电梯运行中")
                        } else {
                            println("电梯到达")
                            mFloorsList.remove(nowPosition)
                        }
                    }
                    println("轮训已经选中的楼层有${mFloorsList}")
                } else {
                    if (nowPosition > 1) {
                        nowPosition--
                        println("到达楼层为$nowPosition")
                        println("需要返回到1楼")
                    } else {
                        println("当前在1楼")
                    }
                }
            }
        }

    }

    GlobalScope.launch {
        withContext(Dispatchers.IO) {
            while (true) {
                delay(1000)
                if (mFloorsList.isEmpty()) {
                    if (nowPosition == 1)
                        mCount++
                    else {
                        mCount = 0
                    }

                    if (mCount > 5) {
                        println("协程取消")
                        job.cancel()
                    } else {
                        println("协程开始重新启动")
                        if (!job.isActive)
                            job.start()
                    }
                }
                else{
                    println("协程开始重新启动")
                    if (!job.isActive)
                        job.join()
                }
            }
        }

    }


    GlobalScope.launch {
        withContext(Dispatchers.IO) {
            while (true) {
                try {
                    println("选择需要去的楼层")
                    val scanner = Scanner(System.`in`)
                    val next = scanner.nextInt()
//                    println("当前还有其他的输入么${scanner.hasNextInt()}")
                    if (next < 1) {
                        throw IllegalArgumentException("楼层选择错误")
                    }

                    if (mFloorsList.contains(next)) {
                        println("已经选择了该楼层")
                    } else {
                        mFloorsList.add(next)
                        println("选择了${next}楼层")
                        println("已经选中的楼层有${mFloorsList}")
                    }
                } catch (e: IllegalArgumentException) {
                    println("ahagagagaga")
                }

            }
        }

    }

    while (true) {

    }

}