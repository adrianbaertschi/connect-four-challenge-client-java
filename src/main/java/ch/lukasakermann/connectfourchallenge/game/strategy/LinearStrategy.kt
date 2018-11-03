package ch.lukasakermann.connectfourchallenge.game.strategy

import ch.lukasakermann.connectfourchallenge.connectFourService.Game
import ch.lukasakermann.connectfourchallenge.game.strategy.ConnectFourStrategy.EMPTY
import org.slf4j.LoggerFactory
import java.util.stream.IntStream
import kotlin.streams.toList

private val log = LoggerFactory.getLogger(LinearStrategy::class.java)


class LinearStrategy : ConnectFourStrategy {
    override fun dropDisc(game: Game?): Int {

        val board = game?.board
        val columnHeads = board!!.cells[0]
        val validMoves = IntStream.range(0, columnHeads.size)
                .boxed()
                .filter { column ->
                    columnHeads[column!!] == EMPTY
                }
                .toList()

        return validMoves.first()
    }
}