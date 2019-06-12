package test

fun main(args: Array<String>) {
    println("Hello World!")
    val list = (0..3).map { it }

    println((0..3).map { it }.toString())
    println((0 until 3).map { it }.toString())

//    val floors = (0..5).map { test.Floor(it.toString()) }
//    val building = test.Building(floors)
//
//    var buildingList = mutableListOf<test.Building>()
//
//    buildingList.add(building)
//
//    val floolsfirst = buildingList.first().floors
//
////    val  flools3 = mutableListOf<test.Floor>()
////    flools3.addAll(floors)
////
//
//
//    Collections.swap(floolsfirst, 0, 1)
//
//    println("floolsfirst $floolsfirst")
//    println("building $building")
//    println("buildingList $buildingList")
//
//    val list = mutableListOf<Int>()
//    list.addAll((0 until 1))
//
//    list.add(3,2)
//    println("list $list")
}