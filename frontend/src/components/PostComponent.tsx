import type {Post} from "../model/Post.tsx";
import React from "react";
import {Box, Card, CardContent, CardMedia, Divider, Stack, Typography} from "@mui/material";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import {VoteBoxComponent} from "./VoteBoxComponent.tsx";
import {CommentComponent} from "./CommentComponent.tsx";
import {useNavigate} from "react-router-dom";

interface PostComponentProps {
    post: Post;
    displayUsername: boolean;
    displayComments: boolean;
}

export const PostComponent: React.FC<PostComponentProps> = ({ post, displayUsername, displayComments }) => {
    const navigate = useNavigate();
    return (
        <Card sx={{ width: 600, borderRadius: 2, boxShadow: 3 }} >
            <CardContent sx={{ display: 'flex' }}>

                <VoteBoxComponent contentType={"post"} contentId={post.id} initialUserVote={post.userReaction} initialScore={post.reactionScore} />

                <Typography variant="h6" component="div" noWrap ml={2}>
                    {post.title}
                </Typography>

                <Box sx={{ flexGrow: 1 }} />

                {displayUsername && (
                    <Typography variant="h6" component="div" noWrap>
                        {post.author}
                    </Typography>
                )}
            </CardContent>
            <CardMedia
                component="img"
                image={REQUEST_PREFIX + "images/" + post.contentUri}
                alt={post.title}
                sx={{ objectFit: 'cover', cursor: 'pointer'  }}
                onClick={() => {navigate("/post/" + post.id)}}
            />

            {displayComments && post.comments && post.comments.length > 0 && (
                <Box sx={{ px: 2, pb: 2 }}>
                    <Divider sx={{ my: 2 }} textAlign="left">
                        <Typography variant="caption" color="text.secondary">
                            {post.comments.length} COMMENTS
                        </Typography>
                    </Divider>

                    <Stack spacing={1}>
                        {post.comments.map((c) => (
                            <CommentComponent postComment={c}/>
                        ))}
                    </Stack>
                </Box>
            )}

        </Card>
    );
};