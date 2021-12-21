package com.runetopic.api.user

import com.runetopic.api.AsyncService
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
class UserService : AsyncService<UserStorage>() {
    override fun storage() = inject<UserStorage>().value
}
