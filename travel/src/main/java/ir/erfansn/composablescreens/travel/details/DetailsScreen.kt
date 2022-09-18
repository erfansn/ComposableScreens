package ir.erfansn.composablescreens.travel.details

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.erfansn.composablescreens.travel.R
import ir.erfansn.composablescreens.travel.ui.components.TravelButton
import ir.erfansn.composablescreens.travel.ui.components.layout.OverlappingRow
import ir.erfansn.composablescreens.travel.ui.components.modifier.shadow
import ir.erfansn.composablescreens.travel.ui.theme.PoppinsFontFamily
import ir.erfansn.composablescreens.travel.ui.theme.TravelTheme

@Composable
fun TravelDetailsRoute() {
    TravelTheme {
        TravelDetailsScreen()
    }
}

@Composable
fun TravelDetailsScreen() {
    val baseModifier = Modifier.padding(horizontal = 24.dp)
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxSize()
            .systemGesturesPadding()
            .systemBarsPadding(),
        backgroundColor = MaterialTheme.colors.surface,
        topBar = {
            TravelDetailsTopBar(
                modifier = baseModifier
            )
        },
        bottomBar = {
            TravelDetailsBottomNavigationBar(
                modifier = baseModifier
            )
        }
    ) { contentPadding ->
        TravelDetailsContent(
            modifier = Modifier
                .fillMaxHeight()
                .padding(contentPadding)
                .then(baseModifier)
        )
    }
}

@Composable
fun TravelDetailsContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(modifier = Modifier.wrapContentWidth()) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(10 / 9f)
                    .clip(MaterialTheme.shapes.medium),
                painter = painterResource(id = R.drawable.the_wave),
                contentDescription = null,
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp)
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "The Wave\nArizona",
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = PoppinsFontFamily,
                        color = Color.White
                    )
                )
                Box(
                    modifier = Modifier
                        .size(86.dp, 42.dp)
                        .clip(RoundedCornerShape(33))
                        .background(MaterialTheme.colors.background),
                    contentAlignment = Alignment.Center
                ) {
                    Row {
                        val height = with(LocalDensity.current) { 14.sp.toDp() }
                        Icon(
                            modifier = Modifier
                                .height(height)
                                .align(Alignment.CenterVertically),
                            painter = painterResource(id = R.drawable.ic_location),
                            contentDescription = "Location"
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(
                            text = "USA",
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            TravelButton(
                modifier = Modifier
                    .shadow(
                        shape = RoundedCornerShape(33),
                        color = MaterialTheme.colors.primaryVariant.copy(alpha = 0.5f),
                        radius = 24.dp,
                        dx = 6.dp,
                        dy = 6.dp
                    )
                    .size(104.dp, 42.dp),
                shape = RoundedCornerShape(33),
                backgroundColor = MaterialTheme.colors.primaryVariant,
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = "Details",
                    style = MaterialTheme.typography.caption.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            TextButton(
                modifier = Modifier.size(104.dp, 42.dp),
                shape = RoundedCornerShape(33),
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colors.onBackground
                ),
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = "Reviews",
                    style = MaterialTheme.typography.caption.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TravelDetailLabelsRow(
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                modifier = Modifier.alpha(ContentAlpha.medium),
                text = stringResource(id = R.string.travel_group_detail_text),
                style = MaterialTheme.typography.body2
            )
            Row(
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.background,
                        shape = RoundedCornerShape(20)
                    )
                    .height(70.dp)
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OverlappingRow {
                        val profileImageIds = listOf(
                            R.drawable.person_one,
                            R.drawable.person_two,
                            R.drawable.person_three
                        )
                        profileImageIds.forEach {
                            Image(
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .background(
                                        color = MaterialTheme.colors.background,
                                        shape = CircleShape
                                    )
                                    .padding(2.dp)
                                    .clip(CircleShape),
                                painter = painterResource(it),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "20+ Trip Members",
                        style = MaterialTheme.typography.overline
                    )
                }
                TravelButton(
                    modifier = Modifier
                        .aspectRatio(1.0f),
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(30),
                    onClick = { /*TODO*/ }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow),
                        contentDescription = "See"
                    )
                }
            }
        }
    }
}

