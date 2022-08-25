package dev.logickoder.synote.data.remote

object HttpRoutes {
    private const val BASE_URL = "https://demo.logad.net/synote/api"
    const val LOGIN = "$BASE_URL/login"
    const val REGISTER = "$BASE_URL/register"
    const val GET_NOTES = "$BASE_URL/get-notes"
}