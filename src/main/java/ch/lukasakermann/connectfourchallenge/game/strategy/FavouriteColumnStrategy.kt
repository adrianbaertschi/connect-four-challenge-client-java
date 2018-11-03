package ch.lukasakermann.connectfourchallenge.game.strategy

import ch.lukasakermann.connectfourchallenge.connectFourService.Game
import ch.lukasakermann.connectfourchallenge.game.strategy.ConnectFourStrategy.EMPTY
import org.slf4j.LoggerFactory
import java.util.*
import java.util.stream.IntStream
import kotlin.streams.toList

private val log = LoggerFactory.getLogger(FavouriteColumnStrategy::class.java)

var fav = -1
var deadCols = mutableSetOf<Int>()

class FavouriteColumnStrategy : ConnectFourStrategy {
    override fun dropDisc(game: Game?): Int {


        val board = game?.board!!
        val columnHeads = board.cells[0]
        val validMoves = IntStream.range(0, columnHeads.size)
                .boxed()
                .filter { column ->
                    columnHeads[column!!] == EMPTY
                }
                .toList()

        val opponentColor = game.players.first { p -> p.playerId != game.currentPlayerId }.disc
        val myColor = game.players.first { p -> p.playerId == game.currentPlayerId }.disc

        // Col Check My
        for (row in 0 until board.rowCount - 2) {
            for (column in 0 until board.columnCount) {
                if (board.getCell(column, row) == myColor
                        && board.getCell(column, row + 1) == myColor
                        && board.getCell(column, row + 2) == myColor) {
                    log.info("3 in Col : {}", column)

                    if (validMoves.contains(column)) {
                        log.info("Try to win: {} ", column)
                        return column
                    }
                }
            }
        }

        // Col Check Gegner
        for (row in 0 until board.rowCount - 2) {
            for (column in 0 until board.columnCount) {
                if (board.getCell(column, row) == opponentColor
                        && board.getCell(column, row + 1) == opponentColor
                        && board.getCell(column, row + 2) == opponentColor) {
                    log.info("3 in Col : {}", column)

                    if (validMoves.contains(column) && !deadCols.contains(column)) {
                        if (row >= 3) {
                            deadCols.add(column)
                        }
                        log.info("Avert loss: {} ", column)
                        return column
                    }
                }
            }
        }



        if (!validMoves.contains(fav)) {
            fav = validMoves[Random().nextInt(validMoves.size)]
            log.info("New random fav, {} ", fav)
        }

        log.info("Play {}", fav)
        return fav
    }

    override fun win(game: Game?) {
        reset()
    }

    override fun loose(game: Game?) {
        reset()
    }

    override fun draw(game: Game?) {
        reset()
    }

    private fun reset() {
        deadCols.clear()
        fav = -1
    }
}
