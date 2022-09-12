@file:OptIn(ExperimentalTextApi::class, ExperimentalTextApi::class)

package ir.erfansn.composablescreens.travel.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import ir.erfansn.composablescreens.travel.R

val PoppinsFontFamily = FontFamily(
    Font(
        resId = R.font.poppins_regular,
        weight = FontWeight.Normal,
    ),
    Font(
        resId = R.font.poppins_medium,
        weight = FontWeight.Medium
    ),
    Font(
        resId = R.font.poppins_bold,
        weight = FontWeight.Bold
    ),
    Font(
        resId = R.font.poppins_semi_bold,
        weight = FontWeight.SemiBold
    )
)

val AbrilFatfaceFontFamily = FontFamily(
    Font(
        resId = R.font.abril_fatface_regular,
        weight = FontWeight.Normal
    )
)

val Typography = Typography(
    h5 = TextStyle(
        fontFamily = AbrilFatfaceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    subtitle1 = TextStyle(
        fontFamily = AbrilFatfaceFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    subtitle2 = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    body1 = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    body2 = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    button = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    caption = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.0.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    ),
    overline = TextStyle(
        fontFamily = PoppinsFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 10.sp,
        letterSpacing = 0.sp,
        platformStyle = PlatformTextStyle(includeFontPadding = false)
    )
)