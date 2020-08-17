package com.kotsu.malvina.model.rest.mappers

import com.kotsu.malvina.model.rest.response.RequestError
import com.kotsu.malvina.ui.login.domain.classes.LoginError
import com.kotsu.malvina.ui.login.domain.classes.LoginInvalidCredentials


class LoginErrorsMapper {

    companion object {

        @JvmStatic
        fun fromApi(errors: List<RequestError>): List<LoginError> {
            val loginErrors = arrayListOf<LoginError>()

            for (error in errors) {
                when (error.code) {
                    RequestError.ERROR_CODE_INVALID_CREDENTIALS -> {
                        loginErrors.add(LoginInvalidCredentials())
                    }
                }
            }

            return loginErrors
        }
    }
}