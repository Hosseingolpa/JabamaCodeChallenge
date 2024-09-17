package com.jabama.challenge.ui.screen.search

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.jabama.challenge.base.Status
import com.jabama.challenge.domain.model.search.Owner
import com.jabama.challenge.domain.model.search.Repository
import com.jabama.challenge.github.R
import com.jabama.challenge.ui.compose.util.HandleNavigateToScreenByState
import com.jabama.challenge.ui.theme.GithubTheme

@Composable
fun SearchScreen(
    state: SearchIUiState,
    onQueryChange: (String) -> Unit,
    onRetryClick: () -> Unit,
    navigateToAuthenticationScreen: () -> Unit,
) {

    val context = LocalContext.current

    fun navigateToWeb(url: String) {
        Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }.let { intent ->
            context.startActivity(intent)
        }
    }

    HandleNavigateToScreenByState(
        isEligibleForNavigation = state.shouldNavigateToAuthenticationScreen,
        navigateToScreen = navigateToAuthenticationScreen
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 8.dp)
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SearchTextField(
            query = state.query,
            onQueryChange = onQueryChange
        )

        Spacer(modifier = Modifier.height(16.dp))

        SearchContent(
            repositoriesStatus = state.repositoriesStatus,
            onRetryClick = onRetryClick,
            onGoToWebPageClick = ::navigateToWeb
        )

    }
}

@Composable
fun SearchTextField(
    query: String,
    onQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = query,
        placeholder = {
            Text(text = stringResource(id = R.string.search_repository_name))
        },
        onValueChange = onQueryChange
    )
}

@Composable
fun SearchContent(
    repositoriesStatus: Status<List<Repository>>,
    onRetryClick: () -> Unit,
    onGoToWebPageClick: (String) -> Unit,
) {
    AnimatedContent(
        modifier = Modifier.fillMaxSize(),
        targetState = repositoriesStatus,
        label = ""
    ) { status ->
        when (status) {
            is Status.Error -> ErrorContent(onRetryClick = onRetryClick)
            Status.Loading -> LoadingContent()
            Status.NotLoaded -> NotLoadedContent()
            is Status.Success -> SuccessContent(
                repositories = status.data,
                onGoToWebPageClick = onGoToWebPageClick
            )
        }
    }
}

@Composable
fun ErrorContent(
    onRetryClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Button(
            modifier = Modifier
                .align(Alignment.Center),
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Black
            ),
            onClick = onRetryClick
        ) {
            Text(
                text = stringResource(id = R.string.try_again),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun LoadingContent() {

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.loading),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }


}

@Composable
fun NotLoadedContent() {

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(id = R.string.please_search_repository),
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }

}

@Composable
fun SuccessContent(
    repositories: List<Repository>,
    onGoToWebPageClick: (String) -> Unit
) {

    if (repositories.isNotEmpty()) {

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(repositories, key = { it.id }) { item ->
                RepositoryItemContent(
                    item = item,
                    onGoToWebPageClick = onGoToWebPageClick
                )
            }
        }

    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = stringResource(id = R.string.no_repository_found),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }

}

@Composable
fun RepositoryItemContent(
    item: Repository,
    onGoToWebPageClick: (String) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(100.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface
                ), shape = MaterialTheme.shapes.medium
            )
            .clickable {
                onGoToWebPageClick(item.url)
            }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {

        AsyncImage(
            modifier = Modifier
                .size(80.dp)
                .clip(shape = CircleShape),
            model = item.owner.avatarUrl,
            error = painterResource(R.drawable.error),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.name_repository, item.name),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                textAlign = TextAlign.Left
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.full_name_repository, item.fullName),
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                textAlign = TextAlign.Left
            )

        }

    }

}


@Preview
@Composable
private fun SearchScreenPreview() {
    GithubTheme {
        val repositoryMock = Repository(
            id = "id",
            name = "Name",
            fullName = "Full name",
            owner = Owner(avatarUrl = ""),
            url = "",
        )
        SearchScreen(
            state = SearchIUiState(
                query = "",
                repositoriesStatus = Status.getStatusSuccess(data = listOf(repositoryMock))
            ),
            onQueryChange = {},
            onRetryClick = {},
            navigateToAuthenticationScreen = {}
        )
    }
}