package com.sipalingandroid.pbiexam.navigation

sealed class Screen(val route: String) {
    data object Home : Screen("home")
    data object Instruction : Screen("instruction")
    data object Exam : Screen("exam")
    data object About : Screen("about")
}
//data object Home : Screen(
//    route = "home",
//    title = R.string.menu_home,
//    icon = Icons.Default.Home
//)
//data object Instruction : Screen(
//    route = "instruction",
//    title = R.string.menu_instruction,
//    icon = Icons.Default.Home
//)
//data object Exam : Screen(
//    route = "exam",
//    title = null,
//    icon = null
//)
//data object About : Screen(
//    route = "about",
//    title = null,
//    icon = null
//)