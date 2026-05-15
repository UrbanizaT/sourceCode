package com.example.urbanizat.ui.screens

import androidx.compose.foundation.Clickable
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Toast
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.fab
import androidx.compose.material3.icons.Icons
import androidx.compose.material3.icons.default.Add
import androidx.compose.material3.icons.default.Comment
import androidx.compose.material3.icons.default.Favorite
import androidx.compose.material3.icons.default.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.urbanizat.ui.components.buttons.BaseButton
import com.example.urbanizat.ui.theme.UrbanizaTTheme

@Composable
fun CommunityScreen() {
    androidx.compose.material3.Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tablón Comunitario") },
                navigationIcon = {
                    IconButton(onClick = { /* Navigate back */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        fab = {
            FloatingActionButton(
                onClick = { /* Create new post */ },
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Crear publicación")
            }
        }
    ) {
        // Main content
        androidx.compose.foundation.layout.Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            // Posts list
            PostsList(
                posts = SampleData.communityPosts,
                onPostClick = { postId -> /* Handle post click */ },
                onLikeClick = { postId -> /* Handle like */ },
                onCommentClick = { postId -> /* Handle comment */ },
                onShareClick = { postId -> /* Handle share */ }
            )
        }
    }
}

@Composable
fun PostsList(
    posts: List<CommunityPost>,
    onPostClick: (String) -> Unit,
    onLikeClick: (String) -> Unit,
    onCommentClick: (String) -> Unit,
    onShareClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.padding(vertical = 8.dp)
    ) {
        items(posts) { post ->
            PostCard(
                post = post,
                onPostClick = { onPostClick(post.id) },
                onLikeClick = { onLikeClick(post.id) },
                onCommentClick = { onCommentClick(post.id) },
                onShareClick = { onShareClick(post.id) }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun PostCard(
    post: CommunityPost,
    onPostClick: () -> Unit,
    onLikeClick: () -> Unit,
    onCommentClick: () -> Unit,
    onShareClick: () -> Unit
) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPostClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = 4.dp
    ) {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = androidx.compose.foundation.layout.Alignment.CenterVertically
            ) {
                // User avatar/initials
                androidx.compose.foundation.layout.Circle(
                    modifier = Modifier
                        .size(36.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        )
                ) {
                    Text(
                        text = post.authorInitials,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))

                // User info
                androidx.compose.foundation.layout.Column {
                    Text(
                        text = post.authorName,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = post.timestamp,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                androidx.compose.foundation.layout.Spacer(
                    modifier = Modifier.weight(1f)
                )

                // More options (placeholder)
                androidx.compose.material3.IconButton(
                    onClick = { /* Show more options */ }
                ) {
                    androidx.compose.material3.Icon(
                        androidx.compose.material3.icons.default.MoreVert,
                        contentDescription = "Más opciones"
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Post content
            Text(
                text = post.content,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(align = androidx.compose.foundation.layout.Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Attachment placeholder (if any)
            if (post.hasAttachment) {
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            shape = androidx.compose.foundation.shape.RoundedCornerShape(8.dp)
                        )
                ) {
                    androidx.compose.material3.Icon(
                        imageVector = androidx.compose.material3.icons.default.Image,
                        contentDescription = "Adjunto",
                        modifier = Modifier
                            .size(24.dp)
                            .align(androidx.compose.foundation.layout.Alignment.Center),
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Adjunto: Foto",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
            }

            // Action buttons
            androidx.compose.foundation.layout.Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ActionButton(
                    icon = Icons.Default.Favorite,
                    label = "Me gusta",
                    count = post.likeCount,
                    isLiked = post.isLikedByUser,
                    onClick = { onLikeClick() }
                )
                ActionButton(
                    icon = Icons.Default.Comment,
                    label = "Comentar",
                    count = post.commentCount,
                    onClick = { onCommentClick() }
                )
                ActionButton(
                    icon = Icons.Default.Share,
                    label = "Compartir",
                    count = post.shareCount,
                    onClick = { onShareClick() }
                )
            }
        }
    }
}

@Composable
fun ActionButton(
    icon: androidx.compose.material3.IconVector,
    label: String,
    count: Int,
    isLiked: Boolean = false,
    onClick: () -> Unit
) {
    androidx.compose.material3.Row(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = androidx.compose.foundation.layout.Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        androidx.compose.material3.Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isLiked && label == "Me gusta")
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "$label ($count)",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Sample data
data class CommunityPost(
    val id: String,
    val authorName: String,
    val authorInitials: String,
    val content: String,
    val timestamp: String,
    val hasAttachment: Boolean,
    val likeCount: Int,
    val commentCount: Int,
    val shareCount: Int,
    val isLikedByUser: Boolean
)

object SampleData {
    val communityPosts = listOf(
        CommunityPost(
            id = "1",
            authorName = "María González",
            authorInitials = "MG",
            content = "¡Recordamos a todos los vecinos que mañana hay corte de agua de 9:00 a 14:00 horas por trabajos en la red principal!",
            timestamp = "Hace 2 horas",
            hasAttachment = false,
            likeCount = 15,
            commentCount = 3,
            shareCount = 1,
            isLikedByUser = false
        ),
        CommunityPost(
            id = "2",
            authorName = "Carlos Ruiz",
            authorInitials = "CR",
            content = "Alguien podría recomendar un buen fontanero urgente? Tengo una fuga en la cocina que no puedo parar.",
            timestamp = "Hace 5 horas",
            hasAttachment = true,
            likeCount = 8,
            commentCount = 7,
            shareCount = 0,
            isLikedByUser = false
        ),
        CommunityPost(
            id = "3",
            authorName = "Comunidad",
            authorInitials = "CC",
            content = "ASAMBLEA GENERAL CONVOCADA: Jueves 30 de mayo a las 19:00 en el salón de actos. Orden del día: aprobación de presupuestos y elección de nuevo presidente.",
            timestamp = "Ayer",
            hasAttachment = false,
            likeCount = 22,
            commentCount = 5,
            shareCount = 2,
            isLikedByUser = true
        )
    )
}

// Preview
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun CommunityScreenPreview() {
    UrbanizaTTheme {
        CommunityScreen()
    }
}