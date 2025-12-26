import type {PostComment} from "../model/PostComment.tsx";
import React from 'react';
import { Box, Typography, Paper, Stack } from '@mui/material';
import { VoteBoxComponent } from './VoteBoxComponent';

interface CommentProps {
    postComment: PostComment;
}

export const CommentComponent: React.FC<CommentProps> = ({ postComment }) => {
    const dateStr = new Date(postComment.uploadDate).toLocaleDateString();

    return (
        <Paper
            elevation={0}
            sx={{
                p: 2,
                my: 1,
                bgcolor: 'background.default',
                borderRadius: 2
            }}
        >
            <Stack direction="row" spacing={2} alignItems="flex-start">

                <VoteBoxComponent
                    contentType="comment"
                    contentId={postComment.id}
                    initialUserVote={postComment.userReaction || 0}
                    initialScore={postComment.reactionScore}
                />

                <Box sx={{ flexGrow: 1 }}>
                    <Stack direction="row" spacing={1} alignItems="center" sx={{ mb: 0.5 }}>
                        <Typography variant="subtitle2" fontWeight="bold">
                            {postComment.author}
                        </Typography>
                        <Typography variant="caption" color="text.secondary">
                            • {dateStr}
                        </Typography>
                    </Stack>

                    <Typography variant="body2" color="text.primary" sx={{textAlign: "left"}}>
                        {postComment.content}
                    </Typography>
                </Box>
            </Stack>
        </Paper>
    );
};