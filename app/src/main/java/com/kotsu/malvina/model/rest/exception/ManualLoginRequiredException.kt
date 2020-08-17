package com.kotsu.malvina.model.rest.exception

import java.lang.RuntimeException


class ManualLoginRequiredException(message: String) : RuntimeException(message) {
}