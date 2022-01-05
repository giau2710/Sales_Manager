package exception;

import java.io.IOException;

public class ExitFileException extends IOException {
    public ExitFileException(IOException e) {
        super(e);
    }
}
