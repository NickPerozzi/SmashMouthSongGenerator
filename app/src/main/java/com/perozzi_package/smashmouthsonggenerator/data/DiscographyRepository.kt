package com.perozzi_package.smashmouthsonggenerator.data

import com.perozzi_package.smashmouthsonggenerator.R

class DiscographyRepository {

    val smashMouthDiscography: MutableMap<String, Map<String, Any>> = mutableMapOf(
        "fush_yu_mang" to mapOf(
            "name" to "Fush Yu Mang",
            "year" to "(1997)",
            "imageAddress" to R.drawable.alb_1_fush_yu_mang_97
        ),
        "astro_lounge" to mapOf(
            "name" to "Astro Lounge",
            "year" to "(1999)",
            "imageAddress" to R.drawable.alb_2_astro_lounge_99
        ),
        "smash_mouth" to mapOf(
            "name" to "Smash Mouth",
            "year" to "(2001)",
            "imageAddress" to R.drawable.alb_3_smash_mouth_01
        ),
        "get_the_picture" to mapOf(
            "name" to "Get the Picture",
            "year" to "(2003)",
            "imageAddress" to R.drawable.alb_4_get_the_picture_03
        ),
        "all_star_smash_hits" to mapOf(
            "name" to "All Star Smash Hits",
            "year" to "(2005)",
            "imageAddress" to R.drawable.alb_5_all_star_smash_hits_05
        ),
        "the_gift_of_rock" to mapOf(
            "name" to "The Gift of Rock",
            "year" to "(2005)",
            "imageAddress" to R.drawable.alb_6_the_gift_of_rock_05
        ),
        "summer_girl" to mapOf(
            "name" to "Summer Girl",
            "year" to "(2006)",
            "imageAddress" to R.drawable.alb_7_summer_girl_06
        ),
        "magic" to mapOf(
            "name" to "Magic",
            "year" to "(2012)",
            "imageAddress" to R.drawable.alb_8_magic_12
        )
    )

}