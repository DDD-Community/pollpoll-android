package com.ddd.pollpoll

data class Vote(
    val category: CategoryEnum = CategoryEnum.Buy,
    val contents: String = "",
    val milliseconds: Long = 0,
    val multipleChoice: Boolean = false,
    val pollItems: List<PollItem> = listOf(PollItem(""), PollItem("")),
    val title: String = ""
)

data class PollItem(
    val name: String
)

enum class CategoryEnum(val categoryId: Int) {
    Buy(1),
    Carrier(1),
    Carrier2(1),
    Love(1),
    Group(1),
    Worry(1)
}
