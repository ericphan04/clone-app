package com.clone.meetup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Share
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
import com.clone.meetup.ui.components.MeetupImage
import com.clone.meetup.data.Event
import com.clone.meetup.data.SampleData
import com.clone.meetup.ui.components.MeetupAvatar
import com.clone.meetup.ui.components.MeetupBadge
import com.clone.meetup.ui.components.MeetupDivider
import com.clone.meetup.ui.components.MeetupPrimaryButton
import com.clone.meetup.ui.components.OverlappingAvatarStrip
import com.clone.meetup.ui.theme.MeetupShape
import com.clone.meetup.ui.theme.MeetupSizes
import com.clone.meetup.ui.theme.MeetupSpacing
import com.clone.meetup.ui.theme.MeetupTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Event detail. Matches EventFragment's vertical order:
 * hero image -> title -> date row -> venue row -> host + attendee strip ->
 * divider -> About -> divider -> Attendees -> (sticky) RSVP bar.
 *
 * The sticky bar is the app's signature move: bottom-anchored, surface fill, 1dp top hairline,
 * RSVP primary on the left, share icon button on the right.
 */
@Composable
fun EventDetailScreen(
    event: Event,
    onBack: () -> Unit,
    onGroupClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors
    val dateFmt = SimpleDateFormat("EEEE, MMMM d \u00B7 h:mm a", Locale.getDefault())
    val endFmt = SimpleDateFormat("h:mm a", Locale.getDefault())

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface),
    ) {
        Box {
            MeetupImage(
                url = event.imageUrl,
                contentDescription = event.title,
                shape = RoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp),
            )
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(MeetupSpacing.small),
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        onClick = onBack,
                        shape = CircleShape,
                        color = colors.surface,
                        modifier = Modifier.size(40.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = colors.textPrimary,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    }
                    Spacer(Modifier.width(MeetupSpacing.tiny))
                    Surface(
                        onClick = { },
                        shape = CircleShape,
                        color = colors.surface,
                        modifier = Modifier.size(40.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Outlined.Share,
                                contentDescription = "Share",
                                tint = colors.textPrimary,
                                modifier = Modifier.size(18.dp),
                            )
                        }
                    }
                }
            }
            if (event.isFeatured) {
                MeetupBadge(
                    text = "Featured event",
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(MeetupSpacing.normal),
                    background = colors.surface,
                )
            }
        }

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = MeetupSpacing.stripe),
        ) {
            item {
                Column(modifier = Modifier.padding(MeetupSpacing.normal)) {
                    if (event.isLive) {
                        MeetupBadge(text = "Live", background = colors.lime)
                        Spacer(Modifier.height(MeetupSpacing.tiny))
                    }
                    Text(
                        text = event.title,
                        style = MaterialTheme.typography.displayLarge,
                        color = colors.textPrimary,
                    )
                    Spacer(Modifier.height(MeetupSpacing.normal))

                    MetaRow(
                        icon = Icons.Filled.DateRange,
                        primary = dateFmt.format(Date(event.startEpochMillis)),
                        secondary = "Ends ${endFmt.format(Date(event.endEpochMillis))} \u00B7 ${event.timezone}",
                    )
                    Spacer(Modifier.height(MeetupSpacing.normal))
                    MetaRow(
                        icon = Icons.Filled.Place,
                        primary = if (event.isOnline) "Online event" else event.venueName,
                        secondary = if (event.isOnline) "Link shared after RSVP" else event.venueCity,
                    )
                    Spacer(Modifier.height(MeetupSpacing.normal))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        MeetupAvatar(url = null, size = MeetupSizes.avatarSmall)
                        Spacer(Modifier.width(MeetupSpacing.tiny))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Hosted by ${event.host}",
                                style = MaterialTheme.typography.labelLarge,
                                color = colors.textPrimary,
                            )
                            Text(
                                text = event.groupName,
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.viridian,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier,
                            )
                        }
                    }
                }
            }

            item { MeetupDivider() }

            item {
                Column(modifier = Modifier.padding(MeetupSpacing.normal)) {
                    Text(
                        text = "About this event",
                        style = MaterialTheme.typography.headlineMedium,
                        color = colors.textPrimary,
                    )
                    Spacer(Modifier.height(MeetupSpacing.tiny))
                    Text(
                        text = event.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.textSecondary,
                    )
                    if (event.hasFee) {
                        Spacer(Modifier.height(MeetupSpacing.small))
                        Surface(
                            shape = RoundedCornerShape(MeetupShape.button),
                            color = colors.grey6,
                        ) {
                            Text(
                                text = "Paid event \u2014 fee collected by the organizer",
                                style = MaterialTheme.typography.bodySmall,
                                color = colors.textSecondary,
                                modifier = Modifier.padding(MeetupSpacing.small),
                            )
                        }
                    }
                }
            }

            item { MeetupDivider() }

            item {
                Column(modifier = Modifier.padding(MeetupSpacing.normal)) {
                    Text(
                        text = "${event.attendeeCount} going",
                        style = MaterialTheme.typography.headlineMedium,
                        color = colors.textPrimary,
                    )
                    Spacer(Modifier.height(MeetupSpacing.small))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OverlappingAvatarStrip(
                            urls = event.attendeeAvatars,
                            size = MeetupSizes.avatarSmall,
                        )
                        Spacer(Modifier.width(MeetupSpacing.small))
                        Text(
                            text = "See all attendees",
                            style = MaterialTheme.typography.labelLarge,
                            color = colors.viridian,
                        )
                    }
                }
            }

            item { MeetupDivider() }

            item {
                Column(modifier = Modifier.padding(MeetupSpacing.normal)) {
                    Text(
                        text = "More events from ${event.groupName}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = colors.textPrimary,
                    )
                    Spacer(Modifier.height(MeetupSpacing.small))
                    Surface(
                        onClick = { onGroupClick(event.groupUrlname) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(MeetupShape.button),
                        color = colors.grey6,
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(MeetupSpacing.normal),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            MeetupImage(
                                url = SampleData.groups
                                    .firstOrNull { it.urlname == event.groupUrlname }
                                    ?.coverUrl,
                                shape = RoundedCornerShape(MeetupShape.icon),
                                modifier = Modifier.size(48.dp),
                            )
                            Spacer(Modifier.width(MeetupSpacing.small))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = event.groupName,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = colors.textPrimary,
                                )
                                Text(
                                    text = "View group",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = colors.textSecondary,
                                )
                            }
                            Text(
                                text = "Open",
                                style = MaterialTheme.typography.labelLarge,
                                color = colors.viridian,
                            )
                        }
                    }
                }
            }
        }

        RsvpBar(event = event)
    }
}

