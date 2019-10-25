package no.dossier.app.kotlindemo.backend.services

import no.dossier.app.kotlindemo.api.Message
import no.dossier.app.kotlindemo.domain.GameState
import no.dossier.app.kotlindemo.domain.GameVariant
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap

@EnableScheduling
@Service
class GameStateService(
    private val messagingTemplate: SimpMessagingTemplate
) {
    private val games: MutableMap<String, GameState> = ConcurrentHashMap()

    fun createGame(id: String, variant: GameVariant): GameState {
        val width = 50
        val height = 25
        val state = GameState(
            variant = variant,
            running = false,
            width = width,
            height = height,
            data = MutableList(width * height) { 0 }
        )
        games[id] = state
        return state
    }

    fun getGameState(id: String): GameState? = games[id]

    fun startGame(id: String) {
        games.compute(id) { _, state -> state?.copy(running = true) }
    }

    fun stopGame(id: String) {
        games.compute(id) { _, state -> state?.copy(running = false) }
    }

    fun deleteGame(id: String) {
        games.remove(id)
    }

    @Scheduled(fixedDelayString = "PT1S")
    fun updateGames() {
        for (id in games.keys) {
            val newState = games.compute(id) { _, state ->
                if (state?.running == true) {
                    getNextGameState(state)
                } else {
                    state
                }
            }
            newState?.let { publishStateUpdate(id, it) }
        }
    }

    private fun publishStateUpdate(id: String, newState: GameState) {
        messagingTemplate.convertAndSend("/game/$id", Message.GameStateUpdated(newState))
    }

    private fun getNextGameState(state: GameState): GameState {
        return when (state.variant) {
            GameVariant.Basic -> getNextBasicGameState(state)
        }
    }
}

// TODO: Replace dummy impl with actual one
private fun getNextBasicGameState(state: GameState): GameState =
    state.copy(data = state.data.mapIndexed { index, value -> if (value == 0) 1 else 0 })
