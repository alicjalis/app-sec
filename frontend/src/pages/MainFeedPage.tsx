import {Box, CircularProgress, Container, Stack, Typography} from "@mui/material";
import {useCallback, useEffect, useRef, useState} from "react";
import {REQUEST_PREFIX} from "../environment/Environment.tsx";
import type {Post} from "../model/Post.tsx";
import {PostComponent} from "../components/PostComponent.tsx";
import {GetCookie} from "../cookie/GetCookie.tsx";

function MainFeedPage() {
    const[posts, setPosts] = useState<Post[]>([]);
    const cookie = GetCookie();
    const [hasMore, setHasMore] = useState(true);
    const [loading, setLoading] = useState(false);

    const pageRef = useRef(0);
    const observerTarget = useRef(null);

    const loadPosts = useCallback(async (pageNumber: number) => {
        setLoading(true);
        console.log(cookie.token);
        try {
            const response = await fetch(
                REQUEST_PREFIX + 'posts?page=' + pageNumber + '&size=10',
                {
                    method: "GET",
                    headers: {
                        'Content-Type': 'application/json; charset=utf-8',
                        ...(cookie?.token && { 'Authorization': 'Bearer ' + cookie.token })
                    }
                }
            );
            const newPosts = await response.json();

            if (newPosts.length === 0) {
                setHasMore(false);
            } else {
                setPosts(prev => {
                    const existingIds = new Set(prev.map(p => p.id));
                    const uniqueNewPosts = newPosts.filter((p: any) => !existingIds.has(p.id));
                    return [...prev, ...uniqueNewPosts];
                });
                pageRef.current = pageNumber;
            }
        } catch (error) {
            console.error("Error loading posts:", error);
        } finally {
            setLoading(false);
        }
    }, [cookie.token]);

    useEffect(() => {
        loadPosts(0);
    },[loadPosts])

    useEffect(() => {
        const observer = new IntersectionObserver(
            entries => {
                if (entries[0].isIntersecting && hasMore && !loading) {
                    const nextPage = pageRef.current + 1;
                    loadPosts(nextPage);
                }
            },
            { threshold: 1.0 }
        );

        if (observerTarget.current) {
            observer.observe(observerTarget.current);
        }

        return () => {
            if (observerTarget.current) {
                observer.unobserve(observerTarget.current);
            }
        };
    }, [hasMore, loading, loadPosts]);

    return (
        <Box sx={{ bgcolor: 'background.default', minHeight: '100vh', py: 3 }}>
            <Container maxWidth="md">

                <Stack spacing={2}>
                    {posts.filter(post => !post.isDeleted).map((post) => (
                        <PostComponent key={post.id} post={post} displayUsername={true} displayComments={false}/>
                    ))}
                    <Box ref={observerTarget} sx={{ width: '100%', height: 20, display: 'flex', justifyContent: 'center', mt: 2 }}>
                        {loading && <CircularProgress />}
                        {!hasMore && <Typography variant="caption">There is no more. Go touch grass!</Typography>}
                    </Box>
                </Stack>

            </Container>
        </Box>
    );
}

export default MainFeedPage;