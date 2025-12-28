import React, { useState } from 'react';
import { Box, TextField, Button, Avatar, Stack, CircularProgress } from '@mui/material';
import SendIcon from '@mui/icons-material/Send';
import {GetCookie} from "../cookie/GetCookie.tsx";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import type { PostComment } from '../model/PostComment';

interface CommentInputProps {
    postId: number;
    onCommentAdded: (newComment: PostComment) => void;
}

export const AddCommentComponent: React.FC<CommentInputProps> = ({ postId, onCommentAdded }) => {
    const [text, setText] = useState("");
    const [loading, setLoading] = useState(false);
    const cookie = GetCookie();

    async function getUserId() {
        const response = await fetch(REQUEST_PREFIX + 'auth/whoami/', {
            method: "GET",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                'Authorization': 'Bearer ' + cookie.token
            }
        });

        if (response.ok) {
            const data = await response.json();
            return data.id;
        }
        throw new Error("Could not fetch user ID");
    }

    const handleSubmit = async () => {
        if (!text.trim()) return;
        setLoading(true);

        try {
            const currentUserId = await getUserId();

            const payload = {
                postId: postId,
                userId: currentUserId,
                content: text,
            };

            const response = await fetch(REQUEST_PREFIX + 'comments/create', {
                method: "POST",
                headers: {
                    'Content-Type': 'application/json; charset=utf-8',
                    'Authorization': 'Bearer ' + cookie.token,
                },
                body: JSON.stringify(payload)
            });

            if (response.ok) {
                const data = await response.json();
                setText("");
                data.author = cookie.username;
                onCommentAdded(data);
                console.log(data);
            } else {
                throw new Error("Failed to post comment");
            }
        } catch (err) {
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <Box sx={{ p: 2, bgcolor: 'background.default', borderRadius: 2, mb: 2 }}>
            <Stack direction="row" spacing={2} alignItems="flex-start">
                <Avatar sx={{ width: 32, height: 32 }} /> {/* Placeholder for current user */}

                <TextField
                    fullWidth
                    size="small"
                    multiline
                    maxRows={4}
                    placeholder="Write a comment..."
                    value={text}
                    onChange={(e) => setText(e.target.value)}
                    disabled={loading}
                    sx={{ bgcolor: 'background.paper' }}
                />

                <Button
                    variant="contained"
                    color="primary"
                    disabled={!text.trim() || loading}
                    onClick={handleSubmit}
                    sx={{ minWidth: '40px', height: '40px', borderRadius: '50%' }}
                >
                    {loading ? <CircularProgress size={20} /> : <SendIcon fontSize="small" />}
                </Button>
            </Stack>
        </Box>
    );
};