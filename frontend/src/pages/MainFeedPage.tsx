import {Box, Container, Stack} from "@mui/material";
import {useEffect, useState} from "react";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import type {Post} from "../model/Post.tsx";
import {PostComponent} from "../components/PostComponent.tsx";

function MainFeedPage() {
    const[posts, setPosts] = useState<Post[]>([]);

    useEffect(() => {
        fetch(
            REQUEST_PREFIX + 'posts/all',
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
    }, []);

    return (
        <Box sx={{ bgcolor: 'background.default', minHeight: '100vh', py: 3 }}>
            <Container maxWidth="md">

                <Stack spacing={2}>
                    {posts.map((post) => (
                        <PostComponent key={post.id} post={post} displayUsername={true}/>
                    ))}
                </Stack>

            </Container>
        </Box>
    );
}

export default MainFeedPage;