@Composable
private fun MetaRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    primary: String,
    secondary: String,
) {
    val colors = MeetupTheme.colors
    Row(verticalAlignment = Alignment.Top) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colors.textPrimary,
            modifier = Modifier
                .padding(top = 2.dp)
                .size(20.dp),
        )
        Spacer(Modifier.width(MeetupSpacing.small))
        Column {
            Text(
                text = primary,
                style = MaterialTheme.typography.labelLarge,
                color = colors.textPrimary,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = secondary,
                style = MaterialTheme.typography.bodySmall,
                color = colors.textSecondary,
            )
        }
    }
}

@Composable
private fun RsvpBar(event: Event) {
    val colors = MeetupTheme.colors
    Surface(
        color = colors.surface,
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column {
            MeetupDivider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .navigationBarsPadding()
                    .padding(horizontal = MeetupSpacing.normal, vertical = MeetupSpacing.small),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MeetupSpacing.small),
            ) {
                MeetupPrimaryButton(
                    text = if (event.hasFee) "Attend \u00B7 Paid" else "Attend",
                    onClick = { },
                    modifier = Modifier.weight(1f),
                )
                Surface(
                    onClick = { },
                    shape = RoundedCornerShape(MeetupShape.button),
                    color = colors.grey6,
                    modifier = Modifier.height(MeetupSizes.touchTarget),
                ) {
                    Box(
                        modifier = Modifier.padding(horizontal = MeetupSpacing.small),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = "Not going",
                            style = MaterialTheme.typography.labelLarge,
                            color = colors.textPrimary,
                        )
                    }
                }
            }
        }
    }
}
