package org.pofo.common.error

class CustomError(errorType: ErrorType) : RuntimeException(errorType.message)
