package com.runetopic.plugins.routing

import com.runetopic.Authentications
import com.runetopic.api.tools.npc.NPCSpawns189
import com.runetopic.api.tools.npc.Npc
import com.runetopic.api.tools.npc.NpcService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.coroutines.reactive.collect
import org.koin.ktor.ext.inject
import org.litote.kmongo.json
import org.reactivestreams.Publisher
import java.util.concurrent.ConcurrentLinkedQueue

fun Application.configureNpcRouting() {
    val npcService by inject<NpcService>()

    routing {
        get("/api/tools/npcs/spawns") {
            when (val revision = call.request.queryParameters["revision"]?.toInt() ?: "") {
                189 -> call.respond(npcService.getNPCSpawns189())
                "" -> call.respond(HttpStatusCode.BadRequest, "Revision must be provided.")
                else -> call.respond(HttpStatusCode.OK, "Revision $revision is not supported yet :( \n  Coming soon 2090™")
            }
        }
        authenticate(Authentications.LOGGED_IN) {
            get("/api/tools/npcs") {
                val sorted = call.request.queryParameters["sorted"] == true.toString()
                when {
                    sorted -> call.respond(npcService.findSorted(Npc::id))
                    else -> call.respond(npcService.find<Npc>())
                }
            }
            post("/api/tools/npcs") {
                if (npcService.add(call.receive<Npc>())) call.respond(HttpStatusCode.Created)
            }
        }
    }
}
