import type {PostComment} from "../model/PostComment.tsx";
import { UserType } from "../enums/UserType";
import React, {useState} from 'react';
import {Box, Typography, Paper, Stack, IconButton} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import { VoteBoxComponent } from './VoteBoxComponent';
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import {GetCookie} from "../cookie/GetCookie.tsx";

interface CommentProps {
    postComment: PostComment;
}

export const CommentComponent: React.FC<CommentProps> = ({ postComment }) => {
    const [isDeleted, setIsDeleted] = useState(postComment.isDeleted);
    const cookie = GetCookie();

    const handleDelete = () => {
        if (!window.confirm("Are you sure you want to delete this comment?")) return;

        fetch(REQUEST_PREFIX + 'comments/' + postComment.id, {
            method: "DELETE",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                ...(cookie?.token && { 'Authorization': 'Bearer ' + cookie.token })
            },
        }).then(response => {
            if (response.ok) {
                setIsDeleted(true);
            }
        }).catch(err => console.error(err));
    };

    if (isDeleted) {
        return (
            <Paper elevation={0} sx={{ p: 2, my: 1, bgcolor: 'background.default', borderRadius: 2, opacity: 0.7 }}>
                <Typography variant="body2" color="text.secondary" fontStyle="italic">
                    [deleted]
                </Typography>
            </Paper>
        );
    }

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
                    <Stack direction="row" justifyContent="space-between" alignItems="center">
                        <Stack direction="row" spacing={1} alignItems="center">
                            <Typography variant="subtitle2" fontWeight="bold">
                                {postComment.author}
                            </Typography>
                            <Typography variant="caption" color="text.secondary">
                                • {new Date(postComment.uploadDate).toLocaleDateString()}
                            </Typography>
                        </Stack>

                        {cookie.userType === UserType.ADMIN && (
                            <IconButton size="small" onClick={handleDelete} aria-label="delete">
                                <DeleteIcon fontSize="small" />
                            </IconButton>
                        )}

                    </Stack>

                    <Typography variant="body2" color="text.primary" sx={{textAlign: "left"}}>
                        {postComment.content}
                    </Typography>
                </Box>
            </Stack>
        </Paper>
    );
};