package put.appsec.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import put.appsec.backend.dto.ErrorResponseDto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserExists(UserAlreadyExistsException ex, WebRequest request) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidToken(InvalidTokenException ex, WebRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler({DisabledException.class, AccountNotEnabledException.class})
    public ResponseEntity<ErrorResponseDto> handleAccountDisabled(Exception ex, WebRequest request) {
        return buildResponse(HttpStatus.FORBIDDEN,
                "Account is disabled. Please check your email and confirm your account.", request);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleBadCredentials(BadCredentialsException ex, WebRequest request) {
        return buildResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password", request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponseDto> handleFileStorage(FileStorageException ex, WebRequest request) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), request);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ErrorResponseDto> handleMaxSize(MaxUploadSizeExceededException ex, WebRequest request) {
        return buildResponse(HttpStatus.PAYLOAD_TOO_LARGE, "File is too large. Please upload a smaller file.", request);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgument(IllegalArgumentException ex, WebRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        String errorMessage = "Validation failed: " + errors;

        return buildResponse(HttpStatus.BAD_REQUEST, errorMessage, request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex, WebRequest request) {
        StringBuilder message = new StringBuilder("Method '");
        message.append(ex.getMethod());
        message.append("' is not supported for this endpoint.");

        if (ex.getSupportedHttpMethods() != null && !ex.getSupportedHttpMethods().isEmpty()) {
            message.append(" Supported methods: ");
            message.append(ex.getSupportedHttpMethods());
        }

        return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, message.toString(), request);
    }


    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponseDto> handleMissingHeader(MissingRequestHeaderException ex, WebRequest request) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Missing required header: " + ex.getHeaderName(), request);
    }

    // Catch all
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred", request);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleNotFound(NoResourceFoundException ex, WebRequest request) {
        return buildResponse(HttpStatus.NOT_FOUND, "The requested endpoint does not exist.", request);
    }

    private ResponseEntity<ErrorResponseDto> buildResponse(HttpStatus status, String message, WebRequest request) {
        ErrorResponseDto response = ErrorResponseDto.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(response, status);
    }
}