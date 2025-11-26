class Car(
    private val type: String,
    private val model: Int,
    private val price: Double,
    private val owner: String,
    private val milesDrive: Int
) {
    init {
        println("Object of class is created and Init is called.\n")
    }

    fun getCarInformation() {
        println("Car Information: $type, $model")
        println("Car Owner: $owner")
        println("Miles Drive: $milesDrive")
    }

    fun getOriginalCarPrice(): Double {
        return price
    }

    fun getCurrentCarPrice(): Double {
        return price - (milesDrive.toDouble() * 5)
    }

    fun displayCarInfo() {
        println("-----------")
        getCarInformation()
        println("Original Car Price: ${getOriginalCarPrice()}")
        println("Current Car Price: ${getCurrentCarPrice()}")
        println("-----------")
    }
}

fun main() {
    println("Creating Car Class Object car1 in next line")
    val car1 = Car("BMW", 2018, 1000000.0, "Aman", 105)
    car1.displayCarInfo()

    println("\nCreating Car Class Object car2 in next line")
    val car2 = Car("BMW", 2019, 400000.0, "Karan", 20)
    car2.displayCarInfo()

    println("\n******* ArrayList of Car **************")
    val carList = arrayListOf(
        Car("Toyota", 2017, 1080000.0, "KJS", 100),
        Car("Maruti", 2020, 4000000.0, "NPP", 200)
    )

    for (car in carList) {
        car.displayCarInfo()
    }
}