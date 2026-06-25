package dev.vorstu.exceptions;

import java.time.LocalDateTime;

public record ErrorResponse(
        String message,
        LocalDateTime timestamp
) {}
