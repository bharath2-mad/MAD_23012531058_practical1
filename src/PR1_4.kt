fun main() {
    print("Enter Number: ")
    var number : Int? = readLine()?.toInt()

    println(when (number?.rem(2)) {
        0 -> "$number is Even"
        else -> "$number is Odd"
    })
}