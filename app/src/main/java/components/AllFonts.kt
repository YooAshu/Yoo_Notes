package components

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.notes.R

val IndieFlowerFamily = FontFamily(
    Font(R.font.indieflower_regular, FontWeight.Normal)
)

val TangerineFamily = FontFamily(
    Font(R.font.tangerine_regular, FontWeight.Normal),
    Font(R.font.tangerine_bold, FontWeight.Bold)
)

val SilkScreenFamily = FontFamily(
    Font(R.font.silkscreen_regular, FontWeight.Normal),
    Font(R.font.silkscreen_bold, FontWeight.Bold)
)

val RockSaltFamily = FontFamily(
    Font(R.font.rocksalt_regular, FontWeight.Normal)
)

val ReenieBeanieFamily = FontFamily(
    Font(R.font.reeniebeanie_regular, FontWeight.Normal)
)

val PrincessSofiaFamily = FontFamily(
    Font(R.font.princesssofia_regular, FontWeight.Normal)
)
val PlayWriteFamily = FontFamily(
    Font(R.font.playwrite_regular, FontWeight.Normal),
    Font(R.font.playwrite_italic, FontWeight.Normal, FontStyle.Italic)
)

val PlasterFamily = FontFamily(
    Font(R.font.plaster_regular, FontWeight.Normal)
)
val JacquardFamily = FontFamily(
    Font(R.font.jacquard_regular, FontWeight.Normal)
)
val EngagementFamily = FontFamily(
    Font(R.font.engagement_regular, FontWeight.Normal)
)
val BonheurRoyalFamily = FontFamily(
    Font(R.font.bonheurroyale_regular, FontWeight.Normal)
)

val BarriecietoFamily = FontFamily(
    Font(R.font.barriecito_regular, FontWeight.Normal)
)
val AmaticFamily = FontFamily(
    Font(R.font.amatic_regular, FontWeight.Normal),
    Font(R.font.amatic_bold, FontWeight.Bold)
)

data class Fonts(
    var fontName : String,
    var fontFamily: FontFamily,
)

val fontsList = listOf(
    Fonts("Indie Flower", IndieFlowerFamily),
    Fonts("Tangerine", TangerineFamily),
    Fonts("Silk Screen", SilkScreenFamily),
    Fonts("Rock Salt", RockSaltFamily),
    Fonts("Reenie Beanie", ReenieBeanieFamily),
    Fonts("Princess Sofia", PrincessSofiaFamily),
    Fonts("Play Write", PlayWriteFamily),
    Fonts("Plaster", PlasterFamily),
    Fonts("Jacquard", JacquardFamily),
    Fonts("Engagement", EngagementFamily),
    Fonts("Bonheur Royal", BonheurRoyalFamily),
    Fonts("Barriecito", BarriecietoFamily),
    Fonts("Amatic", AmaticFamily)
)


class AllFonts {
}