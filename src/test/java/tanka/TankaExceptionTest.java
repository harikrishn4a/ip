package tanka;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TankaExceptionTest {

    @Test
    public void getMessage_returnsConstructorMessage() {
        TankaException e = new TankaException("msg");
        assertEquals("msg", e.getMessage());
    }
}
