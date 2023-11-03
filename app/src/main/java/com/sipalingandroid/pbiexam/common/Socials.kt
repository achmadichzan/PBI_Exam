package com.sipalingandroid.pbiexam.common

import com.sipalingandroid.pbiexam.R

data class Socials(
    val icon: Int,
    val name: Int
)

val listSocials = listOf(
    Socials(R.drawable.linkedin, R.string.linkedin),
    Socials(R.drawable.instagram, R.string.instagram),
    Socials(R.drawable.github, R.string.github),
    Socials(R.drawable.solos, R.string.solos),
)