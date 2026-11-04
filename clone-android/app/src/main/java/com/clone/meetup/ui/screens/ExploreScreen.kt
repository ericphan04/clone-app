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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.clone.meetup.data.SampleData
import com.clone.meetup.ui.components.CategoryTile
import com.clone.meetup.ui.components.EventCard
import com.clone.meetup.ui.components.MeetupChip
import com.clone.meetup.ui.components.SectionHeader
import com.clone.meetup.ui.theme.MeetupShape
import com.clone.meetup.ui.theme.MeetupSpacing
import com.clone.meetup.ui.theme.MeetupTheme

/**
 * Explore tab. Mirrors NewExploreFragment: search field, filter chip rail
 * (Today / Tomorrow / This weekend / Free / Online), "Browse by category" tiles,
 * then a 2-column event feed.
 */
@Composable
fun ExploreScreen(
    onEventClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors
    var selectedFilter by remember { mutableStateOf("All") }

    val filters = listOf(
        "All", "Today", "Tomorrow", "This weekend", "Next week", "Online", "In person", "Free",
    )

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
                    text = "Explore",
                    style = MaterialTheme.typography.displayLarge,
                    color = colors.textPrimary,
                )
                Spacer(Modifier.height(MeetupSpacing.small))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        onClick = { },
                        shape = RoundedCornerShape(MeetupShape.button),
                        color = colors.grey6,
                        modifier = Modifier.weight(1f),
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                horizontal = MeetupSpacing.small,
                                vertical = MeetupSpacing.small,
                            ),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                Icons.Outlined.Search,
                                contentDescription = null,
                                tint = colors.textTertiary,
                                modifier = Modifier.size(18.dp),
                            )
                            Spacer(Modifier.width(MeetupSpacing.tiny))
                            Text(
                                text = "Search events or groups...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = colors.textTertiary,
                            )
                        }
                    }
                    Spacer(Modifier.width(MeetupSpacing.tiny))
                    Surface(
                        onClick = { },
                        shape = CircleShape,
                        color = colors.grey6,
                        modifier = Modifier.size(44.dp),
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                Icons.Filled.Tune,
                                contentDescription = "Filters",
                                tint = colors.textPrimary,
                                modifier = Modifier.size(20.dp),
                            )
                        }
                    }
                }
            }
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = MeetupSpacing.normal),
                horizontalArrangement = Arrangement.spacedBy(MeetupSpacing.tiny),
            ) {
                items(filters) { filter ->
                    MeetupChip(
                        text = filter,
                        selected = filter == selectedFilter,
                        onClick = { selectedFilter = filter },
                    )
                }
            }
        }

        item {
            SectionHeader(title = "Browse by category")
        }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = MeetupSpacing.normal),
                horizontalArrangement = Arrangement.spacedBy(MeetupSpacing.small),
            ) {
                items(SampleData.categories) { category ->
                    CategoryTile(
                        label = category.label,
                        emoji = category.emoji,
                        onClick = { },
                    )
                }
            }
        }

        item { Spacer(Modifier.height(MeetupSpacing.stripe)) }

        item {
            SectionHeader(title = "Popular now")
        }

        item {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = MeetupSpacing.normal),
                horizontalArrangement = Arrangement.spacedBy(MeetupSpacing.small),
                verticalItemSpacing = MeetupSpacing.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1200.dp),
                userScrollEnabled = false,
            ) {
                items(SampleData.events.size) { index ->
                    val event = SampleData.events[index]
                    EventCard(event = event, onClick = { onEventClick(event.id) })
                }
            }
        }
    }
}
