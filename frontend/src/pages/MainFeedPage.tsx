import {Box, Container, Stack} from "@mui/material";
import {useEffect, useState} from "react";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import type {Post} from "../model/Post.tsx";
import {PostComponent} from "../components/PostComponent.tsx";
import {GetCookie} from "../cookie/GetCookie.tsx";

function MainFeedPage() {
    const[posts, setPosts] = useState<Post[]>([]);
    const cookie = GetCookie();

    useEffect(() => {
        fetch(
            REQUEST_PREFIX + 'posts',
            {
                method: "GET",
                headers: {
                    'Content-Type': 'application/json; charset=utf-8',
                    ...(cookie?.token && { 'Authorization': 'Bearer ' + cookie.token })
                }
            }
        ).then(response => {
            if (response.ok) {
                return response.json();
            }
        }).then(data => {
            setPosts(data);
        })
    }, [cookie.token, cookie.username]);

    return (
        <Box sx={{ bgcolor: 'background.default', minHeight: '100vh', py: 3 }}>
            <Container maxWidth="md">

                <Stack spacing={2}>
                    {posts.filter(post => !post.isDeleted).map((post) => (
                        <PostComponent key={post.id} post={post} displayUsername={true} displayComments={false}/>
                    ))}
                </Stack>

            </Container>
        </Box>
    );
}

export default MainFeedPage;