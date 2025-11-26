fun main() {
    val list = ArrayList<Int>()

    for (i in 0 until 5) {
        print("a[$i]=")
        val num = readLine()?.toIntOrNull() ?: 0
        list.add(num)
    }

    val max = list.maxOrNull() ?: 0

    println("\nLargest element = $max")
}