class Matrix(private val matrix: Array<IntArray>) {

    private val noOfRows = matrix.size
    private val noOfCols = matrix[0].size

    operator fun plus(other: Matrix): Matrix {
        val result = Array(noOfRows) { IntArray(noOfCols) }
        for (i in 0 until noOfRows) {
            for (j in 0 until noOfCols) {
                result[i][j] = matrix[i][j] + other.matrix[i][j]
            }
        }
        return Matrix(result)
    }
    operator fun minus(other: Matrix): Matrix {
        val result = Array(noOfRows) { IntArray(noOfCols) }
        for (i in 0 until noOfRows) {
            for (j in 0 until noOfCols) {
                result[i][j] = matrix[i][j] - other.matrix[i][j]
            }
        }
        return Matrix(result)
    }
    operator fun times(other: Matrix): Matrix {
        val result = Array(noOfRows) { IntArray(other.noOfCols) }
        for (i in 0 until noOfRows) {
            for (j in 0 until other.noOfCols) {
                for (k in 0 until noOfCols) {
                    result[i][j] += matrix[i][k] * other.matrix[k][j]
                }
            }
        }
        return Matrix(result)
    }
    override fun toString(): String {
        val builder = StringBuilder()
        for (row in matrix) {
            builder.append(row.joinToString(" ", prefix = "[", postfix = "]\n"))
        }
        return builder.toString()
    }
}

fun main() {
    val firstMatrix = Matrix(arrayOf(
        intArrayOf(3, -2, 5),
        intArrayOf(3, 0, 4)
    ))

    val secondMatrix1 = Matrix(arrayOf(
        intArrayOf(2, 3, 0),
        intArrayOf(-9, 0, 4)
    ))

    val secondMatrix = Matrix(arrayOf(
        intArrayOf(6, 3),
        intArrayOf(9, 0),
        intArrayOf(5, 4)
    ))

    println("***************Addition***************")
    println("Matrix 1:")
    print(secondMatrix1)
    println("Matrix 2:")
    print(secondMatrix1)
    val addResult = secondMatrix1 + secondMatrix1
    println("Addition:\n$addResult")

    println("***************Subtraction***************")
    println("Matrix 1:")
    print(secondMatrix1)
    println("Matrix 2:")
    print(secondMatrix1)
    val subtractResult = secondMatrix1 - secondMatrix1
    println("Subtraction:\n$subtractResult")

    println("***************Multiplication***************")
    println("Matrix 1:")
    print(firstMatrix)
    println("Matrix 2:")
    print(secondMatrix)
    val multiplyResult = firstMatrix * secondMatrix
    println("Multiplication:\n$multiplyResult")
}