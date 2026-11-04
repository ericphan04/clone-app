package com.clone.meetup.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.clone.meetup.ui.theme.MeetupShape
import com.clone.meetup.ui.theme.MeetupSizes
import com.clone.meetup.ui.theme.MeetupSpacing
import com.clone.meetup.ui.theme.MeetupTheme
import com.clone.meetup.ui.theme.GraphikMedium

/**
 * Primary CTA. Mirrors @style/Meetup.Button.Important:
 * height 48dp, corner 8dp, padding 16/24, label = Meetup.Text.Body.Medium (14sp Graphik Medium).
 */
@Composable
fun MeetupPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
) {
    val colors = MeetupTheme.colors
    Surface(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(MeetupSizes.touchTarget),
        shape = RoundedCornerShape(MeetupShape.button),
        color = if (enabled) colors.peach else colors.grey5,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            if (icon != null) {
                Icon(icon, contentDescription = null, tint = colors.textInverse, modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(MeetupSpacing.tiny))
            }
            Text(
                text = text,
                color = if (enabled) colors.textInverse else colors.grey1,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

/**
 * Secondary CTA. Mirrors @style/Meetup.Button (backgroundTint mu_color_default_button_grey,
 * text mu_color_default_button_text).
 */
@Composable
fun MeetupSecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val colors = MeetupTheme.colors
    Surface(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier.height(MeetupSizes.touchTarget),
        shape = RoundedCornerShape(MeetupShape.button),
        color = colors.grey6,
    ) {
        Box(Modifier.padding(horizontal = 24.dp), contentAlignment = Alignment.Center) {
            Text(
                text = text,
                color = colors.textPrimary,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
            )
        }
    }
}

/** Text-only action. Mirrors @style/Meetup.Button.Text.Small — 8dp padding, link color, 14sp medium. */
@Composable
fun MeetupTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors
    Surface(onClick = onClick, modifier = modifier, color = Color.Transparent) {
        Box(Modifier.padding(MeetupSpacing.tiny), contentAlignment = Alignment.Center) {
            Text(
                text = text,
                color = colors.viridian,
                style = MaterialTheme.typography.labelLarge,
                fontFamily = GraphikMedium,
            )
        }
    }
}

/**
 * Avatar. Sizes come straight from dimens.xml: tiny 26, small 36, medium 48, large 72.
 * Rounded square (2dp) is the app's default for group marks — circle for members.
 */
@Composable
fun MeetupAvatar(
    url: String?,
    size: Dp = MeetupSizes.avatarMedium,
    modifier: Modifier = Modifier,
    circular: Boolean = true,
) {
    val shape = if (circular) CircleShape else RoundedCornerShape(MeetupShape.icon)
    MeetupImage(
        url = url,
        shape = shape,
        modifier = modifier.size(size),
        seedOffset = 2,
    )
}
/**
 * Image slot with a token-derived gradient behind it.
 *
 * The clone ships no bundled photography (nothing licensed to redistribute), so every image is
 * a remote URL and Coil paints nothing until it resolves — black rectangles on an offline
 * device. The gradient underneath is drawn from palette_viridian / palette_indigo and seeded by
 * the URL hash so repeated slots vary, which keeps the layout legible with or without network.
 * Deliberately silent on failure: the real app shows no error chrome either.
 */
@Composable
fun MeetupImage(
    url: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    shape: Shape = RoundedCornerShape(MeetupShape.button),
    seedOffset: Int = 0,
) {
    val colors = MeetupTheme.colors
    val seed = remember(url, seedOffset) {
        ((url?.hashCode() ?: 0) + seedOffset * 37) and 0x7FFFFFFF
    }
    val stops = remember(seed, colors.viridian, colors.indigo) {
        val liftTop = (seed % 46).toFloat() / 255f
        val liftBottom = (seed % 28).toFloat() / 255f
        listOf(
            colors.viridian.copy(
                red = (colors.viridian.red + liftTop).coerceAtMost(1f),
                green = (colors.viridian.green + liftTop).coerceAtMost(1f),
                blue = (colors.viridian.blue + liftTop).coerceAtMost(1f),
            ),
            colors.indigo.copy(
                red = (colors.indigo.red + liftBottom).coerceAtMost(1f),
                green = (colors.indigo.green + liftBottom).coerceAtMost(1f),
            ),
        )
    }

    Box(
        modifier = modifier
            .clip(shape)
            .background(Brush.linearGradient(stops)),
    ) {
        if (url != null) {
            AsyncImage(
                model = url,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}

/** Overlapping attendance strip — @dimen/overlapping_member_gallery_overlap = -8dp. */
@Composable
fun OverlappingAvatarStrip(
    urls: List<String?>,
    modifier: Modifier = Modifier,
    size: Dp = MeetupSizes.avatarTiny,
) {
    val colors = MeetupTheme.colors
    Row(modifier = modifier) {
        urls.forEachIndexed { index, url ->
            Box(
                modifier = Modifier
                    .offset(x = (-8).dp * index)
                    .size(size)
                    .clip(CircleShape)
                    .background(colors.surface)
                    .padding(1.dp),
            ) {
                MeetupAvatar(url = url, size = size - 2.dp)
            }
        }
    }
}

/** Chip. Mirrors mu_color_search_chip_background / grey5 fill, 8dp radius, 14sp. */
@Composable
fun MeetupChip(
    text: String,
    selected: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(MeetupShape.button),
        color = if (selected) colors.textPrimary else colors.grey6,
        border = if (selected) null else BorderStroke(1.dp, colors.grey5),
    ) {
        Box(
            modifier = Modifier.padding(horizontal = MeetupSpacing.small, vertical = MeetupSpacing.tiny),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = text,
                color = if (selected) colors.textInverse else colors.textPrimary,
                style = MaterialTheme.typography.labelLarge,
                maxLines = 1,
            )
        }
    }
}

/** Section header. "See all" sits on the trailing edge, 14sp medium viridian. */
@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    val colors = MeetupTheme.colors
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MeetupSpacing.normal, vertical = MeetupSpacing.small),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            color = colors.textPrimary,
            modifier = Modifier.weight(1f),
        )
        if (actionLabel != null && onAction != null) {
            MeetupTextButton(text = actionLabel, onClick = onAction)
        }
    }
}

