package com.kotsu.malvina.model.rest.mappers

import com.kotsu.malvina.model.data.recipients.Recipient
import com.kotsu.malvina.model.rest.response.classes.ApiRecipient


class RecipientMapper {

    companion object {

        @JvmStatic
        fun fromApi(apiRecipient: ApiRecipient, commentary: String) : Recipient {
            return Recipient(
                apiRecipient.name,
                apiRecipient.phoneFormatted,
                apiRecipient.address,
                commentary
            )
        }
    }
}