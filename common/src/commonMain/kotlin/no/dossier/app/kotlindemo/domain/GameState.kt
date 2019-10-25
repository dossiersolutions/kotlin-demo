package no.dossier.app.kotlindemo.domain

import kotlinx.serialization.Serializable

@Serializable
data class GameState(
    val variant: GameVariant,
    val running: Boolean,
    val width: Int,
    val height: Int,
    val data: List<Int>
)

@Serializable
sealed class GameVariant(val possibleStates: Int) {
    object Basic : GameVariant(2)
}
