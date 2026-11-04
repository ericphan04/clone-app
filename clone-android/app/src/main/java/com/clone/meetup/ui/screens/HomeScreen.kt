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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.clone.meetup.data.SampleData
import com.clone.meetup.ui.components.EventCard
import com.clone.meetup.ui.components.EventRow
import com.clone.meetup.ui.components.MeetupAvatar
import com.clone.meetup.ui.components.MeetupBadge
import com.clone.meetup.ui.components.MeetupDivider
import com.clone.meetup.ui.components.MeetupTextButton
import com.clone.meetup.ui.components.SectionHeader
import com.clone.meetup.ui.components.YourGroupCard
import com.clone.meetup.ui.theme.MeetupShape
import com.clone.meetup.ui.theme.MeetupSizes
import com.clone.meetup.ui.theme.MeetupSpacing
import com.clone.meetup.ui.theme.MeetupTheme

/**
 * Home tab. Structure lifted from HomeFragment + res/values/strings.xml home_* keys:
 * app bar with location + search + bell, "Going" section (2 events), "Your groups" rail,
 * "Start a new group" promo card, then recommended events.
 */
@Composable
fun HomeScreen(
    onEventClick: (String) -> Unit,
    onGroupClick: (String) -> Unit,
    onExploreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors
    val going = SampleData.goingEvents()
    val recommended = SampleData.recommendedEvents()

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface),
        contentPadding = PaddingValues(bottom = MeetupSpacing.xlarge),
    ) {
        item { HomeTopBar() }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = MeetupSpacing.normal),
                horizontalArrangement = Arrangement.spacedBy(MeetupSpacing.small),
            ) {
                items(SampleData.events.take(4)) { event ->
                    Box(Modifier.width(300.dp)) {
                        EventCard(event = event, onClick = { onEventClick(event.id) })
                    }
                }
            }
        }

        item {
            SectionHeader(
                title = "Going",
                actionLabel = "See all",
                onAction = { },
            )
        }

        items(going) { event ->
            EventRow(event = event, onClick = { onEventClick(event.id) })
            MeetupDivider(modifier = Modifier.padding(start = 128.dp))
        }

        item { Spacer(Modifier.height(MeetupSpacing.small)) }

        item {
            SectionHeader(
                title = "Your groups",
                actionLabel = "See all",
                onAction = { },
            )
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = MeetupSpacing.normal),
                horizontalArrangement = Arrangement.spacedBy(MeetupSpacing.small),
            ) {
                items(SampleData.groups) { group ->
                    YourGroupCard(group = group, onClick = { onGroupClick(group.id) })
                }
            }
        }

        item { Spacer(Modifier.height(MeetupSpacing.stripe)) }

        item { StartAGroupCard() }

        item {
            SectionHeader(
                title = "Popular near you",
                actionLabel = "Explore",
                onAction = onExploreClick,
            )
        }

        items(recommended) { event ->
            Box(Modifier.padding(horizontal = MeetupSpacing.normal, vertical = MeetupSpacing.xtiny)) {
                EventCard(event = event, onClick = { onEventClick(event.id) })
            }
            Spacer(Modifier.height(MeetupSpacing.small))
        }
    }
}

@Composable
private fun HomeTopBar() {
    val colors = MeetupTheme.colors
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colors.surface)
            .statusBarsPadding()
            .padding(horizontal = MeetupSpacing.normal, vertical = MeetupSpacing.small),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Location",
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.textTertiary,
                )
                Text(
                    text = "Ho Chi Minh City",
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.textPrimary,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Surface(
                onClick = { },
                shape = CircleShape,
                color = colors.grey6,
                modifier = Modifier.size(40.dp),
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        Icons.Outlined.Search,
                        contentDescription = "Search",
                        tint = colors.textPrimary,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
            Spacer(Modifier.width(MeetupSpacing.tiny))
            Box {
                Surface(
                    onClick = { },
                    shape = CircleShape,
                    color = colors.grey6,
                    modifier = Modifier.size(40.dp),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            tint = colors.textPrimary,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(9.dp)
                        .background(colors.peach, CircleShape),
                )
            }
        }
    }
}

@Composable
private fun StartAGroupCard() {
    val colors = MeetupTheme.colors
    Surface(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MeetupSpacing.normal),
        shape = RoundedCornerShape(MeetupShape.button),
        color = colors.grey6,
    ) {
        Row(
            modifier = Modifier.padding(MeetupSpacing.normal),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(colors.surface, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Filled.Add,
                    contentDescription = null,
                    tint = colors.viridian,
                    modifier = Modifier.size(22.dp),
                )
            }
            Spacer(Modifier.width(MeetupSpacing.small))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Start a new group",
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.textPrimary,
                )
                Text(
                    text = "Organize your own events",
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.textSecondary,
                )
            }
            MeetupTextButton(text = "Start", onClick = { })
        }
    }
}
