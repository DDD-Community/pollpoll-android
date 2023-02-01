package com.ddd.pollpoll

data class Vote(
    val category: CategoryEnum = CategoryEnum.Buy,
    val contents: String = "",
    val milliseconds: Int = 0,
    val multipleChoice: Boolean = false,
    val pollItems: List<PollItem> = listOf(PollItem(""), PollItem("")),
    val title: String = ""
)

data class PollItem(
    val name: String
)

enum class CategoryEnum(val categoryId: Int) {
    Buy(0),
    Carrier(1),
    Carrier2(2),
    Love(3),
    Group(4),
    Worry(5)
}
