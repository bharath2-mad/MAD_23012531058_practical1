fun main() {
    print("Enter first number: ")
    val num1 = readLine()?.toDoubleOrNull() ?: return println("Invalid input")

    print("Enter second number: ")
    val num2 = readLine()?.toDoubleOrNull() ?: return println("Invalid input")

    performOperations(num1, num2)
}

fun performOperations(a: Double, b: Double) {
    println("Addition: ${a + b}")
    println("Subtraction: ${a - b}")
    println("Multiplication: ${a * b}")
    if (b != 0.0) {
        println("Division: ${a / b}")
    } else {
        println("Division: Cannot divide by zero")
    }
}