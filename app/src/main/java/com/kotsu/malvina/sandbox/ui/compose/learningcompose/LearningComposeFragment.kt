package com.kotsu.malvina.sandbox.ui.compose.learningcompose

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.MaterialTheme as MT
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.composethemeadapter.MdcTheme
import com.kotsu.malvina.R
import dagger.hilt.android.AndroidEntryPoint


/**
 * The fragment will be as backbone for the compose screen. I want to try for the start to use the Compose
 * only as the replacement for the xml and views but I don't want to change everything at once to the Compose
 * since I read that tools around of the Compose(ie navigation, sharing viewmodels) is not mature yet as of 11/19/2021
 */
@AndroidEntryPoint
class LearningComposeFragment : Fragment() {

    private val viewModel: LearningComposeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return ComposeView(requireContext()).apply {

            // Dispose the Composition when the view's LifecycleOwner is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)

            setContent {

                // Mdc comes from the compose-theme-adapter dependency. Not sure if I want to use this.
                // At one point it makes the xml styles as single source of truth for the styles
                // at another it forces us to still use xml
                MdcTheme {
//                MalvinaTheme() {
                    Surface(
                        color = MT.colors.secondary,
                    ) {
                        val messages by viewModel.messages.observeAsState()

                        messages?.let {
                            MessageList(messages = it)
                        }

                    }
                }
            }
        }
    }

}

@Composable
fun MessageList(messages: List<Message>) {

    Scaffold { paddingValues ->
        // this crashes because the items contain Column if the app theme is not Material one
        // java.lang.IllegalStateException: Nesting scrollable in the same direction layouts like LazyColumn and Column(Modifier.verticalScroll()) is not allowed.
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = paddingValues
        ) {
            items(messages) { message ->
                HelloComposeWorld(message)
            }
        }
    }
}

@Composable
fun HelloComposeWorld(message: Message) {
    Row(
        modifier = Modifier.padding(all = 8.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.image_placeholder),
            null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(48.dp)
                // This doesn't work
                .clip(CircleShape)
                .border(2.dp, MT.colors.primary, CircleShape)
        )
        
        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = message.author,
                // This is not what is set in the styles for primaryVariant. So composables don't get
                // colors from the styles which is weird or like still in transition from xml
                color = MT.colors.primaryVariant,
                style = MT.typography.subtitle2
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MT.shapes.medium,
                elevation = 4.dp
            ) {
                Text(
                    text = message.body,
                    modifier = Modifier.padding(all = 4.dp),
                    style = MT.typography.body2
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessagesList() {
//    MT {
    MdcTheme {
        MessageList(
            SampleData.conversationSample
        )
    }
}