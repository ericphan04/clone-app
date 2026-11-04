package com.clone.meetup.nav

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.clone.meetup.ui.components.MeetupDivider
import com.clone.meetup.ui.theme.MeetupShape
import com.clone.meetup.ui.theme.MeetupSizes
import com.clone.meetup.ui.theme.MeetupSpacing
import com.clone.meetup.ui.theme.MeetupTheme

/** Mirrors com.meetup.feature.legacy.activity.RootTab — 4 tabs, in this exact order. */
enum class RootTab(
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    HOME("Home", Icons.Filled.Home, Icons.Outlined.Home),
    EXPLORE("Explore", Icons.Filled.Search, Icons.Outlined.Search),
    NOTIFICATIONS("Notifications", Icons.Filled.Notifications, Icons.Outlined.Notifications),
    MESSAGES("Messages", Icons.Filled.Email, Icons.Outlined.Email),
}

/**
 * Bottom bar. The real app renders this in a ComposeView anchored to the root FrameLayout
 * (@id/bottom_nav_compose) with a 1dp top hairline and no elevation.
 * 48dp tab row + navigation bar inset; icon 22dp; label 12sp medium.
 */
@Composable
fun MeetupBottomBar(
    selected: RootTab,
    onSelect: (RootTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors

    Column(modifier = modifier.fillMaxWidth().background(colors.surface)) {
        MeetupDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(MeetupSizes.tabBarHeight + MeetupSpacing.tiny),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            RootTab.entries.forEach { tab ->
                val isSelected = tab == selected
                val iconAlpha by animateFloatAsState(
                    targetValue = if (isSelected) 1f else 0.62f,
                    label = "tabIconAlpha",
                )
                val tint = if (isSelected) colors.viridian else colors.textPrimary.copy(alpha = iconAlpha)

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .selectable(
                            selected = isSelected,
                            role = Role.Tab,
                            onClick = { onSelect(tab) },
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        imageVector = if (isSelected) tab.selectedIcon else tab.unselectedIcon,
                        contentDescription = tab.label,
                        tint = tint,
                        modifier = Modifier.size(22.dp),
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = tab.label,
                        style = MaterialTheme.typography.labelSmall,
                        color = tint,
                        fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal,
                        maxLines = 1,
                    )
                }
            }
        }
    }
}
