package org.moeum.common.error

class CustomError(errorType: ErrorType): RuntimeException(errorType.message)