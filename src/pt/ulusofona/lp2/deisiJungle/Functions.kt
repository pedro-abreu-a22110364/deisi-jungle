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
        "MOST_TRAVELED" -> return getMostTraveled(game,list)
        "TOP_ENERGETIC_OMNIVORES" -> return getTopEnergeticOmnivores(game,list)
        "CONSUMED_FOODS" -> return getConsumedFoods(game,list)
    }
    return null
}

fun commandPost (game : GameManager, list : List<String>) : String? {
    when (list.get(0)) {
        "MOVE" -> return move(game,list)
    }
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

fun getMostTraveled(game : GameManager, list : List<String>): String? {
    var string = ""
    val players = game.getAlPlayer().sortedWith{i1,i2 -> i2.getDistance() - i1.getDistance()}.forEach{string += it.getName() + ":" + it.getSpecie().getIdentifier() + ":" + it.getDistance() + "\n"}

    string += "Total:" + game.getDistanceTotal()

    return string
}

fun getTopEnergeticOmnivores(game : GameManager, list : List<String>): String? {
    var string = ""
    val players = game.getAlPlayer().filter { it.getEnergy() != 0 }.sortedWith{i1,i2 -> i2.getEnergy() - i1.getEnergy()}.take(list.get(1).toInt()).forEach{string += it.getName() + ":" + it.getEnergy() + "\n"}

    string.substring(string.length - 2,string.length -1)

    return string
}

fun getConsumedFoods(game : GameManager, list : List<String>): String? {
    var string = ""
    var listaTemp = listOf<String>()
    val players = game.getAlPlayer().forEach{ it.getEatenFoods().forEach { listaTemp += it.getNome() }}

    listaTemp.sortedWith{s1,s2 -> s1.compareTo(s2)}.forEach{string += it + "\n"}

    string.substring(string.length - 2,string.length -1)

    return string
}

fun move(game : GameManager, list : List<String>): String? {
    return ""
}

fun main() {
    val routerFn = router()
    val commandGetFn = routerFn.invoke(CommandType.GET)
    val result = commandGetFn.invoke(GameManager(), listOf("MOST_TRAVELED", "Pedro"))
    print(result)
}