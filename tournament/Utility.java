package tournament;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public abstract class Utility {

    private static final Random RANDOM = new Random();

    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    public static int getRandomInt(final int bound) {
        return Utility.RANDOM.nextInt(bound);
    }

    public static String readStringFromConsole() throws IOException {
        return Utility.READER.readLine();
    }

}
