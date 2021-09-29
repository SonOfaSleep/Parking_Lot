data class Car (val plateNumber: String = "", val color: String = "", var spot: Int) {
    override fun toString(): String {
        return "$plateNumber $color"
    }
}
data class ParkingLot (var slots: MutableList<Car?>, var status: String = "NotCreated") {
    private lateinit var input: MutableList<String>

    fun menu(){
        input = readLine()!!.split(" ").toMutableList()
        if (input[0].lowercase() != "create" && status != "Created"
            && input[0] != "exit") statusCheck()
        when (input[0].lowercase()) {
            "create" -> createParkSlots(input[1].toInt())
            "park" -> parkCar()
            "leave" -> takeCar()
            "status" -> stats()
            "reg_by_color" -> regByColor()
            "spot_by_color" -> spotByColor()
            "spot_by_reg" -> spotByReg()
            "exit" -> return
            else -> {
                println("No such choice, try another one")
                menu()
            }
        }
    }

    private fun statusCheck() {
        println("Sorry, a parking lot has not been created.")
        menu()
    }

    private fun createParkSlots(n: Int) {
        slots.clear()
        slots.addAll(MutableList<Car?>(n){null})
        status = "Created"
        println("Created a parking lot with $n spots.")
        menu()
    }

    private fun parkCar() {
        var freePlaceIndex = -1
        if (slots.indexOf(null) != -1) {
            freePlaceIndex = slots.indexOf(null)
            slots[slots.indexOf(null)] = Car(input[1], input[2], freePlaceIndex + 1)
            println("${input[2]} car parked in spot ${freePlaceIndex + 1}.")
        } else println("Sorry, the parking lot is full.")
        menu()
    }

    private fun takeCar() {
        if (slots[input[1].toInt() - 1] != null) {
            slots[input[1].toInt() - 1] = null
            println("Spot ${input[1]} is free.")
        } else println("There is no car in spot ${input[1]}.")
        menu()
    }

    private fun stats() {
        if (slots.none{ it != null}) {
            println("Parking lot is empty.")
        } else {
            for (i in slots.indices) {
                if (slots[i] == null) continue
                print("${i + 1} ${slots[i]}")
                println()
            }
        }
        menu()
    }

    private fun regByColor() {
        val filteredSlots = slots.filter { it?.color?.lowercase() == input[1].lowercase() }
        if (filteredSlots.isEmpty()) {
            println("No cars with color ${input[1]} were found.")
        } else {
            println(filteredSlots.map { it?.plateNumber }.joinToString())
        }
        menu()
    }

    private fun spotByColor() {  //spot_by_color
        val filteredSlots = slots.filter { it?.color?.lowercase() == input[1].lowercase() }
        if (filteredSlots.isEmpty()) {
            println("No cars with color ${input[1]} were found.")
        } else {
            println(filteredSlots.map { it?.spot }.joinToString())
        }
        menu()
    }

    private fun spotByReg() {   //spot_by_reg
        val filteredSlots = slots.filter { it?.plateNumber == input[1] }
        if (filteredSlots.isEmpty()) {
            println("No cars with registration number ${input[1]} were found.")
        } else {
            println(filteredSlots.map { it?.spot }.joinToString())
        }
        menu()
    }
}

fun main() {
    val parking = ParkingLot(MutableList(1){null})
    parking.menu()
}