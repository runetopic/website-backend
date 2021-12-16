package com.runetopic

import com.runetopic.login.LoginController
import com.runetopic.registration.RegistrationController
import com.runetopic.tools.npc.NpcController
import com.runetopic.tools.obj.ObjController
import com.runetopic.topics.TopicController
import io.ktor.application.*
import io.ktor.routing.*

/**
 * @author Jordan Abraham
 */
fun Application.configureRouting() {
    routing {
        ObjController(this)
        NpcController(this)
        TopicController(this)
        LoginController(this)
        RegistrationController(this)
    }
}
