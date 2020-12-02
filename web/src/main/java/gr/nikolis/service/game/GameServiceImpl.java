package gr.nikolis.service.game;

import gr.nikolis.settings.game.Game;
import gr.nikolis.settings.game.MessageGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service //this creates the instance (Bean)
public class GameServiceImpl implements GameService {

    private final Game game;
    private final MessageGenerator messageGenerator;

    @Autowired
    public GameServiceImpl(Game game, MessageGenerator messageGenerator){
        this.game = game;
        this.messageGenerator = messageGenerator;
    }

    // == init ==
    @PostConstruct
    public void init() {
        log.info(" number = {}", game.getNumber());
        log.info("mainMessage = {}", messageGenerator.getMainMessage());
    }

    @Override
    public boolean isGameOver() {
        return game.isGameLost() || game.isGameWon();
    }

    @Override
    public String getMainMessage() {
        return messageGenerator.getMainMessage();
    }

    @Override
    public String getResultMessage() {
        return messageGenerator.getResultMessage();
    }

    @Override
    public void checkGuess(int guess) {
        game.setGuess(guess);
        game.check();
    }

    @Override
    public void reset() {
        game.reset();
    }
}
