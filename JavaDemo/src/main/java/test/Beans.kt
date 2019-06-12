package test
data class Building(
        var floors: List<Floor> = mutableListOf(),
        var id: String = "", // 40dd8fcd-5e6d-4890-b620-88882d9d3977
        var name: String = "" // test
)

data class Floor(
        var name: String = "", // L2
        var mapName: String? = null
)
