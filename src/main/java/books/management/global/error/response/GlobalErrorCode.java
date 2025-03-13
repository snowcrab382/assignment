package books.management.global.error.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GlobalErrorCode implements ErrorCode {

    INTERNAL_SERVER_ERROR(500, "서버에서 핸들링되지 않은 에러가 발생하였습니다."),
    METHOD_ARGUMENT_NOT_VALID(400, "요청 값이 유효하지 않습니다."),
    METHOD_NOT_ALLOWED(405, "허용되지 않은 메소드입니다."),

    BOOK_ISBN_DUPLICATION(400, "이미 존재하는 ISBN입니다."),
    ;

    private final int status;
    private final String message;

}
