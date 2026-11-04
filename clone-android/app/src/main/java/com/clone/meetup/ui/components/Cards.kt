package com.clone.meetup.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.clone.meetup.data.Event
import com.clone.meetup.data.Group
import com.clone.meetup.ui.theme.MeetupShape
import com.clone.meetup.ui.theme.MeetupSizes
import com.clone.meetup.ui.theme.MeetupSpacing
import com.clone.meetup.ui.theme.MeetupTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private fun formatDate(millis: Long): String =
    SimpleDateFormat("EEE, MMM d \u00B7 h:mm a", Locale.getDefault()).format(Date(millis))

/**
 * Card used in Explore + Home "Going" rail.
 * Single image, title 16sp medium, meta row 12sp secondary, 8dp radius, 1dp grey5 border
 * (the app avoids elevation for list surfaces — cards sit flat with a hairline).
 */
@Composable
fun EventCard(
    event: Event,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(MeetupShape.button),
        color = colors.surface,
        border = androidx.compose.foundation.BorderStroke(1.dp, colors.grey5),
    ) {
        Column {
            Box {
                MeetupImage(
                    url = event.imageUrl,
                    contentDescription = event.title,
                    shape = RoundedCornerShape(topStart = MeetupShape.button, topEnd = MeetupShape.button),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                )
                if (event.isLive || event.isFeatured) {
                    MeetupBadge(
                        text = if (event.isLive) "Live" else "Featured event",
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(MeetupSpacing.small),
                        background = if (event.isLive) colors.lime else colors.surface,
                    )
                }
                if (event.isOnline) {
                    Surface(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(MeetupSpacing.small),
                        shape = RoundedCornerShape(4.dp),
                        color = colors.surface,
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                Icons.Outlined.Videocam,
                                contentDescription = null,
                                tint = colors.viridian,
                                modifier = Modifier.size(14.dp),
                            )
                            Spacer(Modifier.width(4.dp))
                            Text(
                                "Online",
                                style = MaterialTheme.typography.labelSmall,
                                color = colors.viridian,
                            )
                        }
                    }
                }
            }

            Column(modifier = Modifier.padding(MeetupSpacing.normal)) {
                Text(
                    text = formatDate(event.startEpochMillis).uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.peach,
                    fontWeight = FontWeight.Medium,
                )
                Spacer(Modifier.height(MeetupSpacing.xtiny))
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.textPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(MeetupSpacing.xtiny))
                Text(
                    text = event.groupName,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(MeetupSpacing.small))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Place,
                        contentDescription = null,
                        tint = colors.grey1,
                        modifier = Modifier.size(14.dp),
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = if (event.isOnline) "Online event" else "${event.venueName} \u00B7 ${event.venueCity}",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f),
                    )
                }
                Spacer(Modifier.height(MeetupSpacing.small))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    OverlappingAvatarStrip(urls = event.attendeeAvatars.take(3))
                    Spacer(Modifier.width(MeetupSpacing.small))
                    Text(
                        text = "${event.attendeeCount} going",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textTertiary,
                    )
                }
            }
        }
    }
}

/** Compact horizontal row used in "Going" lists and search results. */
@Composable
fun EventRow(
    event: Event,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors
    Surface(onClick = onClick, modifier = modifier.fillMaxWidth(), color = colors.surface) {
        Row(modifier = Modifier.padding(MeetupSpacing.normal)) {
            MeetupImage(
                url = event.imageUrl,
                modifier = Modifier.size(96.dp),
            )
            Spacer(Modifier.width(MeetupSpacing.small))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = formatDate(event.startEpochMillis).uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.peach,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.textPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = event.groupName,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary,
                    maxLines = 1,
                )
                Spacer(Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Outlined.Schedule,
                        contentDescription = null,
                        tint = colors.grey1,
                        modifier = Modifier.size(13.dp),
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "${event.attendeeCount} going",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textTertiary,
                    )
                }
            }
        }
    }
}

/** Fixed-width group tile — @dimen/your_group_card_width = 120dp. */
@Composable
fun YourGroupCard(
    group: Group,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors
    Column(
        modifier = modifier
            .width(MeetupSizes.yourGroupCardWidth)
            .clip(RoundedCornerShape(MeetupShape.button))
            .background(colors.surface)
            .padding(bottom = MeetupSpacing.tiny)
            .then(Modifier),
    ) {
        Surface(onClick = onClick, color = colors.surface) {
            Column {
                MeetupImage(
                    url = group.coverUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(76.dp),
                )
                Spacer(Modifier.height(MeetupSpacing.tiny))
                Text(
                    text = group.name,
                    style = MaterialTheme.typography.labelLarge,
                    color = colors.textPrimary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(horizontal = 2.dp),
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = "${group.memberCount} members",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textTertiary,
                    maxLines = 1,
                    modifier = Modifier.padding(horizontal = 2.dp),
                )
            }
        }
    }
}

/** Full-bleed group row for search results. */
@Composable
fun GroupRow(
    group: Group,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors
    Surface(onClick = onClick, modifier = modifier.fillMaxWidth(), color = colors.surface) {
        Row(
            modifier = Modifier.padding(MeetupSpacing.normal),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            MeetupImage(
                url = group.coverUrl,
                shape = RoundedCornerShape(MeetupShape.icon),
                modifier = Modifier.size(56.dp),
            )
            Spacer(Modifier.width(MeetupSpacing.small))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = group.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    text = buildString {
                        append("${group.memberCount} members")
                        if (group.city.isNotBlank()) append(" \u00B7 ${group.city}")
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary,
                )
                if (group.nextEventTitle != null) {
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = "Next: ${group.nextEventTitle}",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.viridian,
                        maxLines = 1,
                    )
                }
            }
            if (group.isPrivate) {
                Text(
                    text = "Private",
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.textTertiary,
                )
            }
        }
    }
}

/** Category tile — @dimen/home_category_target_width = 150dp, 3:2 aspect. */
@Composable
fun CategoryTile(
    label: String,
    emoji: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors
    Surface(
        onClick = onClick,
        modifier = modifier.width(MeetupSizes.homeCategoryWidth),
        shape = RoundedCornerShape(MeetupShape.button),
        color = colors.grey6,
    ) {
        Box(modifier = Modifier.height(84.dp)) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp),
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelLarge,
                color = colors.textPrimary,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(MeetupSpacing.small),
            )
        }
    }
}

/** Placeholder for the image slot while a remote asset loads. */
@Composable
fun ImageSkeleton(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MeetupTheme.colors.grey6),
    )
}
