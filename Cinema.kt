package cinema

val grid = mutableListOf<MutableList<String>>()
var purchasedTickets = 0
var currentIncome = 0

fun main() {
    createGrid()
    var choice = chooseOption()
    while (choice != 0) {
        when (choice) {
            1 -> printGrid()
            2 -> purchaseTicket()
            3 -> showStatistics()
        }
        choice = chooseOption()
    }
}

fun chooseOption(): Int {
    println()
    println(
        """
        1. Show the seats
        2. Buy a ticket
        3. Statistics
        0. Exit
    """.trimIndent()
    )
    return readln().toInt()
}

fun printGrid() {
    println()
    println("Cinema:")
    print("  ")
    for (i in grid.indices) {
        println(grid[i].joinToString(" "))
    }
}

fun createGrid() {
    println("Enter the number of rows:")
    val rows = readln().toInt()
    println("Enter the number of seats in each row:")
    val seatsPerRow = readln().toInt()
    val numbersList = mutableListOf<String>()
    for (i in 1..seatsPerRow) {
        numbersList.add("$i")
    }
    grid.add(numbersList)
    for (j in 1..rows) {
        val seatsList = MutableList(seatsPerRow) { "S" }
        seatsList.add(0, "$j")
        grid.add(seatsList)
    }
}


fun purchaseTicket() {
    println()
    println("Enter a row number:")
    val row = readln().toInt()
    println("Enter a seat number in that row:")
    val seatNumber = readln().toInt()
    try {
        checkAvailability(row, seatNumber)
        val numberOfRows = grid.size - 1
        val numberOfSeatsPRow = grid[0].size
        val totalNumberOfSeats = numberOfRows * numberOfSeatsPRow
        val ticketPrice = if (totalNumberOfSeats <= 60) {
            10
        } else {
            val frontHalf = numberOfRows / 2
            if (row <= frontHalf) {
                10
            } else {
                8
            }
        }
        grid[row][seatNumber] = "B"
        purchasedTickets++
        currentIncome += ticketPrice
        println()
        println("Ticket price: $$ticketPrice")
    } catch (e: ArrayIndexOutOfBoundsException) {
        println()
        println(e.message)
        purchaseTicket()
    } catch (e: Exception) {
        println()
        println(e.message)
        purchaseTicket()
    }

}

fun checkAvailability(row: Int, seat: Int) {
    val rowRange = 1 until grid.size
    val seatRange = 1..grid[0].size
    if (row !in rowRange || seat !in seatRange) {
        throw ArrayIndexOutOfBoundsException("Wrong input!")
    } else if (grid[row][seat] == "B") {
        throw Exception("That ticket has already been purchased!")
    }
}



fun showStatistics() {
    println()
    val rows = grid.size - 1
    val seatsPerRow = grid[0].size
    val totalTickets = (rows) * (seatsPerRow)
    val totalIncome = if (totalTickets <= 60) {
        totalTickets * 10
    } else {
        val frontHalf = rows / 2
        10 * (frontHalf * seatsPerRow) + 8 * ((rows - frontHalf) * seatsPerRow)
    }
    val percentage = "%.2f".format((purchasedTickets / totalTickets.toDouble()) * 100)
    println("""
        Number of purchased tickets: $purchasedTickets
        Percentage: $percentage%
        Current income: $$currentIncome
        Total income: $$totalIncome
    """.trimIndent())
}

