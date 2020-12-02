package gr.nikolis.settings.game;

import gr.nikolis.qualifiers.MaxNumber;
import gr.nikolis.qualifiers.MinNumber;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component //this creates the instance. This is like Bean
public class NumberGeneratorImpl implements NumberGenerator {

    // == fields ==
    private final Random random = new Random();
    @Getter
    private final int maxNumber;
    @Getter
    private final int minNumber;

    // == constructors ==
    @Autowired
    public NumberGeneratorImpl(@MaxNumber int maxNumber, @MinNumber int minNumber) {
        this.maxNumber = maxNumber;
        this.minNumber = minNumber;
    }

    // == public methods ==
    @Override
    public int next() {
        return random.nextInt(maxNumber - minNumber) + minNumber;
    }
}
