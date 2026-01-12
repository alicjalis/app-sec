import React, {useState} from "react";
import {IconButton, Stack, Typography} from "@mui/material";
import ArrowUpwardIcon from '@mui/icons-material/ArrowUpward';
import ArrowDownwardIcon from '@mui/icons-material/ArrowDownward';
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import {GetCookie} from "../cookie/GetCookie.tsx";
import type {Cookie} from "../cookie/Cookie.tsx";

interface VoteBoxProps {
    contentType: string;
    contentId: number;
    initialScore: number;
    initialUserVote: number | null;
}

function setVote(value: number, contentId: number, contentType: string, cookie: Cookie): void {
    if (!cookie || !cookie.username) return;

    fetch(
        REQUEST_PREFIX + 'reaction/' + contentType,
        {
            method: "POST",
            headers: {
                'Content-Type': 'application/json; charset=utf-8',
                ...(cookie?.token && { 'Authorization': 'Bearer ' + cookie.token })
            },
            body: JSON.stringify({
                targetId: contentId,
                username: cookie.username,
                reaction: value
            })
        }
    )
}

export const VoteBoxComponent: React.FC<VoteBoxProps> = ({contentType, contentId, initialScore, initialUserVote }) => {
    const [score, setScore] = useState(initialScore);
    const [userVote, setUserVote] = useState(initialUserVote);
    const cookie = GetCookie();

    const handleVote = (type: 'up' | 'down') => {
        if (!cookie || !cookie.logged) {
            alert("Log in to vote.");
            return;
        }

        const value = type === 'up' ? 1 : -1;
        let newVote: number | null = value;
        let newScore = score;

        if (userVote === value) {
            newVote = null;
            newScore = score - value;
            setVote(0, contentId, contentType, cookie);
        }

        else if (userVote !== null) {
            newScore = score - userVote + value;
            setVote(newVote, contentId, contentType, cookie);
        }

        else {
            newScore = score + value;
            setVote(newVote, contentId, contentType, cookie);
        }

        setUserVote(newVote);
        setScore(newScore);
    };

    return (
        <Stack
            direction="row"
            alignItems="center"
            spacing={0.5}
            sx={{ bgcolor: 'background.paper', borderRadius: 1, p: 0.5, width: 'fit-content' }}
        >
            <IconButton
                onClick={() => handleVote('up')}
                size="small"
                sx={{ color: userVote === 1 ? '#ff4500' : 'text.secondary' }}
            >
                <ArrowUpwardIcon fontSize="small" />
            </IconButton>

            <Typography
                variant="body2"
                fontWeight="bold"
                sx={{
                    color: userVote === 1 ? '#ff4500' : userVote === -1 ? '#7193ff' : 'text.primary'
                }}
            >
                {Intl.NumberFormat('en-US', { notation: "compact", maximumFractionDigits: 1 }).format(score)}
            </Typography>

            <IconButton
                onClick={() => handleVote('down')}
                size="small"
                sx={{ color: userVote === -1 ? '#7193ff' : 'text.secondary' }}
            >
                <ArrowDownwardIcon fontSize="small" />
            </IconButton>
        </Stack>
    );
};