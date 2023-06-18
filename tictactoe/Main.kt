package tictactoe

fun main() {
    val grid = mutableListOf(mutableListOf<Char>(), mutableListOf(), mutableListOf())
    game(grid)
}

fun readCoordinates(): Pair<Int, Int>? {
    val input = readlnOrNull() ?: return null
    val coordinates = input.split(" ")
    if (coordinates.size != 2) {
        println("Enter two coordinates separated by a space.")
        return null
    }
    val row = coordinates[0].toIntOrNull()
    val column = coordinates[1].toIntOrNull()

    if (row == null || column == null) {
        println("You should enter numbers!")
        return null
    } else if (row !in 1..3 || column !in 1..3) {
        println("Coordinates should be from 1 to 3!")
        return null
    }

    return Pair(row - 1, column - 1)
}

fun drawGrid(grid: List<List<Char>>) {
    println("---------")
    for (row in grid) {
        print("| ")
        for (symbol in row) {
            if (symbol == '_') {
                print("  ")
            } else {
                print("$symbol ")
            }
        }
        println("|")
    }
    println("---------")
}

fun checkWinner(grid: List<List<Char>>): Boolean {
    val winningCombinations = listOf(
        listOf(0, 1, 2),
        listOf(3, 4, 5),
        listOf(6, 7, 8),
        listOf(0, 3, 6),
        listOf(1, 4, 7),
        listOf(2, 5, 8),
        listOf(0, 4, 8),
        listOf(2, 4, 6)
    )

    for (combination in winningCombinations) {
        val symbols = combination.map { (it / 3) to (it % 3) }

        val (row1, column1) = symbols[0]
        val (row2, column2) = symbols[1]
        val (row3, column3) = symbols[2]

        if (grid[row1][column1] == grid[row2][column2] &&
            grid[row2][column2] == grid[row3][column3] &&
            grid[row1][column1] != '_'
        ) {
            return true
        }
    }

    return false
}

fun game(grid: MutableList<MutableList<Char>>) {
    val input = "_____________"
    var countX = 0
    var countO = 0
    var empty = 0
    var index = 0

    for (letter in input) {
        when (letter) {
            'X' -> countX++
            'O' -> countO++
            '_' -> empty++
        }
        grid[index].add(letter)

        if (grid[index].size == 3) {
            index++
            if (index == grid.size) {
                break
            }
        }
    }

    drawGrid(grid)

    fun checkMove() :Boolean{
        while (true) {
            println("Enter the coordinates: ")
            val coordinates = readCoordinates() ?: continue
            val (row, column) = coordinates

            if (grid[row][column] != '_') {
                println("This cell is occupied! Choose another one.")
                continue
            }

            grid[row][column] = 'X'
            drawGrid(grid)
            empty--

            if (checkWinner(grid)) {
                println("X wins")
                return true
            } else if (empty == 0) {
                println("Draw")
                return true
            }

            while (true) {
                val computerCoordinates = readCoordinates() ?: continue
                val (computerRow, computerColumn) = computerCoordinates

                if (grid[computerRow][computerColumn] != '_') {
                    println("This cell is occupied! Choose another one.")
                    continue
                }

                grid[computerRow][computerColumn] = 'O'
                drawGrid(grid)
                empty--

                if (checkWinner(grid)) {
                    println("O wins")
                    return true
                } else if (empty == 0) {
                    println("Draw")
                    return true
                }
                break
            }
        }
    }

    checkMove()
}
