package com.clone.meetup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.clone.meetup.data.SampleData
import com.clone.meetup.nav.MeetupBottomBar
import com.clone.meetup.nav.RootTab
import com.clone.meetup.ui.screens.AuthScreen
import com.clone.meetup.ui.screens.EventDetailScreen
import com.clone.meetup.ui.screens.ExploreScreen
import com.clone.meetup.ui.screens.GroupTimelineScreen
import com.clone.meetup.ui.screens.HomeScreen
import com.clone.meetup.ui.screens.MessagesScreen
import com.clone.meetup.ui.screens.NotificationsScreen
import com.clone.meetup.ui.screens.OnboardingScreen
import com.clone.meetup.ui.theme.MeetupTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MeetupTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MeetupApp()
                }
            }
        }
    }
}

/**
 * Route table. Mirrors the destination names in the decompiled nav graphs:
 * auth_nav_graph -> Onboarding/Auth, group_nav_graph -> Timeline,
 * events_nav_graph -> Event. Root shell stays mount-once with a local tab state,
 * exactly like RootActivity's FrameLayout + ComposeView overlay.
 */
private object Routes {
    const val ONBOARDING = "onboarding"
    const val AUTH = "auth"
    const val ROOT = "root"
    const val EVENT = "event/{eventId}"
    const val GROUP = "group/{groupId}"

    fun event(id: String) = "event/$id"
    fun group(id: String) = "group/$id"
}

@Composable
private fun MeetupApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.ONBOARDING,
        enterTransition = { fadeIn(tween(180)) },
        exitTransition = { fadeOut(tween(140)) },
    ) {
        composable(Routes.ONBOARDING) {
            OnboardingScreen(
                onFinish = {
                    navController.navigate(Routes.ROOT) {
                        popUpTo(Routes.ONBOARDING) { inclusive = true }
                    }
                },
            )
        }

        composable(Routes.AUTH) {
            AuthScreen(
                onBack = { navController.popBackStack() },
                onSignIn = {
                    navController.navigate(Routes.ROOT) {
                        popUpTo(Routes.AUTH) { inclusive = true }
                    }
                },
            )
        }

        composable(Routes.ROOT) {
            RootScaffold(
                onEventClick = { id -> navController.navigate(Routes.event(id)) },
                onGroupClick = { id -> navController.navigate(Routes.group(id)) },
                onProfileClick = { navController.navigate(Routes.AUTH) },
            )
        }

        composable(
            route = Routes.EVENT,
            arguments = listOf(navArgument("eventId") { type = NavType.StringType }),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(260),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(240),
                )
            },
        ) { entry ->
            val eventId = entry.arguments?.getString("eventId").orEmpty()
            val event = SampleData.events.firstOrNull { it.id == eventId } ?: SampleData.events.first()
            EventDetailScreen(
                event = event,
                onBack = { navController.popBackStack() },
                onGroupClick = { urlname ->
                    val match = SampleData.groups.firstOrNull { it.urlname == urlname }
                    if (match != null) navController.navigate(Routes.group(match.id))
                },
            )
        }

        composable(
            route = Routes.GROUP,
            arguments = listOf(navArgument("groupId") { type = NavType.StringType }),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    animationSpec = tween(260),
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    animationSpec = tween(240),
                )
            },
        ) { entry ->
            val groupId = entry.arguments?.getString("groupId").orEmpty()
            val group = SampleData.groups.firstOrNull { it.id == groupId } ?: SampleData.groups.first()
            GroupTimelineScreen(
                group = group,
                onBack = { navController.popBackStack() },
                onEventClick = { id -> navController.navigate(Routes.event(id)) },
            )
        }
    }
}

@Composable
private fun RootScaffold(
    onEventClick: (String) -> Unit,
    onGroupClick: (String) -> Unit,
    onProfileClick: () -> Unit,
) {
    var tab by remember { mutableStateOf(RootTab.HOME) }
    val colors = MeetupTheme.colors

    Box(modifier = Modifier.fillMaxSize().background(colors.surface)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 64.dp),
        ) {
            when (tab) {
                RootTab.HOME -> HomeScreen(
                    onEventClick = onEventClick,
                    onGroupClick = onGroupClick,
                    onExploreClick = { tab = RootTab.EXPLORE },
                )
                RootTab.EXPLORE -> ExploreScreen(onEventClick = onEventClick)
                RootTab.NOTIFICATIONS -> NotificationsScreen(onSettingsClick = onProfileClick)
                RootTab.MESSAGES -> MessagesScreen(onThreadClick = { })
            }
        }

        MeetupBottomBar(
            selected = tab,
            onSelect = { tab = it },
            modifier = Modifier.align(Alignment.BottomCenter),
        )
    }
}
