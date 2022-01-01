package com.runetopic.plugins.routing

import com.runetopic.api.tools.shop.ShopService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureShopRouting() {
    val shopService by inject<ShopService>()

    routing {
        get("/api/tools/shops") {
            call.respond(HttpStatusCode.OK, shopService.getShops())
        }
    }
}