/** 1dp divider, palette_system_grey_5 — the app never uses 2dp or shadows for lists. */
@Composable
fun MeetupDivider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(MeetupShape.divider)
            .background(MeetupTheme.colors.grey5),
    )
}

/** Badge pill — "Live", "Featured", "In 20m". Uses mu_color badge tokens. */
@Composable
fun MeetupBadge(
    text: String,
    modifier: Modifier = Modifier,
    background: Color? = null,
    contentColor: Color? = null,
) {
    val colors = MeetupTheme.colors
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = background ?: colors.lime,
    ) {
        Text(
            text = text,
            color = contentColor ?: colors.textPrimary,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = MeetupSpacing.tiny, vertical = 3.dp),
            maxLines = 1,
        )
    }
}

/** Empty state. Centered, 12sp secondary copy, 80dp vertical breathing room (space_xlarge). */
@Composable
fun EmptyState(
    title: String,
    body: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    val colors = MeetupTheme.colors
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = MeetupSpacing.stripe, vertical = MeetupSpacing.xlarge),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = colors.textPrimary,
        )
        Spacer(Modifier.height(MeetupSpacing.tiny))
        Text(
            text = body,
            style = MaterialTheme.typography.bodySmall,
            color = colors.textSecondary,
        )
        if (actionLabel != null && onAction != null) {
            Spacer(Modifier.height(MeetupSpacing.stripe))
            MeetupPrimaryButton(text = actionLabel, onClick = onAction)
        }
    }
}
