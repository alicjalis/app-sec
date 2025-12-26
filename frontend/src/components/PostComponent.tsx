import type {Post} from "../model/Post.tsx";
import React from "react";
import {Box, Card, CardContent, CardMedia, Typography} from "@mui/material";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import {VoteBoxComponent} from "./VoteBoxComponent.tsx";

interface PostComponentProps {
    post: Post;
    displayUsername: boolean;
}

export const PostComponent: React.FC<PostComponentProps> = ({ post, displayUsername }) => {
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
                sx={{ objectFit: 'cover' }}
            />

        </Card>
    );
};