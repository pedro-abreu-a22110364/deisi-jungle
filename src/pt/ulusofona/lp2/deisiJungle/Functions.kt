package pt.ulusofona.lp2.deisiJungle

enum class CommandType {GET,POST}

fun router () : Function1<CommandType,Function2<GameManager,List<String>,String?>> {
    return ::command
}

fun command (command: CommandType) : Function2<GameManager,List<String>,String?> {
    when (command) {
        CommandType.POST -> return ::commandPost
        CommandType.GET -> return  ::commandGet
    }
}

fun commandGet (game : GameManager, list : List<String>) : String? {
    when (list.get(0)) {
        "PLAYER_INFO" -> return getPlayerInfo(game,list)
        "PLAYERS_BY_SPECIE" -> return getPlayerBySpecie(game,list)
    }
    return null
}

fun commandPost (game : GameManager, list : List<String>) : String? {
    return null
}

fun getPlayerInfo(game : GameManager, list : List<String>): String? {
    val players = game.getAlPlayer().filter { it.name == list.get(1) }

    if (players.isEmpty()) {
        return "Inexistent player"
    }

    return players.get(0).getIdentifier().toString() + " | " +
            players.get(0).getName() + " | " +
            players.get(0).getSpecie().getName() + " | " +
            players.get(0).getEnergy() + " | " +
            players.get(0).getPosition()
}

fun getPlayerBySpecie(game : GameManager, list : List<String>): String? {
    val players = game.getAlPlayer().filter { it.specie.identifier.toString() == list.get(1) }.sortedWith{s1,s2 -> s2.getName().compareTo(s1.getName())}

    if (players.isEmpty()) {
        return ""
    }

    return players.joinToString(",")
}

fun main() {
    val routerFn = router()
    val commandGetFn = routerFn.invoke(CommandType.GET)
    val result = commandGetFn.invoke(GameManager(), listOf("PLAYER_INFO", "Pedro"))
    print(result)
}