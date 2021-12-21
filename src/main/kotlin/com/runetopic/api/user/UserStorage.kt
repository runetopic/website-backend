package com.runetopic.api.user

import com.runetopic.api.AsyncStorage
import org.koin.core.component.KoinComponent

class UserStorage : KoinComponent, AsyncStorage<User>()
