package ir.erfansn.composablescreens.travel.qclay_trip.details

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import ir.erfansn.composablescreens.travel.qclay_trip.R
import ir.erfansn.composablescreens.travel.qclay_trip.ui.components.QclayTripButton
import ir.erfansn.composablescreens.travel.qclay_trip.ui.components.QclayTripIconButton
import ir.erfansn.composablescreens.travel.qclay_trip.ui.components.layout.OverlappingRow
import ir.erfansn.composablescreens.travel.qclay_trip.ui.theme.PoppinsFontFamily
import ir.erfansn.composablescreens.travel.qclay_trip.ui.theme.QclayTripTheme

@Composable
fun DetailsRoute() {
    DetailsScreen()
}

@Composable
private fun DetailsScreen() {
    val baseModifier = Modifier.padding(horizontal = 24.dp)
    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .fillMaxSize()
            .safeDrawingPadding(),
        backgroundColor = MaterialTheme.colors.surface,
        topBar = {
            DetailsTopBar(
                modifier = baseModifier
            )
        },
        bottomBar = {
            DetailsBottomNavigationBar(
                modifier = baseModifier
            )
        }
    ) { contentPadding ->
        DetailsContent(
            modifier = Modifier
                .fillMaxHeight()
                .padding(contentPadding)
                .then(baseModifier)
        )
    }
}

@Composable
private fun DetailsTopBar(modifier: Modifier = Modifier) {
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
        QclayTripIconButton(
            onClick = { /*TODO*/ },
            containerColor = Color.White,
            shadowColor = Color.Gray,
            iconTint = Color.Black,
            iconId = R.drawable.ic_category,
            contentDescription = "Menu"
        )
        Text(
            text = "Group Travel",
            style = MaterialTheme.typography.subtitle1.copy(
                fontWeight = FontWeight.SemiBold,
                fontFamily = PoppinsFontFamily
            )
        )
        QclayTripButton(
            onClick = { /*TODO*/ }
        ) {
            Image(
                modifier = Modifier.matchParentSize(),
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "User profile",
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun DetailsBottomNavigationBar(
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(vertical = 24.dp)
            .fillMaxWidth()
            .height(64.dp)
    ) {
        QclayTripButton(
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
        QclayTripButton(
            modifier = Modifier
                .weight(0.60f)
                .fillMaxHeight(),
            containerColor = Color.Black,
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
private fun DetailsContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Box(modifier = Modifier.wrapContentWidth()) {
            val hazeState = remember { HazeState() }
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(10 / 9f)
                    .clip(MaterialTheme.shapes.medium)
                    .haze(hazeState),
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
                        .size(96.dp, 48.dp)
                        .clip(RoundedCornerShape(33))
                        .border(Dp.Hairline, color = Color(0x50FFFFFF), shape = RoundedCornerShape(33))
                        .hazeChild(
                            state = hazeState,
                            style = HazeDefaults.style(
                                backgroundColor = Color.Transparent,
                                blurRadius = 6.dp,
                            ),
                        )
                        .background(
                            Brush.horizontalGradient(
                                0.0f to Color.Transparent,
                                1.0f to Color.White.copy(alpha = 0.25f),
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row {
                        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colors.surface) {
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
                                style = MaterialTheme.typography.caption,
                            )
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            QclayTripButton(
                modifier = Modifier
                    .size(104.dp, 42.dp),
                shape = RoundedCornerShape(33),
                shadowColor = MaterialTheme.colors.primaryVariant,
                containerColor = MaterialTheme.colors.primaryVariant,
                onClick = { /*TODO*/ }
            ) {
                Text(
                    text = "Details",
                    style = MaterialTheme.typography.caption.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            QclayTripButton(
                modifier = Modifier.size(104.dp, 42.dp),
                shape = RoundedCornerShape(33),
                containerColor = Color.Transparent,
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
            DetailLabelsRow(
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
                QclayTripButton(
                    modifier = Modifier
                        .aspectRatio(1.0f),
                    containerColor = MaterialTheme.colors.primaryVariant,
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
private fun DetailLabelsRow(
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
            DetailLabel(
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
private fun DetailLabel(
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

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    QclayTripTheme {
        DetailsScreen()
    }
}