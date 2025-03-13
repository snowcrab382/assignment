package books.management.global.error.exception;

import books.management.global.error.response.ErrorCode;

public class NonUniqueValueException extends BusinessException {

    public NonUniqueValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
