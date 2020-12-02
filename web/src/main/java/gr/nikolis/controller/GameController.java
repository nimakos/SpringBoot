package gr.nikolis.controller;

import gr.nikolis.service.game.GameService;
import gr.nikolis.mappings.game.AttributeNames;
import gr.nikolis.mappings.game.GameMappings;
import gr.nikolis.mappings.game.ViewNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class GameController {

    // == fields ==
    private final GameService gameService;

    // == Constructor ==
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // http://localhost:8888 or http://localhost:8888/game-home
    @GetMapping(GameMappings.GAME_HOME)
    public String home() {
        gameService.reset();
        return ViewNames.GAME_HOME;
    }

    // == request methods ==
    // http://localhost:8888/game-play
    @GetMapping(GameMappings.GAME_PLAY)
    public String play(Model model) {
        model.addAttribute(AttributeNames.MAIN_MESSAGE, gameService.getMainMessage());
        model.addAttribute(AttributeNames.RESULT_MESSAGE, gameService.getResultMessage());

        log.info("model= {}", model);

        if (gameService.isGameOver())
            return ViewNames.GAME_OVER;

        return ViewNames.GAME_PLAY;
    }

    // == on Submit button pres ==
    @PostMapping(GameMappings.GAME_PLAY)
    public String processMessage(@RequestParam int guess) {
        log.info("guess= {}", guess);
        gameService.checkGuess(guess);
        return GameMappings.REDIRECT_PLAY;
    }

    @GetMapping(GameMappings.GAME_OVER)
    public String gameOver(Model model) {
        model.addAttribute(AttributeNames.RESULT_MESSAGE, gameService.getResultMessage());

        return ViewNames.GAME_OVER;
    }

    @GetMapping(GameMappings.RESTART)
    public String restart(Model model) {
        gameService.reset();
        return GameMappings.REDIRECT_PLAY;
    }
}