@Composable
fun TravelDetailsTopBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                top = 16.dp,
                bottom = 24.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TravelButton(
            modifier = Modifier
                .shadow(
                    color = Color(0xFFCCCCCC).copy(alpha = 0.7f),
                    shape = RoundedCornerShape(18.dp),
                    radius = 24.dp,
                    dx = 10.dp,
                    dy = 10.dp
                )
                .size(56.dp),
            onClick = { /*TODO*/ },
            backgroundColor = Color.White
        ) {
            Icon(
                tint = Color.Black,
                painter = painterResource(id = R.drawable.ic_category),
                contentDescription = "Menu"
            )
        }
        Text(
            text = "Group Travel",
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = PoppinsFontFamily
            )
        )
        TravelButton(
            modifier = Modifier.size(56.dp),
            onClick = { /*TODO*/ }
        ) {
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "User profile",
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun TravelDetailsBottomNavigationBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(vertical = 24.dp)
            .fillMaxWidth()
            .height(64.dp)
    ) {
        TravelButton(
            modifier = Modifier
                .weight(0.30f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(20),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "$270",
                style = MaterialTheme.typography.caption.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        TravelButton(
            modifier = Modifier
                .weight(0.60f)
                .fillMaxHeight(),
            backgroundColor = Color.Black,
            shape = RoundedCornerShape(20),
            onClick = { /*TODO*/ }
        ) {
            Text(
                text = "Book Now",
                color = Color.White,
                style = MaterialTheme.typography.caption.copy(
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}

@Composable
fun TravelDetailLabelsRow(
    modifier: Modifier = Modifier,
    detailLabels: List<DetailLabel> = remember {
        listOf(
            DetailLabel(
                iconId = R.drawable.ic_time_square,
                title = "Trip Duration",
                content = "6 Days"
            ),
            DetailLabel(
                iconId = R.drawable.ic_star,
                title = "Ratings",
                content = "4.6"
            )
        )
    },
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        detailLabels.forEach {
            TravelDetailLabel(
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .weight(1.0f),
                detailLabel = it
            )
        }
    }
}

@Composable
fun TravelDetailLabel(
    modifier: Modifier = Modifier,
    detailLabel: DetailLabel,
) {
    Row(
        modifier = modifier,
    ) {
        Box(
            modifier = Modifier
                .aspectRatio(1.0f)
                .background(
                    color = MaterialTheme.colors.background,
                    shape = RoundedCornerShape(20)
                ),
            contentAlignment = Alignment.Center
        ) {
            val brush = Brush.linearGradient(
                0.0f to MaterialTheme.colors.primary,
                1.0f to MaterialTheme.colors.primaryVariant
                    .copy(alpha = 0.6f)
                    .compositeOver(MaterialTheme.colors.primary),
            )
            Icon(
                modifier = Modifier
                    .graphicsLayer { alpha = 0.99f }
                    .drawWithContent {
                        drawContent()
                        drawRect(
                            brush = brush,
                            blendMode = BlendMode.SrcAtop
                        )
                    },
                painter = painterResource(id = detailLabel.iconId),
                contentDescription = null
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.alpha(ContentAlpha.disabled),
                text = detailLabel.title,
                style = MaterialTheme.typography.overline
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = detailLabel.content,
                style = MaterialTheme.typography.subtitle2.copy(
                    fontWeight = FontWeight.Bold,
                    fontFamily = PoppinsFontFamily,
                )
            )
        }
    }
}

data class DetailLabel(
    @DrawableRes val iconId: Int,
    val title: String,
    val content: String,
)

@Preview(
    showBackground = true,
    device = Devices.PHONE,
)
@Composable
fun DetailsScreenPreview() {
    TravelTheme {
        TravelDetailsScreen()
    }
}