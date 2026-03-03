package br.com.mardoniorodrigues.ordering.domain.model.exception;

import static br.com.mardoniorodrigues.ordering.domain.model.exception.ErrorMessages.ERROR_CUSTOMER_ARCHIVED;

public class CustomerArchivedException extends RuntimeException{

    public CustomerArchivedException() {
        super(ERROR_CUSTOMER_ARCHIVED);
    }

    public CustomerArchivedException(Throwable cause) {
        super(ERROR_CUSTOMER_ARCHIVED, cause);
    }
}
