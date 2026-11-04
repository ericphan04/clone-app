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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.clone.meetup.data.SampleData
import com.clone.meetup.ui.components.MeetupChip
import com.clone.meetup.ui.components.MeetupPrimaryButton
import com.clone.meetup.ui.theme.MeetupShape
import com.clone.meetup.ui.theme.MeetupSpacing
import com.clone.meetup.ui.theme.MeetupTheme
import kotlinx.coroutines.launch

private data class OnboardingPage(
    val eyebrow: String,
    val title: String,
    val body: String,
    val gradient: List<Long>,
)

/**
 * Onboarding. Mirrors `feature.onboarding.NewOnboardingActivity` + auth_nav_graph IntroFragment.
 * Three intro pages, skip in the top-right, dot indicator, then the interest picker
 * (`auto_join_groups_title` = "Groups for you", `browse_popular_groups` CTA).
 * The real flow ends at FinitoActivity — "Invite your friends".
 */
@Composable
fun OnboardingScreen(
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MeetupTheme.colors
    val scope = rememberCoroutineScope()

    val pages = listOf(
        OnboardingPage(
            eyebrow = "Find your people",
            title = "Whatever you're into, there's a group for it.",
            body = "Thousands of local groups meet every week \u2014 running, film, cooking, code, climbing, anything.",
            gradient = listOf(0xFF008294, 0xFF00455D),
        ),
        OnboardingPage(
            eyebrow = "Show up",
            title = "RSVP in two taps and you're in.",
            body = "No group chats to wrangle. Pick an event, tap Attend, and it lands in your calendar.",
            gradient = listOf(0xFFF65858, 0xFF7D694A),
        ),
        OnboardingPage(
            eyebrow = "Or start one",
            title = "Host the thing you wish existed.",
            body = "Open a group, publish an event, and invite the people who've been waiting for it.",
            gradient = listOf(0xFF32D74B, 0xFF008294),
        ),
    )

    val pagerState = rememberPagerState(pageCount = { pages.size + 1 })
    val isInterestStep = pagerState.currentPage == pages.size

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colors.surface),
    ) {
        if (!isInterestStep) {
            Row(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(MeetupSpacing.normal),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Spacer(Modifier.weight(1f))
                Surface(onClick = onFinish, color = colors.surface) {
                    Text(
                        text = "Skip",
                        style = MaterialTheme.typography.labelLarge,
                        color = colors.textSecondary,
                        modifier = Modifier.padding(MeetupSpacing.tiny),
                    )
                }
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.Top,
        ) { page ->
            if (page < pages.size) {
                val p = pages[page]
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(horizontal = MeetupSpacing.normal)
                            .clip(RoundedCornerShape(MeetupShape.button))
                            .background(Brush.linearGradient(p.gradient.map { androidx.compose.ui.graphics.Color(it) })),
                    )
                    Column(modifier = Modifier.padding(MeetupSpacing.normal)) {
                        Text(
                            text = p.eyebrow.uppercase(),
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.peach,
                            fontWeight = FontWeight.Medium,
                        )
                        Spacer(Modifier.height(MeetupSpacing.tiny))
                        Text(
                            text = p.title,
                            style = MaterialTheme.typography.displayLarge,
                            color = colors.textPrimary,
                        )
                        Spacer(Modifier.height(MeetupSpacing.small))
                        Text(
                            text = p.body,
                            style = MaterialTheme.typography.bodyLarge,
                            color = colors.textSecondary,
                        )
                        Spacer(Modifier.height(MeetupSpacing.stripe))
                    }
                }
            } else {
                InterestPicker(onFinish = onFinish)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(MeetupSpacing.normal),
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                repeat(pages.size + 1) { index ->
                    val active = index == pagerState.currentPage
                    Box(
                        modifier = Modifier
                            .height(4.dp)
                            .width(if (active) 24.dp else 8.dp)
                            .clip(CircleShape)
                            .background(if (active) colors.textPrimary else colors.grey4),
                    )
                }
            }
            Spacer(Modifier.height(MeetupSpacing.normal))
            MeetupPrimaryButton(
                text = if (isInterestStep) "Start exploring" else "Continue",
                onClick = {
                    if (isInterestStep) {
                        onFinish()
                    } else {
                        scope.launch { pagerState.animateScrollToPage(pagerState.currentPage + 1) }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun InterestPicker(onFinish: () -> Unit) {
    val colors = MeetupTheme.colors
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MeetupSpacing.normal)
            .statusBarsPadding(),
    ) {
        Text(
            text = "Groups for you",
            style = MaterialTheme.typography.displayLarge,
            color = colors.textPrimary,
        )
        Spacer(Modifier.height(MeetupSpacing.tiny))
        Text(
            text = "Based on your interests and location, we joined you a few groups to help you get started.",
            style = MaterialTheme.typography.bodyLarge,
            color = colors.textSecondary,
        )
        Spacer(Modifier.height(MeetupSpacing.stripe))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(MeetupSpacing.tiny)) {
            items(SampleData.categories.take(10)) { category ->
                MeetupChip(text = "${category.emoji}  ${category.label}", selected = false, onClick = { })
            }
        }
        Spacer(Modifier.height(MeetupSpacing.stripe))
        Text(
            text = "Others you might like",
            style = MaterialTheme.typography.headlineMedium,
            color = colors.textPrimary,
        )
        Spacer(Modifier.height(MeetupSpacing.small))
        SampleData.groups.take(3).forEach { group ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MeetupSpacing.tiny),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(MeetupShape.icon))
                        .background(colors.grey6),
                )
                Spacer(Modifier.width(MeetupSpacing.small))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = group.name,
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.textPrimary,
                    )
                    Text(
                        text = "${group.memberCount} members",
                        style = MaterialTheme.typography.bodySmall,
                        color = colors.textSecondary,
                    )
                }
                Text(
                    text = "Joined",
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.textTertiary,
                )
            }
        }
    }
}
