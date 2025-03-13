package books.management.global.error;

import books.management.global.error.exception.BusinessException;
import books.management.global.error.response.ErrorCode;
import books.management.global.error.response.ErrorResponse;
import books.management.global.error.response.ErrorResponse.FieldError;
import books.management.global.error.response.GlobalErrorCode;
import java.util.List;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldErrors = FieldError.of(e.getBindingResult());
        return ErrorResponse.of(GlobalErrorCode.METHOD_ARGUMENT_NOT_VALID, fieldErrors);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ErrorResponse handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        return ErrorResponse.of(GlobalErrorCode.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(BusinessException.class)
    protected ErrorResponse handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ErrorResponse.of(errorCode);
    }
}
