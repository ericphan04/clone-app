package com.clone.meetup.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.clone.meetup.data.SampleData
import com.clone.meetup.ui.components.MeetupAvatar
import com.clone.meetup.ui.components.MeetupDivider
import com.clone.meetup.ui.theme.MeetupSpacing
import com.clone.meetup.ui.theme.MeetupTheme

/**
 * Messages tab. Mirrors the COCO messenger thread list: 48dp avatar, title 16sp medium,
 * preview 14sp secondary truncated to one line, timestamp 12sp tertiary, unread dot on
 * the avatar's trailing-top edge.
 */
@Composable
fun MessagesScreen(
    onThreadClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface),
        contentPadding = PaddingValues(bottom = MeetupSpacing.xlarge),
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = MeetupSpacing.normal, vertical = MeetupSpacing.small),
            ) {
                Text(
                    text = "Messages",
                    style = MaterialTheme.typography.displayLarge,
                    color = colors.textPrimary,
                )
            }
        }

        items(SampleData.threads) { thread ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MeetupSpacing.normal, vertical = MeetupSpacing.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box {
                    MeetupAvatar(
                        url = thread.avatarUrl,
                        size = com.clone.meetup.ui.theme.MeetupSizes.avatarMedium,
                        circular = !thread.isGroupChat,
                    )
                    if (thread.unread) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .size(11.dp)
                                .background(colors.peach, CircleShape),
                        )
                    }
                }
                Spacer(Modifier.size(MeetupSpacing.small))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = thread.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = colors.textPrimary,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false),
                        )
                        Spacer(Modifier.size(MeetupSpacing.tiny))
                        Text(
                            text = thread.timestamp,
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.textTertiary,
                        )
                    }
                    Spacer(Modifier.height(3.dp))
                    Text(
                        text = thread.preview,
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (thread.unread) colors.textPrimary else colors.textSecondary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                }
            }
            MeetupDivider(modifier = Modifier.padding(start = 76.dp))
        }
    }
}

/**
 * Notifications tab. Same row anatomy as messages but with a surface fill for unread items
 * (palette_background_unread = #1AFFFFFF light / grey5 dark).
 */
@Composable
fun NotificationsScreen(
    onSettingsClick: () -> Unit = {},
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface),
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .padding(horizontal = MeetupSpacing.normal, vertical = MeetupSpacing.small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "Notifications",
                    style = MaterialTheme.typography.displayLarge,
                    color = colors.textPrimary,
                    modifier = Modifier.weight(1f),
                )
                androidx.compose.material3.Surface(
                    onClick = onSettingsClick,
                    shape = CircleShape,
                    color = colors.grey6,
                    modifier = Modifier.size(40.dp),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        androidx.compose.foundation.Image(
                            painter = androidx.compose.ui.res.painterResource(
                                id = com.clone.meetup.R.drawable.ic_profile_screen_member_plus,
                            ),
                            contentDescription = "Profile and settings",
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(colors.textPrimary),
                            modifier = Modifier.size(20.dp),
                        )
                    }
                }
            }
        }

        items(SampleData.notifications) { notification ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(if (notification.isUnread) colors.grey6 else colors.surface)
                    .padding(horizontal = MeetupSpacing.normal, vertical = MeetupSpacing.small),
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    MeetupAvatar(
                        url = notification.avatarUrl,
                        size = com.clone.meetup.ui.theme.MeetupSizes.avatarSmall,
                        circular = false,
                    )
                    Spacer(Modifier.size(MeetupSpacing.small))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = notification.title,
                            style = MaterialTheme.typography.titleMedium,
                            color = colors.textPrimary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = notification.body,
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.textSecondary,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = notification.timestamp,
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.textTertiary,
                        )
                    }
                }
            }
            MeetupDivider()
        }
    }
}
