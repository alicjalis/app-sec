import type {Post} from "../model/Post.tsx";
import React, { useState } from "react";
import {Box, Card, CardContent, CardMedia, Divider, IconButton, Stack, Typography} from "@mui/material";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import {VoteBoxComponent} from "./VoteBoxComponent.tsx";
import {CommentComponent} from "./CommentComponent.tsx";
import {useNavigate} from "react-router-dom";
import {GetCookie} from "../cookie/GetCookie.tsx";
import {AddCommentComponent} from "./AddCommentComponent.tsx";
import type {PostComment} from "../model/PostComment.tsx";
import {UserType} from "../enums/UserType.tsx";
import DeleteIcon from "@mui/icons-material/Delete";

interface PostComponentProps {
    post: Post;
    displayUsername: boolean;
    displayComments: boolean;
}

export const PostComponent: React.FC<PostComponentProps> = ({ post, displayUsername, displayComments }) => {
    const navigate = useNavigate();
    const cookie = GetCookie();

    const [commentsList, setCommentsList] = useState<PostComment[]>(post.comments || []);
    const [isDeleted, setIsDeleted] = useState(post.isDeleted);

    const handleNewComment = (newComment: PostComment) => {
        setCommentsList([...commentsList, newComment]);
    };

    const handleDelete = () => {
        if (!window.confirm("Are you sure you want to delete this post?")) return;

        const payload = { ...post, isDeleted: true };

        fetch(REQUEST_PREFIX + 'posts/update', {
            method: "POST",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                'Authorization': 'Bearer ' + cookie.token,
            },
            body: JSON.stringify(payload)
        }).then(response => {
            if (response.ok) {
                setIsDeleted(true);
            }
        }).catch(err => console.error(err));
    };

    if (isDeleted) {
        return (
            <Card sx={{ width: 600, borderRadius: 2, boxShadow: 3 }} >
                <Typography variant="body2" color="text.secondary" fontStyle="italic">
                    [deleted]
                </Typography>
            </Card>
        );
    }

    return (
        <Card sx={{ width: 600, borderRadius: 2, boxShadow: 3 }} >
            <CardContent sx={{ display: 'flex' }}>

                <VoteBoxComponent contentType={"post"} contentId={post.id} initialUserVote={post.userReaction} initialScore={post.reactionScore} />

                <Typography variant="h6" component="div" noWrap ml={2}>
                    {post.title}
                </Typography>

                <Box sx={{ flexGrow: 1 }} />

                <Stack direction="row" alignItems="center" spacing={1}>
                    {displayUsername && (
                        <Typography variant="h6" component="div" noWrap>
                            {post.author}
                        </Typography>
                    )}

                    {cookie.userType == UserType.ADMIN && (
                        <IconButton
                            onClick={handleDelete}
                            color="error"
                            size="small"
                            sx={{ ml: 1 }}
                        >
                            <DeleteIcon />
                        </IconButton>
                    )}
                </Stack>
            </CardContent>
            <CardMedia
                component="img"
                image={REQUEST_PREFIX + "images/" + post.contentUri}
                alt={post.title}
                sx={{ objectFit: 'cover', cursor: 'pointer'  }}
                onClick={() => {navigate("/post/" + post.id)}}
            />

            {displayComments && post.comments &&  (
                <Box sx={{ px: 2, pb: 2 }}>
                    <Divider sx={{ my: 2 }} textAlign="left">
                        <Typography variant="caption" color="text.secondary">
                            {post.comments.length} COMMENTS
                        </Typography>
                    </Divider>

                    {cookie.logged && <AddCommentComponent postId={post.id} onCommentAdded={handleNewComment}/>}
                    <Stack spacing={1}>
                        {commentsList.map((c) => (
                            <CommentComponent key={c.id} postComment={c}/>
                        ))}
                    </Stack>
                </Box>
            )}

        </Card>
    );
};