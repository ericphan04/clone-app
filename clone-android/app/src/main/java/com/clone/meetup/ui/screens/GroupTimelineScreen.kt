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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.clone.meetup.ui.components.MeetupImage
import com.clone.meetup.data.Group
import com.clone.meetup.data.SampleData
import com.clone.meetup.ui.components.EventRow
import com.clone.meetup.ui.components.MeetupChip
import com.clone.meetup.ui.components.MeetupDivider
import com.clone.meetup.ui.components.MeetupPrimaryButton
import com.clone.meetup.ui.theme.MeetupShape
import com.clone.meetup.ui.theme.MeetupSizes
import com.clone.meetup.ui.theme.MeetupSpacing
import com.clone.meetup.ui.theme.MeetupTheme

/**
 * Group timeline. Mirrors `group_nav_graph` → `TimelineFragment`.
 * Tab set from res/values/strings.xml group_timeline_tab_*:
 * Upcoming · Past · Discussions · Photos · Members.
 * Header: 200dp cover, name 32sp, member count + city, Join CTA pinned right of the meta row.
 */
@Composable
fun GroupTimelineScreen(
    group: Group,
    onBack: () -> Unit,
    onEventClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Upcoming", "Past", "Discussions", "Photos", "Members")

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface),
        contentPadding = PaddingValues(bottom = MeetupSpacing.xlarge),
    ) {
        item {
            Box {
                MeetupImage(
                    url = group.coverUrl,
                    shape = RoundedCornerShape(0.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                )
                Row(
                    modifier = Modifier
                        .statusBarsPadding()
                        .fillMaxWidth()
                        .padding(MeetupSpacing.small),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
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
                    Spacer(Modifier.weight(1f))
                    Surface(
                        onClick = { },
                        shape = CircleShape,
                        color = colors.surface,
                        modifier = Modifier.size(40.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Filled.MoreVert,
                                contentDescription = "More",
                                tint = colors.textPrimary,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    }
                }
            }
        }

        item {
            Column(modifier = Modifier.padding(MeetupSpacing.normal)) {
                Text(
                    text = group.name,
                    style = MaterialTheme.typography.displayLarge,
                    color = colors.textPrimary,
                )
                Spacer(Modifier.height(MeetupSpacing.xtiny))
                Text(
                    text = buildString {
                        append("${group.memberCount} members")
                        if (group.city.isNotBlank()) append(" \u00B7 ${group.city}")
                        if (group.isPrivate) append(" \u00B7 Private group")
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.textSecondary,
                )
                Spacer(Modifier.height(MeetupSpacing.small))
                Row(horizontalArrangement = Arrangement.spacedBy(MeetupSpacing.tiny)) {
                    group.topics.take(3).forEach { topic ->
                        MeetupChip(text = topic, selected = false, onClick = { })
                    }
                }
                Spacer(Modifier.height(MeetupSpacing.normal))
                Row(horizontalArrangement = Arrangement.spacedBy(MeetupSpacing.small)) {
                    MeetupPrimaryButton(
                        text = if (group.isPrivate) "Request to join" else "Join group",
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
                            modifier = Modifier.padding(horizontal = MeetupSpacing.normal),
                            contentAlignment = Alignment.Center,
                        ) {
                            Text(
                                text = "Share",
                                style = MaterialTheme.typography.labelLarge,
                                color = colors.textPrimary,
                            )
                        }
                    }
                }
            }
        }

        item { MeetupDivider() }

        item {
            ScrollableTabRow(
                selectedTabIndex = tabIndex,
                containerColor = colors.surface,
                contentColor = colors.textPrimary,
                edgePadding = MeetupSpacing.normal,
                divider = { MeetupDivider() },
            ) {
                tabs.forEachIndexed { index, label ->
                    Tab(
                        selected = index == tabIndex,
                        onClick = { tabIndex = index },
                        text = {
                            Text(
                                text = label,
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = if (index == tabIndex) FontWeight.Medium else FontWeight.Normal,
                                color = if (index == tabIndex) colors.textPrimary else colors.textSecondary,
                            )
                        },
                    )
                }
            }
        }

        when (tabIndex) {
            0 -> items(SampleData.events) { event ->
                EventRow(event = event, onClick = { onEventClick(event.id) })
                MeetupDivider(modifier = Modifier.padding(start = 128.dp))
            }

            1 -> item { GroupEmptyTab("No past events", "This group hasn't hosted an event yet.") }
            2 -> item { GroupEmptyTab("No discussions yet", "Start the first conversation with your group.") }
            3 -> item { GroupEmptyTab("No photos yet", "Photos from events will show up here.") }
            4 -> items(SampleData.groups) { member ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = MeetupSpacing.normal, vertical = MeetupSpacing.small),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Box(
                        modifier = Modifier
                            .size(MeetupSizes.avatarSmall)
                            .clip(CircleShape)
                            .background(colors.grey6),
                    )
                    Spacer(Modifier.width(MeetupSpacing.small))
                    Column {
                        Text(
                            text = member.name,
                            style = MaterialTheme.typography.titleMedium,
                            color = colors.textPrimary,
                        )
                        Text(
                            text = "Member",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.textSecondary,
                        )
                    }
                }
                MeetupDivider(modifier = Modifier.padding(start = 76.dp))
            }
        }
    }
}

@Composable
private fun GroupEmptyTab(title: String, body: String) {
    val colors = MeetupTheme.colors
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MeetupSpacing.stripe, vertical = MeetupSpacing.large),
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
    }
}
