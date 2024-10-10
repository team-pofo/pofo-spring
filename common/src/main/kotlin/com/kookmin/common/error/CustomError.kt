package com.kookmin.common.error

class CustomError(errorType: ErrorType): RuntimeException(errorType.message)