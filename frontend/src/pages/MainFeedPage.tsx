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
            REQUEST_PREFIX + 'posts/all?username=' + cookie.username,
            {
                method: "GET",
                headers: {
                    'Content-Type': 'application/json; charset=utf-8',
                }
            }
        ).then(response => {
            if (response.ok) {
                return response.json();
            }
        }).then(data => {
            setPosts(data);
        })
    }, [cookie.username]);

    return (
        <Box sx={{ bgcolor: 'background.default', minHeight: '100vh', py: 3 }}>
            <Container maxWidth="md">

                <Stack spacing={2}>
                    {posts.map((post) => (
                        <PostComponent key={post.id} post={post} displayUsername={true} displayComments={false}/>
                    ))}
                </Stack>

            </Container>
        </Box>
    );
}

export default MainFeedPage;