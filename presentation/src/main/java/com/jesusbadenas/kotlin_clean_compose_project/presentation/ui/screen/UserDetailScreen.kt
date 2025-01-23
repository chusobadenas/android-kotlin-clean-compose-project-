package com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.rememberAsyncImagePainter
import com.jesusbadenas.kotlin_clean_compose_project.domain.model.User
import com.jesusbadenas.kotlin_clean_compose_project.presentation.R
import com.jesusbadenas.kotlin_clean_compose_project.presentation.model.UIState
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.components.LoadingView
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.components.Toolbar
import com.jesusbadenas.kotlin_clean_compose_project.presentation.ui.mock.mockUser
import com.jesusbadenas.kotlin_clean_compose_project.presentation.userdetails.UserDetailsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun UserDetailScreen(
    userId: Int
) {
    val viewModel: UserDetailsViewModel = koinViewModel()
    viewModel.loadUser(userId)
    val uiState by viewModel.uiState.collectAsState()
    UserDetailBody(uiState = uiState)
}

@Composable
fun UserDetailBody(
    uiState: UIState<User>
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Toolbar()
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp)
        ) {
            (uiState as? UIState.Success)?.data?.let { user ->
                UserDetailInfo(user = user)
            }
        }
        LoadingView()
    }
}

@Composable
fun UserDetailInfo(
    user: User
) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (userImage, userNameText, userEmailText, userWebsiteText) = createRefs()
        Image(
            contentDescription = stringResource(R.string.user_image_description),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.constrainAs(userImage) {
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
                .fillMaxWidth()
                .height(128.dp),
            painter = rememberAsyncImagePainter(user.imageUrl)
        )
        Text(
            modifier = Modifier.constrainAs(userNameText) {
                top.linkTo(userImage.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
                .padding(vertical = 16.dp)
                .wrapContentSize(),
            style = MaterialTheme.typography.headlineMedium,
            text = user.name.orEmpty()
        )
        Text(
            modifier = Modifier.constrainAs(userEmailText) {
                top.linkTo(userNameText.bottom)
                start.linkTo(parent.start)
            }
                .padding(horizontal = 16.dp)
                .wrapContentSize(),
            style = MaterialTheme.typography.labelLarge,
            text = user.email.orEmpty()
        )
        Text(
            modifier = Modifier.constrainAs(userWebsiteText) {
                top.linkTo(userEmailText.bottom)
                start.linkTo(parent.start)
            }
                .padding(horizontal = 16.dp)
                .wrapContentSize(),
            style = MaterialTheme.typography.labelLarge,
            text = user.website.orEmpty()
        )
    }
}

/** Preview **/
@Preview
@Composable
private fun UserDetailBodyPreview() {
    UserDetailBody(uiState = UIState.Success(
        data = mockUser
    ))
}
