fun main() {
    print("Enter a number: ")
    val number = readLine()?.toIntOrNull()

    if (number == null || number < 0) {
        println("Please enter a non-negative integer.")
    } else {
        val result = factorial(number)
        println("Factorial of $number is: $result")
    }
}

fun factorial(n: Int): Long {
    return if (n == 0 || n == 1)
        1
    else
        n * factorial(n - 1)
}