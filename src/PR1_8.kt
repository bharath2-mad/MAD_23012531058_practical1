fun main() {
    val array1 = arrayOf(10, 90, 60, 80, 100)
    println("Create Array-1 by using arrayOf() method:")
    println(array1.joinToString(", "))

    val array2 = Array(5) { 0 }
    println("\nCreate Array-2 by using Array<>():")
    println(array2.joinToString(", "))

    val array3 = Array(7) { it }
    println("\nCreate Array-3 by using Array<>() and lambda function:")
    println(array3.joinToString(", "))

    val array4 = IntArray(5)
    println("\nCreate Array-4 by using IntArray():")
    println(array4.joinToString(", "))

    val array5 = intArrayOf(12, 10, 1, 5, 18, 19)
    println("\nCreate Array-5 by using intArrayOf():")
    println(array5.joinToString(", "))

    val array6 = arrayOf(
        intArrayOf(1, 3),
        intArrayOf(4, 5),
        intArrayOf(6, 7)
    )
    println("\nCreate 2D Array-6 by using arrayOf() and intArrayOf():")
    for (row in array6) {
        println(row.joinToString(", "))
    }

    val a = IntArray(5)
    println("\nPlease enter Array Value:")
    for (i in a.indices) {
        print("a[$i]=")
        a[i] = readLine()?.toIntOrNull() ?: 0
    }

    println("\nEntered Array:")
    println(a.joinToString(", "))

    println("\n***************With Built-in Function***************")
    val builtInSorted = a.sorted()
    println("After sorting by built-in function:")
    println(builtInSorted.joinToString(", "))

    println("\n***************Without Built-in Function***************")
    println("Before Sorting:")
    println(a.joinToString(", "))

    // Bubble sort without built-in
    for (i in 0 until a.size - 1) {
        for (j in 0 until a.size - i - 1) {
            if (a[j] > a[j + 1]) {
                val temp = a[j]
                a[j] = a[j + 1]
                a[j + 1] = temp
            }
        }
    }

    println("\nAfter Sorting without built-in function:")
    println(a.joinToString(", "))
}