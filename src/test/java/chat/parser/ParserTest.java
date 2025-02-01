package chat.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest {
    @Test
    public void parseInputTest() {
        assertEquals(Function.bye, Parser.parseInput("bye").getFunction());
    }

    @Test
    public void parseFileInput_todoTest() {
        assertEquals("[T][ ] test", Parser.parseFileInput(new String[]{"T", "0", "test"}).toString());
    }

    @Test
    public void parseFileInput_deadlineTest() {
        assertEquals("[D][X] test 2 (by: Jan 31 2025, 1436)",
                Parser.parseFileInput(new String[]{"D", "1", "test 2", "31/01/2025 1436"}).toString());
    }

    @Test
    public void parseFileInput_eventTest() {
        assertEquals("[E][X] test 3 (from: Jan 31 2025, 1436 to: Jan 31 2025, 1536)",
                Parser.parseFileInput(new String[]{"E", "1", "test 3",
                        "31/01/2025 1436", "31/01/2025 1536"}).toString());
    }

    @Test
    public void convertToIntTest() {
        assertEquals(1, Parser.convertToInt("1"));
    }

}
