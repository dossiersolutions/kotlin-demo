package no.dossier.app.kotlindemo.domain

data class GameState(
    val variant: GameVariant,
    val width: Int,
    val height: Int,
    val data: List<Int>
)

enum class GameVariant {
    Basic
}
